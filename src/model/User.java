/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jonpaulcarlo
 */
public class User extends Card{
    
    private String email;

    public User(){
        
    }
    
    public User(String cardNumber, String pin) {
        setCardNumber(cardNumber);
        setPin(pin);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String email) {
        this.email = email;
    }
    
    
    
}
