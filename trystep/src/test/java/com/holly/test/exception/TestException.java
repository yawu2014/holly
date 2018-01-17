package com.holly.test.exception;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/17 15:54
 */

public class TestException {
    boolean testEx() throws Exception{
        boolean ret = true;
        try{
            ret = testEx1();
        }catch(Exception e){
            System.out.println("testEx catch exception");
            ret = false;
            throw e;
        }finally {
            System.out.println("testEx,finally");
            return ret;
        }
    }

    boolean testEx1() throws Exception{
        boolean ret = true;
        ret = testEx2();
        if(!ret){
            return false;
        }
        System.out.println("testEx1,at the end of try");
        return ret;
    }
    boolean testEx2() throws Exception {
        boolean ret = true;
        try{
            int b = 12;
            int c ;
            for(int i=2;i>=-2;i--){
                c = b/i;
                System.out.println("i="+i);
            }
            return true;
        }catch(Exception e){
            System.out.println("testEx2,catch exception");
            ret = false;
            throw new Exception("xxx");
        }finally {
            System.out.println("test2,finally return type="+ret);
            throw new Exception("yyy"); //此处的return或者throw会吃掉catch块中的return或者throw
        }
    }

    public static void main(String[] args) {
        TestException testException = new TestException();
        try{
            testException.testEx1();
        }catch (Exception e){
            e.printStackTrace();;
        }
    }


}
