package com.macro.mall.tiny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.macro.mall.tiny.mbg.mapper")
public class MallMylearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallMylearningApplication.class, args);
    }

}
