package com.sweetbalance.backend.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestCrawler extends BaseCrawler{


    @Autowired
    public TestCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> crawl() {
        return crawlCertainBeverage();
//        return crawlCategories();
    }

    /*
     * JS에 의해 로드되는 동적 element의 경우
     * 하단 crawlCategories() 처럼 getText() 메소드 사용 시에는 정상 작동하지 않음
     * getAttribute("textContent") 메소드를 사용하여야 정상적으로 데이터를 가져온다
     */

    // 바닐라크림 콜드브루 영양정보 추출 예시 코드 (동적 element)
    private List<String> crawlCertainBeverage(){

        String BASE_URL = "https://www.starbucks.co.kr/menu/drink_list.do";

        List<String> results = new ArrayList<>();
        navigateTo(BASE_URL);

        // 명시적 대기 설정
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 테이블 요소 찾기
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[3]/div[7]/div[2]/div[2]/div/dl/dd[2]/table[1]")));

        // 테이블 내의 모든 행 찾기
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty() && cells.get(0).getAttribute("textContent").equals("바닐라 크림 콜드 브루")) {
                for (WebElement cell : cells) {
                    results.add(cell.getAttribute("textContent"));
                }
                break;
            }
        }

        return results;
    }

    // 카테고리 리스트 추출 예시 코드 (정적 element)
    private List<String> crawlCategories(){
        
        String BASE_URL = "https://www.starbucks.co.kr/menu/drink_list.do";

        List<String> results = new ArrayList<>();
        navigateTo(BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[3]/div[7]/div[2]/div[1]/div[2]/dl/dd[1]/div/form/fieldset/ul/div/div[1]")));

        List<WebElement> labels = container.findElements(By.tagName("label"));
        for (WebElement label : labels) {
            if (!label.getText().equals("전체 상품보기")) {
                results.add(label.getText());
            }
        }

        return results;
    }
}
