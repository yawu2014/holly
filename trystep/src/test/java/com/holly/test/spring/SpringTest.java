package com.holly.test.spring;

import com.holly.test.spring.bean.*;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        TreeBean treeBeanH = new TreeBean("H");
        treeBeanE.setLeft(treeBeanH);
        treeBeanD.setRight(treeBeanE);
        treeBeanF.setLeft(treeBeanG);
        treeBeanB.setLeft(treeBeanD);
        treeBeanB.setRight(treeBeanF);
        treeBeanA.setLeft(treeBeanB);
        treeBeanA.setRight(treeBeanC);
        String[] result = new String[]{"","",""};
        orderVisit(treeBeanA ,result);
        for(String str:result) {
            System.out.println(str);
        }
        System.out.println("----by stack");
        String[] resultStack = new String[]{"","",""};
        orderVisitByStackSuffix(treeBeanA,resultStack);
        for(String str:resultStack){
            System.out.println(str);
        }
        orderInQue(treeBeanA);
    }

    /**
     * 递归遍历
     * @param bean
     * @param str [0]先序遍历 [1]中序遍历 [2]后序遍历
     */
    private void orderVisit(TreeBean bean,String[] str){
        if(bean != null){
//            System.out.print(bean.getData()+":");//此处所在顺序就是访问循序,前序 A:B:D:E:F:G:C:
            str[0] += bean.getData()+":";
            orderVisit(bean.getLeft(),str);
            str[1] += bean.getData()+"-";
//            System.out.print(bean.getData()+":");//此处所在顺序就是访问循序,中序 D-E-B-G-F-A-C-
            orderVisit(bean.getRight(),str);
            str[2] += bean.getData()+"#";
//            System.out.print(bean.getData()+":");//此处所在顺序就是访问循序,后序 E:D:G:F:B:C:A:
        }

    }
    /**
     * 二叉树后续遍历
     * @param bean
     * @param resut [0]先序遍历 [1]中序遍历 [2]后序遍历
     */
    private void orderVisitByStackSuffix(TreeBean bean,String[] resut){
        Stack<TreeBean> stack = new Stack<TreeBean>();
        TreeBean visitBean = bean;
        Set<TreeBean> set = new HashSet<TreeBean>();
        Set<TreeBean> orderSet = new HashSet<>();
        if(visitBean == null){
            System.out.println("bean is null");
        }else{
            while(visitBean != null || stack.size()>0){
                while(visitBean != null){
                    resut[0]+=visitBean.getData()+":";
                    stack.push(visitBean);
                    visitBean = visitBean.getLeft();
                }
                visitBean = stack.pop();
                if(orderSet.add(visitBean)) {
                    resut[1] += visitBean.getData() + "-";
                }
                TreeBean visitRBean = visitBean.getRight();
                if(visitRBean != null){
                    if(set.add(visitRBean)) {
                        stack.push(visitBean);
                        visitBean = visitRBean;
                    }else{
                        resut[2] += visitBean.getData() + "#";
                        visitBean = null;
                    }
                }else{
                    resut[2] += visitBean.getData()+"#";
                    visitBean = null;
                }
            }
        }
    }

    /**
     * 队列遍历每一层的对象
     * @param treeBean
     */
    public void orderInQue(TreeBean treeBean){
        ConcurrentLinkedQueue treeQueue = new ConcurrentLinkedQueue();
        Object objPlaceHolder = new Object();
        if(treeBean != null){
            treeQueue.offer(treeBean);
//            treeQueue.offer(objPlaceHolder);
        }
        Object preObj = null;
        while(!treeQueue.isEmpty()){
            Object obj = treeQueue.poll();
//            if(obj == objPlaceHolder){
//                treeQueue.offer(objPlaceHolder);
//            }else {
                TreeBean tree = (TreeBean)obj;
                System.out.print(tree.getData()+"=");
                TreeBean tmp = null;
                if ((tmp = tree.getLeft()) != null) {
                    treeQueue.offer(tmp);
                }
                if((tmp = tree.getRight()) != null){
                    treeQueue.offer(tmp);
                }
//            }
//            if(preObj == obj && preObj == objPlaceHolder){
//                break;
//            }
//            preObj = obj;
        }
    }
}

