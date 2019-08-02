package com.springcloud.example.hystrix;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider", fallback = FallbackHelloService.class)
@Service
public interface HiService {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String hi(@RequestParam String name);

}
