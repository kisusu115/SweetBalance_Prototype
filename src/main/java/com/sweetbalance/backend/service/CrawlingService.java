package com.sweetbalance.backend.service;

import com.sweetbalance.backend.crawler.StarbucksCrawler;
import com.sweetbalance.backend.crawler.TestCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlingService {
    /*
     * 크롤링 호출 및 결과 저장 로직 구현 요함
     */

    private final TestCrawler testCrawler;
    private final StarbucksCrawler starbucksCrawler;

    @Autowired
    public CrawlingService(TestCrawler testCrawler, StarbucksCrawler starbucksCrawler) {
        this.testCrawler = testCrawler;
        this.starbucksCrawler = starbucksCrawler;
    }

    public void executeCrawling() {
        
        // 테스트 데이터 크롤링
        List<String> testData = testCrawler.crawl();
        System.out.println("Test Crawled Data: " + testData);

        // 스타벅스 데이터 크롤링
//        List<String> starbucksData = starbucksCrawler.crawl();
//        System.out.println("Starbucks Data: " + starbucksData);

        /*
         * DB 업데이트 등 로직 추가
         */
    }
}
