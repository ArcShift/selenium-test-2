package main;

import java.util.Properties;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Jelajah Tekno Indone
 */
public class Automation extends Selenium {

  int threadID;
  JavascriptExecutor jsExe;
  Properties properties;
  Database db;
  String query;
  Long key = null;
  String baseUrl = "http://kbbi.co.id/daftar-kata";
  HttpRequest request;
  Response response;

  public Automation(String threadName, int threadID) {
    this.threadName = threadName;
    this.threadID = threadID;
    this.properties = new Properties();
    this.db = new Database();
  }

  @Override
  public void run() {
    setupBrowser();
    cek();
    login();
    cekNUPTK();
  }
  void cek(){
    
  }
  void login() {
    boolean loginStatus = false;
    while (!loginStatus) {
      if (checkTitle("Kemdikbud Login")) {//login
        //input 
        sleeping(SHORT_WAIT);
      } else if (checkTitle("Index - VervalPTK")) {//logged in
        loginStatus = true;
        logResponse("Berhasil");
      } else {//error page
        load(baseUrl);
        logResponse("Gagal");
      }
    }
  }

  void cekNUPTK() {
    logAction("get");
    String step = "begin";
    stepping:
    while (!step.equals("end")) {
      switch (step) {
        case "begin": {
        }
        case "1": {
        }
        case "2": {
        }
      }
    }
    logResponse("finish");
  }

  void setupBrowser() {
    if (threadID % 2 == 1) {//chrome
      ChromeOptions chromeOptions = new ChromeOptions();
      webDriver = new ChromeDriver(chromeOptions);
    } else {//firefox
      FirefoxOptions firefoxOptions = new FirefoxOptions();
//    firefoxOptions.addArguments("-headless");
      webDriver = new FirefoxDriver(firefoxOptions);
    }
    webDriverWait = new WebDriverWait(webDriver, SHORT_WAIT);
    jsExe = (JavascriptExecutor) webDriver;
    Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
    message("run using " + capabilities.getBrowserName());
    webDriver.get(baseUrl);
  }

  private void logAction(String action) {
    query = "INSERT INTO `table_name` (parser_action) VALUES (?);";
    key = db.executeUpdate(query, action);
    message("log Action => " + action);
  }

  private void logResponse(String response) {
    if (key != null) {
      query = "UPDATE `table_name` SET response_time = NOW(), response_detail = ? WHERE id_log = ?;";
      db.executeUpdate(query, response, key);
      message("log Response => " + response);
    }
  }
}
