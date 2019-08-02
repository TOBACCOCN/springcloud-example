package com.springcloud.example.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiHelloController {

    @Autowired
    HelloService helloService;
    @Autowired
    HiService hiService;

    @GetMapping("/hi")
    public String hi(@RequestParam String name) {
        return hiService.hi(name);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return helloService.hello(name);
    }

}
