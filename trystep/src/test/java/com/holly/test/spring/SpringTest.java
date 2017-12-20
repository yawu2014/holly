package com.holly.test.spring;

import com.holly.test.spring.bean.*;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/13 17:36
 */

public class SpringTest {
    @Test
    public void testSimpleLoad(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("beanFactoryTest.xml"));
        MyTestBean myTestBean = (MyTestBean) bf.getBean("myTestBean");
        System.out.println(myTestBean.getTestStr());
    }

    /**
     * @Author: liuyujian
     * @Description: 循环依赖测试,构造函数的循环依赖会抛出异常 A->B B->C C->A
     * singletonsCurrentlyInCreation: 正在创建实例(单例)的状态记录hashmap,当前正在创建实例池
     * 过程解析:
     * 在创建A的时候会将A的beanname放入到singletonsCurrentlyInCreation中,在解析Constructor过程中,依赖B
     * 则去创建B的实例,将B的beanname放入singletonsCurrentlyInCreation中,在解析B的Constructor过程中,依赖C,
     * 则去创建C的实例,将C的beanname放入singletonsCurrentlyInCreation中,在解析C的Constructor过程中,依赖A
     * 则去创建A实例,在检查singletonsCurrentlyInCreation是否存在A时,发现重复创建A,报异常
     * @Date: 2017/12/19 13:56
     */
    @Test
    public void testCircleDependencyConstructor(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("circledependencytest.xml"));
        CircleDependencyA cda = bf.getBean("cda",CircleDependencyA.class);
        System.out.println(cda);

    }

    /**
     * @Author: liuyujian
     * @Description: 使用Setter方式解决循环依赖
     * 在创建A时,会使用无参的构造函数创建A的实例,并暴露一个ObjectFactory用于提前暴露创建中的A
     * @Date: 2017/12/19 14:15
     */

    @Test
    public void testCircleDependencySetter(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("circledependencytest2.xml"));
        CircleDependencyA cda = bf.getBean("cda",CircleDependencyA.class);
        System.out.println(cda);
    }

    /**
     * @Author: liuyujian
     * @Description:
     * @Date: 2017/12/19 17:28
     */

    @Test
    public void testCircleDependencySetterPrototype(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("circledependencytest3.xml"));
        CircleDependencyA cda = bf.getBean("cda",CircleDependencyA.class);
        System.out.println(cda);
    }

    @Test
    public void testAop(){
        ApplicationContext  bf = new ClassPathXmlApplicationContext("aoptest.xml");
        TestBean tb = (TestBean) bf.getBean("testBean");
        tb.test();
    }
    @Test
    public void testJDKDynamicProxy(){
        ProxyTestInterfaceImpl impl = new ProxyTestInterfaceImpl();
        ProxyTestInvocationHandler invocationHandler = new ProxyTestInvocationHandler(impl);
        ProxyTestInterface obj = (ProxyTestInterface) invocationHandler.getProxy();
        obj.add();
    }
    @Test
    public void testBiTree(){
        TreeBean treeBeanA = new TreeBean("A");
        TreeBean treeBeanB = new TreeBean("B");
        TreeBean treeBeanC = new TreeBean("C");
        TreeBean treeBeanD = new TreeBean("D");
        TreeBean treeBeanE = new TreeBean("E");
        TreeBean treeBeanF = new TreeBean("F");
        TreeBean treeBeanG = new TreeBean("G");
        treeBeanD.setRight(treeBeanE);
        treeBeanF.setLeft(treeBeanG);
        treeBeanB.setLeft(treeBeanD);
        treeBeanB.setRight(treeBeanF);
        treeBeanA.setLeft(treeBeanB);
        treeBeanA.setRight(treeBeanC);
        orderVisitByStack(treeBeanA);
    }

    /**
     * 递归遍历
     * @param bean
     */
    private void orderVisit(TreeBean bean){
        if(bean != null){
//            System.out.println(bean.getData()+":");//此处所在顺序就是访问循序,前序
            orderVisit(bean.getLeft());
//            System.out.println(bean.getData()+":");//此处所在顺序就是访问循序,中序
            orderVisit(bean.getRight());
            System.out.println(bean.getData()+":");//此处所在顺序就是访问循序,后序
        }
    }
    private void orderVisitByStack(TreeBean bean){
        Stack<TreeBean> stack = new Stack<TreeBean>();
        TreeBean visitBean = bean;
        Set<TreeBean> set = new HashSet<TreeBean>();
        if(visitBean == null){
            System.out.println("bean is null");
        }else{
            while(visitBean != null && set.add(visitBean)){
                while(visitBean != null){
                    System.out.print(visitBean.getData()+"-");
                    stack.push(visitBean);
                    visitBean = visitBean.getLeft();
                }
                visitBean = stack.pop();
                visitBean = visitBean.getRight();
            }
        }
    }
}

