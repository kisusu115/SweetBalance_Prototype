package com.sweetbalance.backend.config;

import com.sweetbalance.backend.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SchedulerConfig {
    // 필요하다면 스케줄러 관련 설정 (추후 CRON 표현식 등 포함)
    private final CrawlingService crawlingService;

    @Autowired
    public SchedulerConfig(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    // 매일 오전 1시에 크롤링 실행
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleCrawlingTask() {
        crawlingService.executeCrawling();
    }
}
