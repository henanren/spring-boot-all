package com.myzh.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ShardingApplication
 * 
 * @author ruqing
 * @since 2019.3.1 22:57
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.myzh.sharding.mapper")
@ServletComponentScan
public class ShardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingApplication.class, args);
	}

}
