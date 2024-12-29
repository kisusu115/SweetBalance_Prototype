package com.sweetbalance.backend.crawler;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StarbucksCrawler extends BaseCrawler {

    @Value("${spring.selenium.base-url.starbucks}")
    private String BASE_URL;

    @Autowired
    public StarbucksCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> crawl() {
        List<String> results = new ArrayList<>();
        navigateTo(BASE_URL);

        // TODO: Selenium 코드로 데이터 추출 (음료 목록 및 데이터 가져오기)
        // WebElement example = driver.findElement(By.cssSelector(".example-selector"));
        // results.add(example.getText());

        return results;
    }
}