package com.gmy.blog;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author gmydl
 * @title: IOTest
 * @projectName blog-api
 * @description: io 操作
 * @date 2022/7/30 10:15
 */

public class IOTest {

    @Test
    public void charTest() throws IOException {
        File fIle = new File("./text.txt");
        FileReader fileReader = new FileReader(fIle);

        int temp;
        while ((temp = fileReader.read()) != -1){
            System.out.print((char) temp);
        }

        fileReader.close();
    }

    @Test
    public void fileWriter(){
        File fIle = new File("./textWriter.txt");
        try {
            FileWriter fileWriter = new FileWriter(fIle);
            fileWriter.append("this.append()\n");
            // fileWriter.write(1);
            fileWriter.write("str: this.str()\n");
            fileWriter.write(new char[]{'d', 'q'});
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 缓冲流
     */
    @Test
    public void bufferTest(){



    }
}
