package com.sweetbalance.backend;

import com.sweetbalance.backend.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebCrawlingTestApplication implements CommandLineRunner {

    private final CrawlingService crawlingService;

    @Autowired
    public WebCrawlingTestApplication(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlingTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        crawlingService.executeCrawling();
    }
}
