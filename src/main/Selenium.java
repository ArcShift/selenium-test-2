
package main;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Jelajah Tekno Indone
 */
public class Selenium extends Thread {

  String threadName;
  WebDriver webDriver;
  final static int SHORT_WAIT = 5;//in second
  final static int WAIT = 20;//in second
  final static int REFRESH = 20;//in second
  final static int MAX_FIND = 12;
  int nFind = 1;
  WebDriverWait webDriverWait;

  public Selenium() {
    System.setProperty("webdriver.gecko.driver", "D:\\driver\\geckodriver.exe");
    System.setProperty("webdriver.chrome.driver", "D:\\driver\\chromedriver.exe");
  }

  void message(String stringMessage) {
    System.out.println(threadName + " => " + stringMessage);
  }

  void sleeping(int second) {
    try {
      Thread.sleep(second * 1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(AutomationKBBI.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  void refresh() {
//    sleeping(SHORT_WAIT);
    message("refreshing");
    webDriver.navigate().refresh();
    sleeping(1);
    checkPopUp();//FIX: only one usage
  }

  private boolean checkPopUp() {
    try {
      webDriver.switchTo().alert().accept();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  boolean findOnce(By by) {
    if (webDriver.findElements(by).size() <= 0) {
      message(by.toString().substring(3) + " not found(" + nFind +")");
      return false;
    } else {
      return true;
    }
  }

  WebElement find(By by) {
    nFind = 1;
    while (!findOnce(by)) {//check element exist
      if (nFind > MAX_FIND) {
        return null;
      }
      sleeping(SHORT_WAIT);
      nFind++;
    }
    return webDriver.findElement(by);
  }

  void click(By by) {//one way click, for element that disappear after clicking
    find(by).click();
    message("clicking : " + by.toString().substring(3));
    sleeping(SHORT_WAIT);
  }

  void clicks(By button, By targetElement) {//click a button until target element found
    click(button);
    while (!findOnce(targetElement)) {
      if (nFind > MAX_FIND) {
        click(button);
        nFind = 0;
      } else {
        message("page still loading");
      }
      sleeping(SHORT_WAIT);
      nFind++;
    }
  }

  void clickOrRefreshs(By button, By targetElement) {//for button that disapear after clicking
    click(button);
    nFind=0;
    while (!findOnce(targetElement)) {
      if(nFind>10){
        refresh();
        nFind=0;
        click(button);
      }
      nFind++;
      sleeping(SHORT_WAIT);
    }
  }

  void set(By by, String value) {//set value to an element
//    webDriverWait.until(ExpectedConditions.elementToBeSelected(by));
    find(by).sendKeys(value);
    message("Set : " + by.toString());
  }

  void select(By selectElement, String value) {
    Select select = new Select(find(selectElement));
    try {
      select.selectByVisibleText(value);
    } catch (Exception e) {
      message("option : " + value + " not found");
      sleeping(SHORT_WAIT);
      select(selectElement, value);
    }
    message("selecting " + selectElement + " with value " + value);
  }

  List<WebElement> getAllSelectOption(By by) {
    List<WebElement> options;
    try {
      Select select = new Select(find(by));
      options = select.getOptions();
    } catch (Exception e) {
      options = null;
      message("select " + by.toString() + " failed");
    }
    return options;
  }

  boolean checkVisibility(By by) {
    if (find(by).isDisplayed()) {
      return true;
    } else {
      return false;
    }
  }

  void load(String url) {
    webDriver.get(url);
    message("Load : "+url);
    sleeping(WAIT);
  }

  boolean checkURL(String url) {
    webDriverWait.until(ExpectedConditions.titleIs(url));
    if (webDriver.getCurrentUrl().trim().equals(url.trim())) {
      return true;
    } else {
      return false;
    }
  }

  boolean checkTitle(String url) {
//    sleeping(SHORT_WAIT);
    webDriverWait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    if (webDriver.getTitle().trim().equals(url.trim())) {
      return true;
    } else {
      return false;
    }
  }
}