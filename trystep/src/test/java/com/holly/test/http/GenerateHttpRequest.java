package com.holly.test.http;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/16 15:47
 */

public class GenerateHttpRequest {
    class PostRequest implements Callable<String> {
        String url;
        String param;
        CountDownLatch latch;
        public PostRequest(String url,String param,CountDownLatch latch){
            this.url = url;
            this.param = param;
            this.latch = latch;
        }
        @Override
        public String call() throws Exception {
            PrintWriter out = null;
            BufferedReader in = null;
            String result = "";
            try {
                URL realUrl = new URL(url);
                // 打开和URL之间的连接
                latch.await();
                URLConnection conn = realUrl.openConnection();
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                System.out.println("发送 POST 请求出现异常！"+e);
                e.printStackTrace();
            }
            //使用finally块来关闭输出流、输入流
            finally{
                try{
                    if(out!=null){
                        out.close();
                    }
                    if(in!=null){
                        in.close();
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            return result;
        }
    }
    @Test
    public void HighVisitTest() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        String url = "http://node:8099/api/robot/ah/jinde/archive/person/personalarchive/save";
        CountDownLatch latch = new CountDownLatch(1);
        List<Future> lists = Lists.newArrayList();
        for(int i=0;i<20;i++) {
            String scardno = "41142119450603"+getNum();
            System.out.println("scardno:"+scardno);
            String param = "clientid=110&sign=D2F857E97DD688C8256C990FE60A1F38&token=c423aa06715e4da48871aca3f902c759&infostr={\"cancerdiagnosistime\":\"\",\"cancername\":\"\",\"copddiagnosistime\":\"\",\"dbirthday\":\"1945-06-03\",\"dcreatetime\":\"2018-01-16\",\"gxbdiagnosistime\":\"\",\"gxydiagnosishis\":\"\",\"gxydiagnosistime\":\"\",\"gydiagnosistime\":\"\",\"ifloatingpopulation\":\"2\",\"jobdiseasediagnosistime\":\"\",\"jobdiseasename\":\"\",\"nczdiagnosistime\":\"\",\"sabobloodcode\":\"2\",\"sareacitycode\":\"341800\",\"sareacountrycode\":\"341825\",\"sareaname\":\"\",\"sareaprovincecode\":\"340000\",\"sareatowncode\":\"341825200\",\"sareavillagecode\":\"341825200205\",\"sbloodcode\":\"\",\"sbloodname\":\"\",\"sbloodtime\":\"\",\"sbrotherdieasecode\":\"\",\"sbrotherdieaseother\":\"\",\"scardno\":"+scardno+",\"schildrendiseasecode\":\"\",\"schildrendiseaseother\":\"\",\"scontactname\":\"王吉\",\"scontactphone\":\"13153526553\",\"scooperativecard1\":\"\",\"scooperativecard2\":\"\",\"scooperativeno\":\"\",\"scostcode\":\"11.5\",\"scostothername\":\"\",\"screateorgname\":\"版书乡白沙村卫生室\",\"sculturecode\":\"3\",\"sdiseasecode\":\"\",\"sdiseasememo\":\"\",\"sdiseaseother\":\"\",\"sdiseaseothertime\":\"\",\"sdoctorname\":\"程太禄\",\"sdoctoruid\":\"45541\",\"sdrinkwatercode\":\"\",\"sdrugallergycode\":\"K11\",\"sdrugallergyothername\":\"\",\"sexposecode\":\"Q92\",\"sfatherdiseasecode\":\"\",\"sfatherdiseaseother\":\"\",\"sfatherpersonid\":\"\",\"sfueltypecode\":\"\",\"shereditarydiseasecode\":\"\",\"shospitalcode\":\"\",\"shospitalinfo1\":\"\",\"shospitalinfo2\":\"\",\"shospitalinstitution1\":\"\",\"shospitalinstitution2\":\"\",\"shospitalintime1\":\"\",\"shospitalintime2\":\"\",\"shospitalouttime1\":\"\",\"shospitalouttime2\":\"\",\"shospitalpatientid1\":\"\",\"shospitalpatientid2\":\"\",\"shouseholdtypecode\":\"2\",\"sicno\":\"\",\"skitchenexhaustcode\":\"\",\"slivestockbarcode\":\"\",\"smarrycode\":\"2\",\"smedicalno\":\"\",\"smidiagnosistime\":\"\",\"smotherdiseasecode\":\"\",\"smotherdiseaseother\":\"\",\"smotherpersonid\":\"\",\"snationcode\":\"02\",\"soperationcode\":\"\",\"soperationname\":\"\",\"soperationtime\":\"\",\"spermanentstatuscode\":\"2\",\"spermanenttypecode\":\"2\",\"spersonname\":\"李瑾\",\"sphone\":\"13193524265\",\"sphysicaldisabilitycode\":\"\",\"sphysicaldisabilityother\":\"\",\"sregisteredareaname\":\"\",\"srhbloodcode\":\"否\",\"ssexcode\":\"1\",\"stoilteltypecode\":\"\",\"straumacode\":\"\",\"straumaname\":\"\",\"straumatime\":\"\",\"sworkcode\":\"8\",\"sworkdept\":\"经纶\",\"tnbdiagnosishis\":\"\",\"tnbdiagnosistime\":\"\",\"tuberculosisdiagnosistime\":\"\",\"nongHeResult\":false}";
            lists.add(threadPool.submit(new PostRequest(url, param, latch)));
        }
        Thread.sleep(10);
        latch.countDown();
        for(Future future:lists){
            try {
                System.out.println(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    private String getNum(){
        Random random = new Random();
        int result = random.nextInt()%10000;
        if(result < 0){
            result = -result;
        }
        String res = String.valueOf(result);
        if(res.length() < 4){
            res = "0"+res;
        }
        return res;
    }
    @Test
    public void random(){
        System.out.println(getNum());
    }
}
