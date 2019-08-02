package com.springcloud.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloHystrix")
    public String hello(@RequestParam String name) {
        return restTemplate.getForObject("http://PROVIDER/hi?name=" + name, String.class);
    }

    public String helloHystrix(String name) {
        return "sorry! " + name;
    }

}
