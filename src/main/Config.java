package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config extends java.util.Properties {

  FileInputStream fileInputStream;

  public Config(String file) {
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException ex) {
      System.out.println("file not found");
      Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(0);
    }
    try {
      load(fileInputStream);
    } catch (IOException ex) {
      Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(0);
    }
  }
}
