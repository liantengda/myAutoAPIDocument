package com.lian.javareflect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lian.javareflect.mapper")
@SpringBootApplication
public class JavareflectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavareflectApplication.class, args);
    }

}
