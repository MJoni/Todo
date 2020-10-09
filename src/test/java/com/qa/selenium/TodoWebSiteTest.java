package com.qa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class TodoWebSiteTest {

    private WebDriver driver;

    @Before
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/Driver/chromedriver.exe");
        driver = new ChromeDriver();
        Thread.sleep(2000);
        driver.manage().window().setSize(new Dimension(1366, 768));

    }

    @Test
    public void webTitleTest(){
        driver.get("http://127.0.0.1:5500/index.html");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("QAC Home", driver.getTitle());
    }
    @Test
    public void testCreateUser(){

    }
    @Test
    public void testReadUser(){

    }
    @Test
    public void testUpdateUser(){

    }
    @Test
    public void testDeleteUser() {

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}