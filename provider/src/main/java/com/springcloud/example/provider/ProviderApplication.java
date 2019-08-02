package com.springcloud.example.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @Value("${server.port}")
    String port;

    @GetMapping("/hi")
    public String hi() {
        return "hi! " + foo + ", I am form port: " + port;
    }

    @PostMapping("/hii")
    public String hii(HttpServletRequest request, String name) {
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        logger.info(">>>>> CONTENT_TYPE: {}", contentType);
        return "hi! " + name + ", I am form port: " + port;
    }

    @PostMapping("/hello")
    public String hello(HttpServletRequest request) {
        // 1.content-typ: application/x-www-form-urlencoded
        // getParameter() 和 getParameterMap() 可以拿到 uri 中的参数，也可以拿到请求体中的参数
        // 2.conten-type: multipart/form-data
        // getParameter() 和 getParameterMap() 可以拿到 uri 中的参数，拿不到其他参数
        // 3.conten-type: application/json
        // getParameter() 和 getParameterMap() 可以拿到 uri 中的参数，拿不到其他参数
        String name = request.getParameter("name");
        Map<String, String[]> map = request.getParameterMap();
        map.keySet().forEach(key -> logger.info(">>>> {} = {}", key, String.join(",", Arrays.asList(map.get(key)))));
        return "hi! " + name + ", I am form port: " + port;
    }

    @PostMapping("/upload")
    public void upload(HttpServletRequest request) {
        logger.info(">>>>> JVM MEMORY LEFT: {}", Runtime.getRuntime().freeMemory());
        try (ServletInputStream inputStream = request.getInputStream()) {
            byte[] buf = new byte[2048];
            int len;
            FileOutputStream fos = new FileOutputStream(new File("F:\\temp\\" + UUID.randomUUID()));
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.close();
            logger.info(">>>>> JVM MEMORY LEFT: {}", Runtime.getRuntime().freeMemory());
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            logger.error(stringWriter.toString());
        }
    }

}
