package com.gmy.blog.proxy;

import org.checkerframework.checker.units.qual.C;

/**
 * @author guanmy
 * @title: StaticProxyTest
 * @projectName blog
 * @description: 静态代理
 * @date 2022/7/20 14:36
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        // 被代理类对象
        LNClothFactory ln = new LNClothFactory();
        // 代理类对象
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(ln);

        proxyClothFactory.produceCloth();
    }
}

interface ClothFactory{

    void produceCloth();
}

/**
 * 衣服代理类
 */
class ProxyClothFactory implements ClothFactory {

    // 用被代理类对象实现实例化
    private ClothFactory factory;

    public ProxyClothFactory(ClothFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂坐准备工作");
        factory.produceCloth();
        System.out.println("代理工厂做一些善后处理工作");
    }
}

/**
 * 被代理类
 */
class LNClothFactory implements ClothFactory{
    @Override
    public void produceCloth() {
        System.out.println("李宁工厂生产衣服");
    }
}
