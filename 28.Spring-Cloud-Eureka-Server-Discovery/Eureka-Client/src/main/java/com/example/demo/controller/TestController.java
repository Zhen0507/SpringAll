package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient client;

   @GetMapping("/info")
public String info() {
    List<ServiceInstance> instances = client.getInstances("eureka-client");
    StringBuilder infoBuilder = new StringBuilder();

    if (instances == null || instances.isEmpty()) {
        infoBuilder.append("No available service instances.");
    } else {
        instances.forEach(instance -> {
            if (instance != null) {
                log.info("host：" + instance.getHost() + "，service_id：" + instance.getServiceId());
                infoBuilder.append("host: ").append(instance.getHost())
                           .append(", service_id: ").append(instance.getServiceId())
                           .append("\n");
            }
        });
    }

    return infoBuilder.toString();
}


    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
