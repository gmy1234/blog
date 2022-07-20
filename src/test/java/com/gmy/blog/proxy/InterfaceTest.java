package com.gmy.blog.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author guanmy
 * @title: InterfaceTest
 * @projectName blog
 * @description: 静态代理
 * @date 2022/7/20 14:06
 */
@SpringBootTest
public class InterfaceTest {

    @Test
    public void connection(){
        Serve serve = new Serve();
        ProxyServer proxyServer = new ProxyServer(serve);
        proxyServer.browse();

    }

}

interface NetWork{
    public void browse();

}

/**
 * 被代理类
 */
class Serve implements NetWork{

    @Override
    public void browse() {
        System.out.println("True 的服务器访问网络");
    }
}

/**
 * 代理类
 */
class ProxyServer implements  NetWork{

    private NetWork work;

    public ProxyServer(NetWork work){
        this.work = work;
    }

    @Override
    public void browse() {
        this.check();
        work.browse();
    }

    public void check(){
        System.out.println("联网之前的检查工作");
    }
}