package io.recommendation.web;

import io.recommendation.web.consumer.Consumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"io.recommendation"})
@MapperScan(basePackages = "io.recommendation.common.mapper")
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }


    @Override
    public void run(String... strings) throws Exception {
        try {
            Consumer consumer = new Consumer("rank","192.168.101.11:9092");
            consumer.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
