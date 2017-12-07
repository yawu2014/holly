package com.holly.test;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 16:43
 */

public class Han2Pin {
    @Test
    public void test(){
        String hanyu="刘玉建琪";
        char[] cl_chars = hanyu.trim().toCharArray();
        String pinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try{
            for(int i=0;i<cl_chars.length;i++){
                if(String.valueOf(cl_chars[i]).matches("[\\u4e00-\\u9fa5]+")){
                    pinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                }else{
                    pinyin += cl_chars[i];
                }
            }
        }catch (BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
            System.out.println("字符不匹配,不能转成拼音");
        }
        System.out.println(pinyin);
    }

}
