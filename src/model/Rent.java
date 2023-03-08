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
public class Rent {
    
    private int movieID;
    private int customerID;
    String rentDate;
    String rentReturn;

    public Rent(int movieID, int customerID) {  //for the rent
        this.movieID = movieID;
        this.customerID = customerID;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getRentReturn() {
        return rentReturn;
    }

    public void setRentReturn(String rentReturn) {
        this.rentReturn = rentReturn;
    }

    public Rent(int movieID, int customerID, String rentReturn) { //for the movie return
        this.movieID = movieID;
        this.customerID = customerID;
        this.rentReturn = rentReturn;
    }
    
    

    String getReturnDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
