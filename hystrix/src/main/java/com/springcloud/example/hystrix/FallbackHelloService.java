package com.springcloud.example.hystrix;

import org.springframework.stereotype.Component;

@Component
public class FallbackHelloService implements HiService {

    @Override
    public String hi(String name) {
        return "sorry! " + name;
    }

}
