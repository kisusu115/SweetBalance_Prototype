package com.sweetbalance.backend.crawler;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseCrawler {

    protected WebDriver driver;

    @Autowired
    public BaseCrawler(WebDriver driver) {
        this.driver = driver;
    }

    // 크롤링 로직을 구현하기 위한 추상 메서드 - 반환 값은 로직 구현에 따라 변경요함
    public abstract List<String> crawl();

    // 페이지 이동 공통 메서드
    protected void navigateTo(String url) {
        driver.get(url);
    }

    // WebDriver 종료
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}