/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;


public class Card {
    
    private String cardNumber;
    private String pin;
    private String tempBalance;
    private int balance;
    

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }  

    public String getTempBalance() {
        return tempBalance;
    }

    public int getBalance() {
        return balance;
    }

    
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setTempBalance(String tempBalance) {
        this.tempBalance = tempBalance;
        balance = Integer.parseInt(tempBalance);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    
    
    CardGenerator cg = new CardGenerator();
    private String[] cardDetails = new String[3]; 

    
    public void generateCard(){
         this.cardDetails = cg.cardDetails();
         setCardNumber(cardDetails[0]);
         setPin(cardDetails[1]);
         setTempBalance(cardDetails[2]);
         
    }

    //Innerclass for a card generator============================================================================
    public class CardGenerator{
        
        Random cardGen = new Random();
        String[] newCard = cardDetails();
    
        public String[] cardDetails(){
            
            String numGen = "4"; //Visa cards always starts with "4"
            String pinGen = "";
            String[] newCard = new String [3];
                        
            for(int i = 0; i<15;i++){ //the loops make sure that the card number and the pin always have 16/3 digits respectively even if the first digits are zero
            numGen = numGen + cardGen.nextInt(10);
            }
            
            for(int i = 0; i<3;i++){
            pinGen = pinGen + cardGen.nextInt(10);
            }
            
            newCard[0] = numGen;
            newCard[1] = pinGen;
            newCard[2] = Integer.toString(cardGen.nextInt(999)); //randomizing the card balance
            
            return newCard;
            
        }
        
        
    }
    
    
}
