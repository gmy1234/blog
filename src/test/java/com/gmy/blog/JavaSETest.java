package com.gmy.blog;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.context.Theme;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author guanmy
 * @title: JavaSETest
 * @projectName blog
 * @description: 测试
 * @date 2022/7/19 16:21
 */
@Slf4j
@SpringBootTest
public class JavaSETest {

    @Test
    public void ipAddr() throws UnknownHostException {
        InetAddress byName = InetAddress.getByName("127.0.0.1");
        System.out.println(byName);
    }

    /**
     * 客户端
     *
     * @throws IOException
     */
    @Test
    public void client() throws IOException {
        InetAddress inet = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(inet, 43221);
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("你好，我是客户端，发的请求".getBytes());
        outputStream.close();
        socket.close();
    }

    /**
     * 服务端
     */
    @Test
    public void server() throws IOException {
        ServerSocket server = new ServerSocket(43221);
        Socket accept = server.accept();

        InputStream inputStream = accept.getInputStream();

        int len;
        byte[] buffer = new byte[5];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        System.out.println(baos.toString());

        baos.close();
        inputStream.close();
        accept.close();
    }

    public static void main(String[] args) throws RuntimeException {

        String realTime3 = "2017年01月21日到2022年03月21日";
        String realTime4 = "2017年8月至2022年3月";
        String realTime5 = "2017年8月---2022年3月";
        String realTime6 = "2017年8月-2022年3月";
        int index = realTime5.length() / 2 ;
        String substring = realTime5.substring(index);
        System.out.println(substring);

    }

    @Test
    public void async() throws ExecutionException, InterruptedException {

        String m = "“";
        String test = "姓, 、~$，？》我国会是'？': 《 “】【 . 「」 + ”名";
        String success = test.replaceAll("[\\pP.`+~$｀＄＾“]", "");
        String trimAfter = success.replaceAll(" ", "");
        System.out.println(trimAfter);

        Pattern compile = Pattern.compile("[\\pP+~$`^=|<>～｀＄＾“]");
        Matcher matcher = compile.matcher(test);
        String s = matcher.replaceAll("").trim();

        System.out.println(s);



        String textRow = "姓, 、~$，？》我国会是'？' 《 “】【 「」 + ”名";

    }


}
