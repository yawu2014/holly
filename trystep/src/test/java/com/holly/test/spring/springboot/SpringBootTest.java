package com.holly.test.spring.springboot;

import com.holly.test.spring.springboot.configuration.*;
import com.holly.test.spring.springboot.service.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:51
 */
public class SpringBootTest {
    @Test
    public void testConfiguration(){
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(JavaConfig.class);
        UserFunctionService userFunctionService = acac.getBean(UserFunctionService.class);
        System.out.println(userFunctionService.say("xxxx"));
        acac.close();
    }
    @Test
    public void testELConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ELConfig.class);
        ELConfig elConfig = context.getBean(ELConfig.class);
        elConfig.outputResource();
    }
    @Test
    public void testAspectConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);
        DemoMethodService demoMethodService = context.getBean(DemoMethodService.class);
        demoAnnotationService.add();
        demoMethodService.add();
        context.close();
    }
    @Test
    public void testAwareConfig() throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        AwareService awareService = context.getBean(AwareService.class);
        awareService.outputResult();
    }
    @Test
    public void testAsyncTaskConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskExecutorConfig.class);
        AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
        for(int i=0;i<10;i++){
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
        context.close();
    }
    @Test
    public void testConditionalConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        ListService listService = context.getBean(ListService.class);
        System.out.println("os:"+context.getEnvironment().getProperty("os.name")+":cmd:"+listService.showListCmd());
    }
}
