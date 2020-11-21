package com.holly.test.decode;

import cn.hutool.core.collection.*;
import cn.hutool.core.io.*;
import org.apache.commons.lang3.*;
import org.assertj.core.util.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

public class Decoder2 {
    //解密后的目标路径
    static File destFile = new File("E:\\decodegoto\\original_.php");;
    //要解密的文件路径
    static File orginalFile = new File("E:\\decodegoto\\original.php");

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



    public static void main(String[] args) throws Exception {
        List<String> lines = FileUtil.readLines(orginalFile, Charset.defaultCharset());
        for (String line : lines) {
            FileUtil.appendLines(Lists.newArrayList(line), destFile, Charset.defaultCharset());

            if (line.trim().indexOf("function") > -1) {
                break;
            }
        }

        FileUtil.appendLines(Lists.newArrayList(blankNumber(1) + " {"), destFile, Charset.defaultCharset());

        startWrite(7, false, lines);

        FileUtil.appendLines(Lists.newArrayList(blankNumber(1) + " }"), destFile, Charset.defaultCharset());
        FileUtil.appendLines(Lists.newArrayList("} ?>"), destFile, Charset.defaultCharset());
    }

    //下一步将要执行的行数
    private static int goOnLineIndex = -1;
    //当前正在执行的深度
    private static int step = 1;


    private static void startWrite(int index, boolean breakOut, List<String> lines) throws Exception {
        if (index == -1 || breakOut) {
            return;
        }
        List<String> labelTree = getLabelTree(lines, index);
        Map<String, List<LineInfo>> labelMap = getLabelMaps(lines);

        int nextLine = -1;
        boolean breakOutFlag = false;
        for (String label : labelTree) {
            List<LineInfo> lineInfos = labelMap.get(label);

            if (isIfBlock(lineInfos)) {
                dealIfBlock(lineInfos, lines);
            } else if (isForOrWhileOrOther(lineInfos)) {
                dealOtherBlock(lineInfos);
            } else {
                List<LineInfo> newLineInfos = new ArrayList<>();
                newLineInfos.addAll(lineInfos.subList(0, lineInfos.size() - 1));

                for (LineInfo newLineInfo : newLineInfos) {
                    if (!isGotoFlag(newLineInfo.getContent())) {
                        System.out.println(newLineInfo);
                        FileUtil.appendLines(Lists.newArrayList(newLineInfo.getContent()), destFile, Charset.defaultCharset());
                    } else {
                        goOnLineIndex = newLineInfo.getLine();
                    }
                }
            }
            //如果集合中最后一行是out
            LineInfo lineInfo = lineInfos.get(lineInfos.size() - 1);
            if (lineInfo.isOut()) {
                nextLine = lineInfo.getLine() + 2;
                String tempLabel = extractLabelInGoto(lines.get(nextLine));

                List<LineInfo> tempLinesInfo = labelMap.get(tempLabel);
                if (isIfBlock(tempLinesInfo)) {
                    breakOutFlag = false;
                } else {
                    breakOutFlag = true;
                }
                break;
            }
        }
        startWrite(nextLine, breakOutFlag, lines);
    }

    /**
     * 处理if语句
     *
     * @param lineInfos
     * @return
     */
    private static void dealIfBlock(List<LineInfo> lineInfos, List<String> lines) throws Exception {
        String content = lineInfos.get(0).getContent();
        //如果反转了
        if (isIfDeCondition(content)) {
            //获得原始的语句
            String originalCondition = getOriginalCondition(content);
            //写该if语句
            FileUtil.appendLines(Lists.newArrayList(originalCondition), destFile, Charset.defaultCharset());
            //获得即将执行的下一行语句
            startWrite(lineInfos.get(3).getLine(), false, lines);

            goOnLineIndex = lineInfos.get(1).getLine();

            //执行完了之后在最后添加}
            FileUtil.appendLines(Lists.newArrayList("}"), destFile, Charset.defaultCharset());
        } else {
            //写该if语句
            FileUtil.appendLines(Lists.newArrayList(content), destFile, Charset.defaultCharset());
            //获得即将执行的下一行语句
            startWrite(lineInfos.get(1).getLine(), false, lines);

            //执行完了之后，拼接else
            FileUtil.appendLines(Lists.newArrayList(blankNumber(4) + "}else{"), destFile, Charset.defaultCharset());
            startWrite(lineInfos.get(3).getLine(), false, lines);
            //执行完了之后在最后添加}
            FileUtil.appendLines(Lists.newArrayList(blankNumber(4) + "}"), destFile, Charset.defaultCharset());
        }
        if (goOnLineIndex != -1) {
            startWrite(goOnLineIndex, false, lines);
        }

    }

    private static void dealOtherBlock(List<LineInfo> lineInfos) throws Exception {
        String content = lineInfos.get(0).getContent();
        FileUtil.appendLines(Lists.newArrayList(content), destFile, Charset.defaultCharset());

        List<String> newLines = lineInfos.subList(1, lineInfos.size() - 2).stream().map(lineInfo -> lineInfo.getContent()).collect(Collectors.toList());

        //获得即将执行的下一行语句
        startWrite(0, false, newLines);
        //执行完了之后在最后添加}
        FileUtil.appendLines(Lists.newArrayList(blankNumber(4) + "}"), destFile, Charset.defaultCharset());

        List<String> lines = FileUtil.readLines(orginalFile, Charset.defaultCharset());

        int line = lineInfos.get(lineInfos.size() - 2).getLine();
        int j = line;
        for (int i = line; i < lines.size(); i++) {
            if (isGotoFlag(lines.get(i))) {
                j = i;
                break;
            }
        }
        if (j == line) {
            return;
        }
        String s = lines.get(j);
        //如果出来for循环后，碰到跳出标识，然后需要找到真正的goto语句
        if (isGotoFlag(s)) {
            String s1 = extractLabelInGoto(s);
            Map<String, List<LineInfo>> labelMaps = getLabelMaps(lines);
            if (labelMaps.get(s1).size() == 1 && StringUtils.isBlank(labelMaps.get(s1).get(0).getContent())) {
                j = labelMaps.get(s1).get(0).getLine() + 2;
            }
        }
        startWrite(j, false, lines);
    }

    private static String blankNumber(int num) {
        String empty = StringUtils.EMPTY;
        for (int i = 0; i < num; i++) {
            empty += "  ";
        }
        return empty;
    }


    /**
     * 根据行数获得该行goto 字段对应的标记集合
     *
     * @param lines
     * @param index
     * @return
     * @throws Exception
     */
    private static List<String> getLabelTree(List<String> lines, int index) throws Exception {

        String s1 = lines.get(index);

        if (!isGotoFlag(s1)) {
            System.out.println(index);
            System.out.println(s1);
            throw new Exception("该行不是goto语句");
        }

        Map<String, List<LineInfo>> labelMap = getLabelMaps(lines);

        List<String> labelTree = new LinkedList<>();

        while (true) {

            String line = lines.get(index);
            if (isGotoFlag(line)) {
                String currentLabel = extractLabelInGoto(line);

                labelTree.add(currentLabel);
                List<LineInfo> blockLines = labelMap.get(currentLabel);

                if (CollectionUtil.isEmpty(blockLines)) {
                    break;
                }

                //获得最后一行语句 一般是goto语句
                LineInfo lastLineInfo = blockLines.get(blockLines.size() - 1);
                //如果最后最后一行不是goto语句，终止
                if (!isGotoFlag(lastLineInfo.getContent())) {
                    break;
                }
                //遇到if代码块终止
                if (isIfBlock(blockLines)) {
                    break;
                }

                //最后一句行号
                int lineNo = lastLineInfo.getLine();
                //再从最后一行递归
                index = lineNo;
            }
        }
        return labelTree;
    }

    /**
     * <label:代码集合/>
     *
     * @param lines
     * @return
     * @throws Exception
     */
    private static Map<String, List<LineInfo>> getLabelMaps(List<String> lines) throws Exception {
        LinkedHashMap<String, List<LineInfo>> labelMap = new LinkedHashMap<>();
        int prefixBlankCount = Math.max(8, prefixBlankCount(lines.get(0)));
        for (int i = 0; i < lines.size(); i++) {
            String originalLine = lines.get(i);
            String line = originalLine.trim();
            //如果当前是label语句
            if (isLabelFlag(line)) {
                String label = extractLabel(line);
                List<LineInfo> contents = new ArrayList<>();
                //从当前行开始循环，遇到新的label结束
                for (int j = i + 1; j < lines.size(); j++) {
                    String lineJ = lines.get(j);
                    //遇到新的label语句 而且满足代码前空格长度=8
                    if (isLabelFlag(lineJ) && prefixBlankCount(lineJ) == prefixBlankCount) {
                        break;
                    }
                    //遇到代码前空格长度小于8 结束
                    if (prefixBlankCount(lineJ) < prefixBlankCount) {
                        break;
                    }
                    LineInfo lineInfo = new LineInfo(j, lineJ);
                    contents.add(lineInfo);
                }
                //如果一行内容也没有
                if (CollectionUtil.isEmpty(contents)) {
                    LineInfo lineInfo = new LineInfo(i, StringUtils.EMPTY);
                    contents.add(lineInfo);
                }
                if (!labelMap.containsKey(label)) {
                    labelMap.put(label, contents);
                }
            }
        }
        List<String> newLines = CollectionUtil.reverseNew(lines);
        String lastLine = StringUtils.EMPTY;
        for (String newLine : newLines) {
            if (isLabelFlag(newLine) && prefixBlankCount(newLine) == 8) {
                lastLine = extractLabel(newLine.trim());
                break;
            }
        }

        String finalLastLine = lastLine;
        labelMap.forEach((label, lineInfos) -> {
            if (lineInfos.size() == 1) {
                LineInfo lineInfo = lineInfos.get(0);
                if (lineInfo.getLine() == lines.size() - 1) {
                    lineInfo.setLast(true);
                }
                //如果当前内容为空，且下一行是label语句
                if (StringUtils.isBlank(lineInfo.getContent()) && (lineInfo.getLine() + 1 < lines.size()) && isLabelFlag(lines.get(lineInfo.getLine() + 1))) {
                    lineInfo.setOut(true);
                }
                if (StringUtils.equals(label, finalLastLine)) {
                    lineInfo.setLast(true);
                }
            }
        });
        return labelMap;
    }

    /**
     * if ($res) {
     * goto ad_i5;
     * }
     * goto odLf6;
     *
     * @param lines
     * @return
     */
    private static boolean isIfBlock(List<LineInfo> lines) {
        if (lines.size() == 4) {
            String line1 = lines.get(0).getContent();
            String line2 = lines.get(1).getContent();
            String line3 = lines.get(2).getContent();
            String line4 = lines.get(3).getContent();
            boolean isline1 = line1.trim().startsWith("if") && line1.trim().endsWith("{");
            boolean isline2 = isGotoFlag(line2);
            boolean isline3 = line3.trim().equals("}");
            boolean isline4 = isGotoFlag(line4);
            return isline1 && isline2 && isline3 && isline4;
        }
        return false;
    }

    private static boolean isForOrWhileOrOther(List<LineInfo> lines) {

        if (lines.size() < 3) {
            return false;
        }
        String firstLine = lines.get(0).getContent();
        String lastLine = lines.get(lines.size() - 2).getContent();
        if (firstLine.trim().startsWith("for") || firstLine.trim().startsWith("foreach")) {
            if (lastLine.trim().startsWith("}")) {
                return true;
            }
        }
        return false;


    }

    //是否是if反转
    private static boolean isIfDeCondition(String line) {
        line = line.trim();
        if (line.contains("||") || line.contains("&")) {
            return line.startsWith("if (!(") && line.endsWith(")) {");
        } else {
            return line.startsWith("if (!") && line.endsWith(") {");
        }
    }

    private static String getOriginalCondition(String line) {
//        line = line.trim();
        if (line.contains("||") || line.contains("&")) {
            line = line.replace("if (!(", "if (");
            line = line.replace(")) {", ") {");
        } else {
            line = line.replace("if (!", "if (");
//            line = line.replace(") {", "{");
        }
        return line;
    }


    /**
     * 求一行字符串前空格数量
     *
     * @param line
     * @return
     */
    private static int prefixBlankCount(String line) {
        return line.replaceAll("([ ]*).*", "$1").length();
    }

    /**
     * 某一行是否是label字段 MKSo0:
     *
     * @param line
     * @return
     */
    private static boolean isLabelFlag(String line) {
        if (StringUtils.isBlank(line)) {
            return false;
        }
        return line.trim().length() == 6 && getLastChar(line.trim()).equals(":");
    }

    /**
     * 某一行是否是goto 字段；goto SRkBk;
     *
     * @param line
     * @return
     */
    private static boolean isGotoFlag(String line) {
        line = line.trim();
        if (StringUtils.isBlank(line)) {
            return false;
        }
        return line.length() == 11 && line.startsWith("goto") && getLastChar(line).equals(";");
    }

    private static String extractLabel(String line) throws Exception {
        if (!isLabelFlag(line)) {
            throw new Exception("该语句不是label语句");
        }
        return line.substring(0, 5);
    }

    /**
     * 在goto字段提取label
     *
     * @param line
     * @return
     * @throws Exception
     */
    private static String extractLabelInGoto(String line) throws Exception {
        line = line.trim();
        if (StringUtils.isBlank(line)) {
            throw new Exception("字符串为空，无法提取label");
        }
        if (!isGotoFlag(line)) {
            throw new Exception("不是goto标识");
        }
        return line.substring(5, 10);
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
}
