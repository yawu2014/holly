package com.holly.test;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 16:43
 */

public class Han2Pin {
    @Test
    public void test(){
        String hanyu="\uE863";
        char[] cl_chars = hanyu.trim().toCharArray();
        String pinyin = "";
        String initial = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try{
            for(int i=0;i<cl_chars.length;i++){
                if(String.valueOf(cl_chars[i]).matches("[\\u4e00-\\u9fa5]+")){
                    String res = PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                    pinyin += res;
                    initial+=res.substring(0,1);
                }else{
                    pinyin += cl_chars[i];
                }
            }
        }catch (BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
            System.out.println("字符不匹配,不能转成拼音");
        }
        if(StringUtils.isAlpha(initial)){
            System.out.println("true");
        }
        System.out.println(pinyin.toLowerCase());
        System.out.println(initial.toLowerCase());
    }
    @Test
    public void testStringFormat(){
        String word="刘玉建";
        String suffix = String.format(" and (sphone='%s' or scardno='%s' or spersonname like '%%%s%%')",word,word,word);
        System.out.println(suffix);
    }
}
