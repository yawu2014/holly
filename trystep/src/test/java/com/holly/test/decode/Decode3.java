package com.holly.test.decode;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Decode3 {
    //解密后的目标路径
    static File destFile = new File("E:\\decodegoto\\original_.php");;
    //要解密的文件路径
    static File orginalFile = new File("E:\\decodegoto\\original.php");
    static AtomicInteger ai = new AtomicInteger(0);
    private List<FunctionMeta> functionMetaList = new ArrayList<>();
    static{

        if (destFile.exists()){
            destFile.delete();
        }
        try {
            destFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Decode3 decode3 = new Decode3();
        decode3.parseStruct();
        decode3.parseFunction();
    }
    /**
     * 解析整个文件语法结构
     */
    private void parseStruct(){
        List<String> lines = FileUtil.readLines(orginalFile, Charset.defaultCharset());
        int rows = lines.size();
        for (int i =0; i<rows; i++) {
            String content = lines.get(i).trim();
            if(StringUtils.isEmpty(content)){
                continue;
            }
            // define
            if(content.startsWith("define")){
                DefineMeta defineMeta = new DefineMeta();
                defineMeta.setContent(content);
            } else if(content.startsWith("class")){ //class
                ClassMeta classMeta = new ClassMeta();
                classMeta.setTitle(content);
                String[] metaArr = content.split(" ");
                classMeta.setClassName(metaArr[1]);
                if(metaArr.length > 2){
                    if("extends".equals(metaArr[2])){
                        classMeta.setSuperClass(metaArr[3]);
                    }
                }
            } else if(isFunction(content)){ // function
                String[] metaArr = content.trim().split(" ");
                FunctionMeta functionMeta = new FunctionMeta();
                functionMeta.setName(metaArr[2]);
                functionMeta.setTitle(content);
                List<ExpressionMeta> expressions = new ArrayList<>();
                functionMeta.setLineInfos(expressions);
                // todo 最后一个函数的结束没有考虑到
                for(int j = i+2; j < rows; j++){
                    String functionRow = lines.get(j);
                    if(StringUtils.isEmpty(functionRow)){
                        continue;
                    }
                    if(isFunction(functionRow)){
                        i = j-1;
                        break;
                    }
                    ExpressionMeta expression = new ExpressionMeta(j, functionRow);
                    if(isLabelFlag(functionRow)) {
                        expression.setLabelExpress(true);
                    } else {
                        expression.setCommonExpress(true);
                        if (isGotoFlag(functionRow)) {
                            expression.setGotoExpress(true);
                        } else if(functionRow.startsWith("if")){
                            expression.setIfExpress(true);
                        }
                    }
                    expressions.add(expression);
                }
                functionMetaList.add(functionMeta);
            }
        }
    }
    static ThreadLocal<String> methodName = new ThreadLocal<>();
    /**
     * 解析各个函数
     */
    private void parseFunction(){
        for(FunctionMeta functionMeta : this.functionMetaList){
            Map<String, List<ExpressionMeta>> label2Expression = genLabel2Expression(functionMeta.getLineInfos());
            List<ExpressionMeta> result = new ArrayList<>();
            methodName.set(functionMeta.getName());
            doParseFunction(Lists.newArrayList(functionMeta.getLineInfos().get(0)), label2Expression, result);
        }
    }

    /**
     *  解析
     * @param expressionMetaList
     * @param label2Expression
     * @param result
     */
    private void doParseFunction(List<ExpressionMeta> expressionMetaList, Map<String, List<ExpressionMeta>> label2Expression, List<ExpressionMeta> result){
        if(CollectionUtils.isEmpty(expressionMetaList)){
            log.error("empty expression");
            return;
        }
        for (ExpressionMeta expressionMeta : expressionMetaList) {
            if(expressionMeta.isGotoExpress()){
                String gotoLabel = extractLabelInGoto(expressionMeta.getContent());
                doParseFunction(label2Expression.get(gotoLabel), label2Expression, result);
            } else {
                result.add(expressionMeta);
            }
        }
        logInfo(result);
    }

    private static void logInfo(List<ExpressionMeta> exps){
        int round = ai.addAndGet(1);
        List<String> lines = new ArrayList<>();
        String fileName = methodName.get() + round;
        log.info("\nbegin:{}", round);
        for(ExpressionMeta exp : exps){
            log.info("{} \n", exp.getContent());
            lines.add(exp.getContent());
        }
        log.info("\nend:{}", round);
        try {
            File file = new File("E:\\decodegoto\\m" + fileName);
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileUtils.writeLines(file, lines);
        } catch (IOException e) {
            log.error("写入文件出错:{}", e.getMessage());
        }
    }
    /**
     * gotolable 获取实际的执行语句
     * @param gotoLabel
     * @param map
     * @return
     */
    private List<ExpressionMeta> retriveExps(String gotoLabel, Map<String, List<ExpressionMeta>> map){
        List<ExpressionMeta> exps = map.get(gotoLabel);
        if(CollectionUtils.isEmpty(exps)){
            log.error("lable:{} not has Expression", gotoLabel);
            return new ArrayList<>();
//            throw new RuntimeException("Expression erro：" + JSON.toJSON(exps) + "-" + gotoLabel);
        }
        if(exps.size() == 1 && exps.get(0).isGotoExpress()){
            return retriveExps(extractLabelInGoto(exps.get(0).getContent()), map);
        }
        return exps;
    }

    /**
     * label2 Expressions
     * @param expressionMetas
     * @return
     */
    private Map<String, List<ExpressionMeta>> genLabel2Expression(List<ExpressionMeta> expressionMetas){
        Map<String, List<ExpressionMeta>> map = new HashMap<>();
        String preLabel = "";
        List<ExpressionMeta> eList = new ArrayList<>();
        for(int i=0; i<expressionMetas.size(); i++){
            ExpressionMeta expressionMeta = expressionMetas.get(i);
            if(expressionMeta.isLabelExpress()) {
                String curLabel = expressionMeta.getContent().trim().substring(0, 5);
                if(!StringUtils.isEmpty(preLabel)){
                    if(!eList.isEmpty()){
                        List<ExpressionMeta> labelExpression = new ArrayList<>();
                        labelExpression.addAll(eList);
                        eList.clear();
                        map.put(preLabel, labelExpression);
                    }else {
                        ExpressionMeta express = new ExpressionMeta(expressionMeta.getLine(), curLabel);
                        express.setLabelExpress(true);
                        map.put(preLabel, Lists.newArrayList(express));
                    }
                }
                preLabel = curLabel;
            } else {
                if(StringUtils.isNotEmpty(preLabel)) {
                    eList.add(expressionMeta);
                }
            }
        }
        map.forEach((key, value) ->{
            StringBuilder sb = new StringBuilder(key).append("\n");
            value.forEach(vv -> sb.append(vv.getContent()).append("\n"));
            log.info("{}", sb.toString());
        });
        return map;
    }
    /**
     * 是否是标签行
     * @param line
     * @return
     */
    private static boolean isLabelFlag(String line) {
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            return false;
        }
        return line.trim().length() == 6 && getLastChar(line.trim()).equals(":");
    }

    /**
     * 获得字符串最后一个字符
     *
     * @param str
     * @return
     */
    private static String getLastChar(String str) {
        return str.substring(str.length() - 1);
    }

    /**
     * 在goto字段提取label
     *
     * @param line
     * @return
     * @throws Exception
     */
    private static String extractLabelInGoto(String line) {
        line = line.trim();
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            throw new RuntimeException("字符串为空，无法提取label");
        }
        if (!isGotoFlag(line)) {
            throw new RuntimeException("不是goto标识");
        }
        return line.substring(5, 10);
    }
    /**
     * 某一行是否是goto 字段；goto SRkBk;
     *
     * @param line
     * @return
     */
    private static boolean isGotoFlag(String line) {
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            return false;
        }
        String str = line.trim();
        return str.length() == 11 && str.startsWith("goto") && getLastChar(str).equals(";");
    }

    /**
     * 是否是function的定义行
     * @return
     */
    private boolean isFunction(String line){
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            return false;
        }
        String str = line.trim();
        return str.contains("function");
    }
}
