package com.fuzy.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringCloudAlibabaSentinalSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaSentinalSampleApplication.class, args);
    }

    @GetMapping("test_flow_rule")
    public String testFlowRule(){
        return "SUCCESS";
    }

}
