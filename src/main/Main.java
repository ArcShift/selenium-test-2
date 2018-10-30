/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Jelajah Tekno Indone
 */
public class Main {

  final static int N_THREAD = 1;
  AutomationKBBI[] automations = new AutomationKBBI[N_THREAD];

  public Main() {
    //run thread    
    for (int i = 0; i < automations.length; i++) {
      automations[i] = new AutomationKBBI("Program " + i, i);
      automations[i].start();
    }
  }

  public static void main(String[] args) {
    Main main = new Main();
  }

}