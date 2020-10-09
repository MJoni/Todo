package com.qa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

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
        driver.get("http://127.0.0.1:5500/index.html");
        WebElement first_name = driver.findElement(By.name("first_name"));
        first_name.sendKeys("Joni");
        WebElement surname = driver.findElement(By.name("surname"));
        surname.sendKeys("Baki");
        WebElement user_name = driver.findElement(By.name("user_name"));
        user_name.sendKeys("mjoni");
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("mjoni@qa.com");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("123456");

        WebElement btn = driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[6]/button"));
        btn.click();

        WebElement expected = driver.findElement(By.id("success-create"));
        assertEquals("New user created Successfully!", expected.getText());

    }
    @Test
    public void testReadUser() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/index.html");
        Thread.sleep(1000);
        WebElement userId = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/form/div[1]/input"));
        userId.sendKeys("62");
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/form/div[2]/input"));
        password.sendKeys("123456");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/form/div[3]/button")).click();
        Thread.sleep(1000);
        WebElement expected = driver.findElement(By.id("login-msg"));
        assertEquals("Login Successfully!", expected.getText());

    }
    @Test
    public void testUpdateUser() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        Thread.sleep(2000);
        driver.findElement(By.id("my-profile")).click();
        Thread.sleep(1000);
        WebElement first_name = driver.findElement(By.id("first_name"));
        first_name.sendKeys("New");
        WebElement surname = driver.findElement(By.id("surname"));
        surname.sendKeys("User");
        WebElement user_name = driver.findElement(By.id("user_name"));
        user_name.sendKeys("nuser");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("nuser@qa.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123456");
        Thread.sleep(1000);
        driver.findElement(By.id("update-user")).click();
        Thread.sleep(1000);

        String expected = driver.findElement(By.id("show-msg")).getText();
        assertEquals("User details Updated", expected);
        driver.findElement(By.id("show-msg")).click();
    }
    @Test
    public void testDeleteUser() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        Thread.sleep(2000);
        driver.findElement(By.id("my-profile")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("delete-user")).click();
        Thread.sleep(1000);
        assertEquals("QAC Home", driver.getTitle());


    }
    @Test
    public void testCreateTask() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/button[1]")).click();
        Thread.sleep(1000);

        WebElement title = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[1]/input"));
        title.sendKeys("Our Task");
        WebElement  start_date = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[2]/input"));
        start_date.sendKeys("11-10-2020");
        WebElement  due_date = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[3]/input"));
        due_date.sendKeys("11-11-2929");
        WebElement body = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[4]/textarea"));
        body.sendKeys("we will be done by then");
        Thread.sleep(1000);

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[5]/button[2]")).click();
        Thread.sleep(1000);

        String expected = driver.findElement(By.id("show-msg")).getText();
        assertEquals("New Task Created", expected);
        driver.findElement(By.id("show-msg")).click();

    }
    @Test
    public void testReadTask() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        Thread.sleep(1000);
        WebElement inputSearch = driver.findElement(By.xpath("/html/body/ul/form/input"));
        inputSearch.sendKeys("194");
        driver.findElement(By.xpath("/html/body/ul/form/button")).click();
        Thread.sleep(2000);
        String expected = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/span/table/tr/td/div/h4")).getText();
        Thread.sleep(1000);
        assertEquals("194. Their task", expected);
    }
    @Test
    public void testUpdateTask() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        driver.findElement(By.xpath("/html/body/ul/form/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/span/table/tr[1]/td")).click();
        Thread.sleep(1000);
        WebElement updateBtn = driver.findElement(By.id("updateAtask"));
        updateBtn.click();
        Thread.sleep(1000);

        WebElement title = driver.findElement(By.id("title"));
        title.sendKeys("Our Task");
        WebElement  start_date = driver.findElement(By.id("start_date"));
        start_date.sendKeys("11-10-2020");
        WebElement  due_date = driver.findElement(By.id("due_date"));
        due_date.sendKeys("11-11-3000");
        WebElement body = driver.findElement(By.id("body"));
        body.sendKeys("What else can be done");
        Thread.sleep(1000);
        driver.findElement(By.id("#updateTask")).click();
        Thread.sleep(1000);
        String expected = driver.findElement(By.id("show-msg")).getText();
        assertEquals("Task Updated", expected);

    }
    @Test
    public void testDeleteTask() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/todo.html");
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/ul/form/button")).click();
//        Thread.sleep(1000);
//        int index =0;
//        WebElement data = driver.findElement(By.id("display-result"));
//       Thread.sleep(1000);
//        List<WebElement> tableRows = data.findElements(By.tagName("td"));
//        System.out.println(tableRows.size());
//        tableRows.get(index).click();
//        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/span/table/tr[1]/td")).click();
        Thread.sleep(1000);
        WebElement deleteBtn = driver.findElement(By.id("deleteAtask"));
        deleteBtn.click();
        Thread.sleep(1000);
        driver.findElement(By.id("delConfirmButton")).click();
        Thread.sleep(1000);

        String expected = driver.findElement(By.id("show-msg")).getText();
        assertEquals("A task has been deleted", expected);

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}