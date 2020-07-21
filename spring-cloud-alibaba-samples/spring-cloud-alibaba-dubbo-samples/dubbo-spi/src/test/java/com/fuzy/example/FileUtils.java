package com.fuzy.example;

import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.protocol.ProtocolListenerWrapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @ClassName FileUtils
 * @Description 读取文件工具类
 * @Author 11564
 * @Date 2020/7/21 7:44
 * @Version 1.0.0
 */
public class FileUtils {
    @Test
    public void readFileByURL() throws IOException {
        String name = Protocol.class.getName();
        URL url = this.getClass().getClassLoader().getResource("META-INF/dubbo/"+name);
        assert url != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine())!=null){
            System.out.println(line);
        }
        reader.close();
    }
    @Test
    public void readFileByResourceAsStream()throws IOException{
        String name = Protocol.class.getName();
        InputStream inputStream = Protocol.class.getClassLoader().getResourceAsStream("META-INF/dubbo/" + name);
        assert inputStream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine())!=null){
            System.out.println(line);
        }
        reader.close();
    }
    @Test
    public void readFileByEnumerationUrl()throws IOException{
        String name = Protocol.class.getName();
        Enumeration<URL> resources = Protocol.class.getClassLoader().getResources("META-INF/dubbo/" + name);
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            assert url != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine())!=null){
                System.out.println(line);
            }
            reader.close();
        }
    }
}
