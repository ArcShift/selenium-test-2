/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jelajah Tekno Indone
 */
public class Test {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try{
      int n= 4/ 0;
      System.out.println(n);
      
    }catch(ArithmeticException ae){
      
    }
    
  }
  
}