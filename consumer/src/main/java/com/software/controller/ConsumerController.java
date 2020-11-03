package com.software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gaohu9712@163.com
 * @date 2020/10/29
 * @description
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/go")
    public void go() {
        List<ServiceInstance> providerServer = discoveryClient.getInstances("ProviderServer");

        if (0 == providerServer.size()) {
            return;
        }

        ServiceInstance serviceInstance = providerServer.get(0);
        System.out.print(serviceInstance.getUri() + " --- ");
        String url = serviceInstance.getUri() + "/provider/get";


        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }
}
