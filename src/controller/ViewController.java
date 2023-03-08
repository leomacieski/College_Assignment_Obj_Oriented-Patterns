/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import model.Model;
import model.Movies;
import model.Rent;
import model.User;
import view.View;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author jonpaulcarlo
 */
public class ViewController implements ActionListener {
    View view;
    Model model;
    Movies[] moviesAll,moviesSearch;
    
    private int allMovieCount = 0;
    private int countSearch; //counts the search result
    private int categoriesCount;
    private String[][] allMovies;
    private String[][] searchMovieResult;
    private String[][] moviesActive; // for sorting
    private String [][] categoriesResult;
    private String search;
    private String active; //for sorting
    private String cardLast = null; //for the last 4 digit of the card
    private String pin = null; //for the pin input
    private int movieCode = 0;
    private int customerID; //will serve as a foreign key for the rent
    private int movieID; //will serve as a foreign key for the rent
    private int customerBalance; //customer's balance on their card
    
    String[][] infoMovie;

    public ViewController (){
      this.view = new View(this);
      this.model = new Model();
      
      checkLateReturn(); //runs the "never returned" checker everytime the Xtra-Vision app opens
    }

    @Override
    //rent movie
    public void actionPerformed(ActionEvent e) {
        
        //================================================================going to main panel================================================================\\
        if(e.getActionCommand().equals("goMain")){
            
            view.setHeaderLabel("All Movies");
            allMovieCount = model.allMoviesCount();
            allMovies = model.allMovies();
            searchMovieResult = null;
            for(int i=0;i<allMovies.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(allMovies[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getWelcome().dispatchEvent(new WindowEvent(view.getWelcome(), WindowEvent.WINDOW_CLOSING));//disposes the other frame when this frame opens
            view.main();  
            
        }
        
        //return screen
        //================================================================Opens the Return window ================================================================\\
        if(e.getActionCommand().equals("retur")){
            System.out.println("return a movie");
           // view.getWelcome().dispatchEvent(new WindowEvent(view.getWelcome(), WindowEvent.WINDOW_CLOSING));
            view.returnn();
        }
        
        if(e.getActionCommand().equals("exit2")){
            view.getInfo().dispatchEvent(new WindowEvent(view.getInfo(), WindowEvent.WINDOW_CLOSING));
        }
        
        //================================================================for the search functiom================================================================\\
        if(e.getActionCommand().equals("search")){
            
            model = new Model();

            search = view.getSearchBar().getText();
            countSearch = model.countSearchTitle(search); //count the results
            System.out.println("CS1 "+countSearch);////////////////
            
            if(search.isEmpty())
            {
                model.allMoviesCount();
                allMovies = model.allMovies(); 
                searchMovieResult = null;

                for(int i=0;i<allMovies.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(allMovies[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
                
                view.getMovieSectionL().show(view.getMovieSection(), "moviesHome");

            }
            else{
                
                if(countSearch==0)
                {
                    showMessageDialog(view.getMovieSection(), "No results found for "+search+"!", "Error!", 0);
                }
                else
                {
                    view.setHeaderLabel("Search Results for \""+search+"\""); //assign the input into the label
                    searchMovieResult = model.searchTitle(search); 
                    allMovies = null;

                    for(int i=0;i<searchMovieResult.length;i++)       //for the rows
                    {
                        for(int j=0;j<6;j++)   //for the columns
                        {
                            System.out.println(searchMovieResult[i][j]);         //initalizing the value of squares into 0;

                        }  
                        System.out.println("");
                    }
                    view.getMovieSection().add(view.movieSearchPanel(),"searchResult");
                    view.getMovieSectionL().show(view.getMovieSection(), "searchResult");
                }
                
            }
        }
        
        //================================================================for the sort function================================================================\\
        if(e.getActionCommand().equalsIgnoreCase("sort")){
            
            moviesActive = null; //container for the sorting function
            active=null;
            String sortType = view.getSort().getSelectedItem().toString();
            
            
            if(allMovies == null){  //switches the data inside of the container on what data is active.
                moviesActive = searchMovieResult;
                active = "search";

                System.out.println("Search active");
            }else if(searchMovieResult == null){
                moviesActive = allMovies;
                active = "all";
                System.out.println("All active");

            }
            if(active.equalsIgnoreCase("all")){  //Sorts the "All movies" result if the active identifier is "all"
                moviesActive = model.allMoviesSort(sortType);
                for(int i=0;i<allMovies.length;i++)       //for the rows
                    {
                        for(int j=0;j<6;j++)   //for the columns
                        {
                            System.out.println(moviesActive[i][j]);         //initalizing the value of squares into 0;

                        }  
                        System.out.println("");
                    }
                view.getMovieSection().add(view.moviePanelSort(),"allMoviesSort");
                view.getMovieSectionL().show(view.getMovieSection(), "allMoviesSort");
            }
            else if(active.equalsIgnoreCase("search")){  //Sorts the search result if the active identifier is "search"
                moviesActive = model.searchTitleSort(search , sortType);
                for(int i=0;i<moviesActive.length;i++)       //for the rows
                    {
                        for(int j=0;j<6;j++)   //for the columns
                        {
                            System.out.println(moviesActive[i][j]);         //initalizing the value of squares into 0;

                        }  
                        System.out.println("");
                    }
                
                view.getMovieSection().add(view.moviePanelSort(),"allMoviesSort");
                view.getMovieSectionL().show(view.getMovieSection(), "allMoviesSort");
//                view.getMovieSection().add(view.movieSearchPanelSort(),"searchResult");
//                view.getMovieSectionL().show(view.getMovieSection(), "searchResult");
            }
            
        }

        //================================================================FAQ================================================================\\
        //FAQ
        if(e.getActionCommand().equals("faq")){
            System.out.println("FAQ frame for help");
            view.getMain().dispatchEvent(new WindowEvent(view.getMain(), WindowEvent.WINDOW_CLOSING));
            view.faq();
        }
        if(e.getActionCommand().equals("back")){
            System.out.println("back to home movies");
            view.getFaq().dispatchEvent(new WindowEvent(view.getFaq(), WindowEvent.WINDOW_CLOSING));
            view.main();
        }
        
                                  
        //================================================================Switches the Categories window================================================================\\
        //============================= panels with card layout
        
        String categories = null;
        
        if(e.getActionCommand().equals("action")){
            view.setHeaderLabel("Category: Action");
            System.out.println("action category");
            categories="action";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("comedy")){
            view.setHeaderLabel("Category: Comedy");
            System.out.println("comedy category");
            categories="comedy";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("drama")){
            view.setHeaderLabel("Category: Drama");
            System.out.println("drama category");
            categories="drama"; //categories identifier
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("fantasy")){
            view.setHeaderLabel("Category: Fantasy");
            System.out.println("fantasy category");
            categories="fantasy";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("horror")){
            view.setHeaderLabel("Category: Horror");
            System.out.println("horror category");
            categories="horror";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("mystery")){
            view.setHeaderLabel("Category: Mystery");
            System.out.println("mystery category");
            categories="mystery";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("romance")){
            view.setHeaderLabel("Category: Romance");
            System.out.println("romance category");
            categories="romance";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("scifi")){
            view.setHeaderLabel("Category: Science Fiction");
            System.out.println("scifi category");
            categories="scifi";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("thriller")){
            view.setHeaderLabel("Category: Thriller");
            System.out.println("thriller category");
            categories="thriller";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        if(e.getActionCommand().equals("western")){
            view.setHeaderLabel("Category: Western");
            System.out.println("western category");
            categories="western";
            categoriesCount=model.categoriesMoviesCount(categories);
            categoriesResult=model.categoriesMovies(categories);
            System.out.println("count="+ categoriesCount);
            for(int i=0;i<categoriesResult.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(categoriesResult[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
            view.getMovieSection().add(view.movieCategories(), "movieCategories");
            view.getMovieSectionL().show(view.getMovieSection(), "movieCategories");
        }
        
        //================================================================back to home when the Home button is pressed================================================================\\
        
        if(e.getActionCommand().equals("home")){
            
            model.allMoviesCount();
                allMovies = model.allMovies(); 
                searchMovieResult = null;
                view.setHeaderLabel("All Movies");

                for(int i=0;i<allMovies.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(allMovies[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
                
                view.getMovieSection().add(view.moviePanel(),"moviesHome");
                view.getMovieSectionL().show(view.getMovieSection(), "moviesHome");
        }
        
        if(e.getActionCommand().equals("home")){
            
            model.allMoviesCount();
                allMovies = model.allMovies(); 
                searchMovieResult = null;
                view.setHeaderLabel("All Movies");

                for(int i=0;i<allMovies.length;i++)       //for the rows
                {
                    for(int j=0;j<6;j++)   //for the columns
                    {
                        System.out.println(allMovies[i][j]);         //initalizing the value of squares into 0;

                    }  
                    System.out.println("");
                }
                
                view.getMovieSection().add(view.moviePanel(),"moviesHome");
                view.getMovieSectionL().show(view.getMovieSection(), "moviesHome");
        }
        
        
        
        
        
        //================================================================For the movie renting function================================================================\\
        
        //fetch the details of the movie picked while clicking the info button
        int countResult = view.getCountResult();
        
        for(int i=0;i<countResult;i++){
            
            if(e.getActionCommand().equals("info"+view.getMovieID()[i])){
                
                if(view.getPanelActive().equalsIgnoreCase("movieAll")){
                    
                    //infoMovie the container for the details of the selected movie based on the moviecode of the movie
                    movieCode = Integer.parseInt(allMovies[i][5]);
                    infoMovie = model.infoMovies(movieCode);
                    
                }else if(view.getPanelActive().equalsIgnoreCase("movieSearch")){
                    
                    movieCode = Integer.parseInt(searchMovieResult[i][5]);
                    infoMovie = model.infoMovies(movieCode);
                    
                }else if(view.getPanelActive().equalsIgnoreCase("movieSort")){
                    
                    movieCode = Integer.parseInt(moviesActive[i][5]);
                    infoMovie = model.infoMovies(movieCode);
                    
                }else if(view.getPanelActive().equalsIgnoreCase("movieCategory")){
                    
                    movieCode = Integer.parseInt(categoriesResult[i][5]);
                    infoMovie = model.infoMovies(movieCode);
                    
                }
                
                view.info();
            
            }
            
        }
        
        //Switches to the payment page if they click the "Rent button" on the info page.
        if(e.getActionCommand().equals("rent")){
            
            cardLast = null; //re-initialize the card details, simulating the removal of card if the user rented a movie before deciding to rent another one
            pin = null;
            
            view.getInfoMainCard().add(view.payPanel(),"payment");
            view.getInfoCardL().show(view.getInfoMainCard(), "payment");
            
        }
        
        //closes the info/payment page
        if(e.getActionCommand().equals("exit")){
            view.getInfo().dispatchEvent(new WindowEvent(view.getInfo(), WindowEvent.WINDOW_CLOSING));
        }
        
        //goes back to the info page from the payment page
        if(e.getActionCommand().equals("infoBack")){
            view.getInfoMainCard().add(view.infoPanel(),"info");
            view.getInfoCardL().show(view.getInfoMainCard(), "info");
            
        }
        
        //Brings up an option pane for the customer to input their card details (simulates inserting a card on the kiosk)
        if(e.getActionCommand().equals("insertCard")){
            
            boolean loop = true;
            
            while(loop == true){
                
                loop = false; 
                int insertCard = JOptionPane.showConfirmDialog(view.getInfoMainCard(), view.inputCardDetails(), "Please enter your details:", JOptionPane.OK_CANCEL_OPTION);
                cardLast = view.getLastDigits().getText();
                pin = view.getCardPin().getText();


                if(insertCard==0){ //when the "ok" button is pressed on the option pane

                    if(!cardLast.matches("[a-zA-Z]*")&&(cardLast.length()==4)){ //checks if the user inputs a valid last 4 digits (no letters and must be 4 digits long)

                        if(!pin.matches("[a-zA-Z]*")&&(pin.length()==3)){ //checks if the user inputs a valid pin (no letters and must be 3 digits long)

                        }else{showMessageDialog(view.getInfoMainCard(), "Please enter a valid pin! (Must be 3 numbers and doesn't contain any letter)", "Error!", 0); loop = true;} //pops up an error if the input is incorrect

                    }else{showMessageDialog(view.getInfoMainCard(), "Please enter a valid 4 digit number! (Must be 4 numbers that matches the last 4 digits of your card and doesn't contain any letter)", "Error!", 0); loop = true;} //pops up an error if the input is incorrect


                }

            }
            
            User user = new User(cardLast, pin);
            boolean checkCard = model.userCardInput(user);
            
            
            if(checkCard){ //check if the inputs matches is a valid card (if it exists in the database)
                
                customerID = model.userID(user)[0];
                customerBalance = model.userID(user)[1]; //fetches the card balance
                movieID = model.movieID(movieCode); 
                
            }else{
                
                showMessageDialog(view.getInfoMainCard(), "Wrong Card Details!", "Error!", 0);
                cardLast = pin = null;
            }
            
        }
        
        
        //Saves the record of the rent (id of the customer, movie, and the date they rented) on the rent table in the database
        if(e.getActionCommand().equals("confirm")){

            if(!(cardLast==null) || !(pin==null)){ //checks if the user entered a vaild card first

                    if(customerBalance>2.99){  //checks if the customer has enough balance first and shows an error message if they don't

                        Rent rent = new Rent(movieID, customerID);

                        //Date setter==============================================================
                        Date date = new Date();
                        String dateFormat = "yyyy-MM-dd";
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
                        String sqlConvert = dateFormatter.format(date);

                        rent.setRentDate(sqlConvert);

                        //========================================================================

                        model.setRent(rent);
                        model.updateNumAvailRent(movieID); //updates the number of available stocks on the movie table on the database
                        model.deductBalance(customerID); //deducts 2.99 euros on the user balance

                        boolean emailSent = false; //boolean to determine if the email is sent or not

                        //Initializes email to null first to remove previous details===================================================

                        String email = null;

                        if(view.getEmailButton().isSelected()){ //checks if the check box is ticked 
                            System.out.println("Is selected");
                            email = view.getEmailInput().getText();

                            if(email.contains("@")&&email.contains(".")){ //simple validation for email; must contain '@' amd "."
                                
                                sendEmail(email);////////////////////
                                User userEmail = new User(email);
                                model.setemail(customerID, userEmail);
                                emailSent = true;

                            }else{System.out.println("Invalid E-mail!");}

                        }else{System.out.println("No email");}

                        // changes the message depending if the user choose to receive an email or not
                        if(emailSent){

                            JOptionPane.showMessageDialog(view.getInfoMainCard(), "Please don't forget to take the movie disc and your card!"
                                    + "\nRemember the movie code when returning the movie."
                                    + "\nMovie Code: "+infoMovie[0][5]
                                    + "\n(Email sent. Check your e-mail for receipt!)", "Transaction Complete!", 1);
                            view.getInfo().dispatchEvent(new WindowEvent(view.getInfo(), WindowEvent.WINDOW_CLOSING));

                        }else{

                            JOptionPane.showMessageDialog(view.getInfoMainCard(), "Please don't forget to take the movie disc and your card!"
                                    + "\nRemember the movie code when returning the movie."
                                    + "\nMovie Code: "+infoMovie[0][5], "Transaction Complete!", 1);
                            view.getInfo().dispatchEvent(new WindowEvent(view.getInfo(), WindowEvent.WINDOW_CLOSING));

                        }



                        cardLast = null; //re-initializes or 'removes' the card
                        pin = null; 



                    }else{

                        showMessageDialog(view.getInfoMainCard(), "Insufficient Balance!", "Error!", 0);
                        cardLast = null; //re-initializes or 'removes' the card
                        pin = null; 

                    }


            }else{showMessageDialog(view.getInfoMainCard(), "Please enter your payment details first!", "Error!", 0);}




        }
        
        //Generates a new card with a randomized card number, pin, and balance
        if(e.getActionCommand().equals("createCard")){
            
            User user = new User();
            user.generateCard();
            String genCardNumber = user.getCardNumber();
            String genPin = user.getPin();
            int genBalance = user.getBalance();
            
            System.out.println(genCardNumber + " " + genPin +" "+ genBalance);
            
            JOptionPane.showMessageDialog(view.getInfoMainCard(), "Insert the card now with these credentials:\n"
                    + "\nCard Number: "+genCardNumber+"\nCard Pin: "+ genPin + "\nRemaining Balance: "+genBalance
                    +"\n\nPlease remember your card details!\n"
                            + "(*Note: You only need the last 4 digits of your card to insert it.)", "Card Created!", 1);
            model.newCard(user);
            
        }
        
        //Shows/Hide the Email input field
        if(e.getActionCommand().equals("show/hide")){
            
            if(view.getEmailButton().isSelected()){
                
                view.getEmailLabel().setVisible(true);
                view.getEmailInput().setVisible(true);
                
            }else{
                
                view.getEmailLabel().setVisible(false);
                view.getEmailInput().setVisible(false);
                
            }
            
            System.out.println(view.getEmailButton().isSelected());
        }
        
        
        //listener for the submit button on the Return
        if(e.getActionCommand().equals("submit")){  
            

            cardLast = view.getLastDigits().getText();
            pin = view.getCardPin().getText();
            String movieCodeTemp = view.getMovieCode2().getText();
            User user = new User(cardLast, pin);
            boolean checkCard = model.userCardInput(user);

            System.out.println(cardLast+pin);
            if(!(cardLast==null) || !(pin==null)){ //checks if the user entered a vaild card first
                
                if(!cardLast.matches("[a-zA-Z]*")&&(cardLast.length()==4)){

                        if(!pin.matches("[a-zA-Z]*")&&(pin.length()==3)){

                            if(checkCard){
                
                                customerID = model.userID(user)[0];  //fetches the customer id
                                customerBalance = model.userID(user)[1]; //fetches the card balance
                                
                                
                                if(!movieCodeTemp.matches("[a-zA-Z]*")&&(movieCodeTemp.length()==4)){ //checks if the movie code input is a valid one (4 digits and doesn't contain any letter
                                    
                                    movieCode = Integer.parseInt(movieCodeTemp); //convert the movie code temp into an int
                                    if(model.checkMovieCode(movieCode)){
                                        
                                        movieID = model.movieID(movieCode); //make a model to check if the movie code is in the database
                                        if(model.checkValidRent(movieID, customerID)){
                                            
                                            //===================================================================================
                                            
                                            
                                            //=========Date Picker======================\\
                                            Date date = new Date();
                                            String dateFormat = "yyyy-MM-dd";
                                            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
                                            String returnDate = dateFormatter.format(date);
                                            System.out.println(returnDate);
                                            //==========================================\\
                                            
                                            
                                            
                                            model.setReturnDate(movieID,customerID,returnDate); //updates the return date on the database
                                            model.updateNumAvailReturn(movieID); //updates the number of available stocks on the database for the returned movie
                                            
                                            int rentID = model.rentID(movieID,customerID,returnDate); //fetch the rent id 
                                            int daysLate = model.countLateDaysReturn(rentID);         //fetch the difference between the current date and the rent date in the particular rent id 
                                            
                                            
                                            if(daysLate>0){ //checks if the number of days late is more than 0 and if it does, it charges the customer the appropriate amount 
                                                double charge = daysLate * 1.50;
                                                System.out.println("Charge: "+charge);
                                                model.deductLateCharge(customerID, charge);
                                                
                                                JOptionPane.showMessageDialog(view.getReturnn(), "Thank you for using Xtra-Vision Kiosk!"                  //Gives a message that the user is charged because of returning the movie late--
                                                    + "\nYou were charged €"+charge +" because you returned the disc "+daysLate+" days late (€1.50/day)"   //--together with the confirmation message
                                                        + "\nSee you again Soon!");
                                                
                                            }else{
                                                
                                                JOptionPane.showMessageDialog(view.getReturnn(), "Thank you for using Xtra-Vision Kiosk!"                  //confirmation message
                                                    + "\n See you again Soon!");
                                                
                                            }
                                            
                                            
                                            //==================================================================================
                                           
                                        //==============================================Error messages for validation=========================================\\    
                                        }else{System.out.println("Movie not rented with this card");}
                                        
                                        System.out.println("Success! 2 "+cardLast + " " + pin + " MovieCode: " + movieCode + "Customer ID "+customerID+" Movie ID "+ movieID);
                                        
                                    }else{showMessageDialog(view.getInfoMainCard(), "Wrong movie code inserted!", "Error!", 0);}
                                    
                                    
                                }else{
                                    showMessageDialog(view.getInfoMainCard(), "Please enter a valid movie code! (Must contain 4 digits and doesn't contain any letter)", "Error!", 0);
                                }

                            }else{

                                showMessageDialog(view.getInfoMainCard(), "Wrong Card Details!", "Error!", 0);
                                cardLast = pin = null;
                            }
                            
                        }else{showMessageDialog(view.getInfoMainCard(), "Please enter a valid pin! (Must be 3 numbers and doesn't contain any letter)", "Error!", 0);}

                    }else{showMessageDialog(view.getInfoMainCard(), "Please enter a valid 4 digit number! (Must be 4 numbers that matches the last 4 digits of your card and doesn't contain any letter)", "Error!", 0);}
                

                
            }else{showMessageDialog(view.getInfoMainCard(), "Please enter your payment details first!", "Error!", 0);}

            
        }
        
       if(e.getActionCommand().equals("exit1")){
           view.getReturnn().dispatchEvent(new WindowEvent(view.getReturnn(), WindowEvent.WINDOW_CLOSING));
       } 
       
       if(e.getActionCommand().equals("exit3")){
           view.getMain().dispatchEvent(new WindowEvent(view.getMain(), WindowEvent.WINDOW_CLOSING));
           view.welcome();
       }
        
        
        
    }
    

    //================================================================Method for sending an email================================================================\\
    private void sendEmail(String email){

    //Code is based from an answer in a stackoverflow forum
    //https://stackoverflow.com/questions/19493904/javax-mail-messagingexception-could-not-connect-to-smtp-host-localhost-port
    
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    String receiptMessage = "Thank you for renting!" //message for the email
           +"\n\n\nMovie: "+infoMovie[0][0]
            + "\nMovie Code: "+infoMovie[0][5]
           +"\nCost: €2.99"
            + "\nDate of transaction: "+new Date()
            +"\n\n\n\n\n\n"
            + "\nPlease remember to return it on time!"
            + "\nYou will be charged €1.50 per day up to maximum of 10 days if you failed to return it on time."
            + "\nYou can keep the disc after 10 days (You will be charged €15).";

    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", "465");
    props.setProperty("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.auth", "true");
    props.put("mail.debug", "true");
    props.put("mail.store.protocol", "pop3");
    props.put("mail.transport.protocol", "smtp");
    final String username = "oocsaad99@gmail.com"; //credentials for the email address that is used to send the email messages
    final String password = "samplepassword99";
    try{
      Session session = Session.getDefaultInstance(props, 
                          new Authenticator(){
                             protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                             }});

   // -- Create a new message --
      Message msg = new MimeMessage(session);

   // -- Set the FROM and TO fields --
      msg.setFrom(new InternetAddress("oocsaad99@gmail.com"));
      msg.setRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(email,false));  //sets the input as the receiver of the email
      msg.setSubject("Xtra-Vision Receipt");
      msg.setText(receiptMessage);
      msg.setSentDate(new Date());
      Transport.send(msg);
      System.out.println("Message sent.");
    }catch (MessagingException e){ 
      System.out.println("Error, cause: " + e);
    }

}
    
    
//================================================================Method for sending an email================================================================\\
    private void sendEmailLate(String email){

    //Code is based from an answer in a stackoverflow forum
    //https://stackoverflow.com/questions/19493904/javax-mail-messagingexception-could-not-connect-to-smtp-host-localhost-port
    
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    String receiptMessage = "You were charged €15 for not returning the movie before the due date." //message for the email
            + "\nDate of transaction: "+new Date()
            +"\n\n\n\n\n\n"
            + "\nPlease remember to return it on time next time!";

    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", "465");
    props.setProperty("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.auth", "true");
    props.put("mail.debug", "true");
    props.put("mail.store.protocol", "pop3");
    props.put("mail.transport.protocol", "smtp");
    final String username = "oocsaad99@gmail.com"; //credentials for the email address that is used to send the email messages
    final String password = "samplepassword99";
    try{
      Session session = Session.getDefaultInstance(props, 
                          new Authenticator(){
                             protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                             }});

   // -- Create a new message --
      Message msg = new MimeMessage(session);

   // -- Set the FROM and TO fields --
      msg.setFrom(new InternetAddress("oocsaad99@gmail.com"));
      msg.setRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(email,false));  //sets the input as the receiver of the email
      msg.setSubject("Xtra-Vision Receipt");
      msg.setText(receiptMessage);
      msg.setSentDate(new Date());
      Transport.send(msg);
      System.out.println("Message sent.");
    }catch (MessagingException e){ 
      System.out.println("Error, cause: " + e);
    }

}
    
    //========================================== "Never returned" checker ==========================================\\
    public void checkLateReturn(){
        
        int countReturn = model.countReturn();
        String[][] returnResult = model.returnChecker(countReturn);
        
        //Date setter==============================================================
            Date date = new Date();
            String dateFormat = "yyyy-MM-dd";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
            String returnDate = dateFormatter.format(date);
        //========================================================================
        
        int idRent; 
        int dateDifference;
        int numberCharged = 0;
        
        for(int i = 0; i<countReturn; i++){  //checks if the user hasn't returned a disc 10 days after its due
            
            idRent = Integer.parseInt(returnResult[i][0]);
            dateDifference = model.lateDaysCounter(countReturn, returnDate, idRent);
            
            if(dateDifference>=11){
                
                model.chargeLateMax(idRent); //sets each idRent to the model to charge them individualy if they haven't return the game after the due date
                numberCharged++; //counts the number of customers charged.
                System.out.println("Customer ID "+returnResult[i][1]+" charged");
                
                String email= model.fetchLateMaxEmail(idRent);
                if(!(email == null)){
                    sendEmailLate(email);
                }
                
            }
            
        }
        System.out.println("Customers charged: "+numberCharged);
        
    }
    
    
    //================================================================Getters================================================================\\
    
    public int getAllMovieCount() {
        return allMovieCount;
    }

    public String[][] getAllMovies() {
        return allMovies;
    }

    public int getCategoriesCount() {
        return categoriesCount;
    }

    public String[][] getCategoriesResult() {
        return categoriesResult;
    }

    public int getCountSearch() {
        return countSearch;
    }

    public String[][] getSearchMovieResult() {
        return searchMovieResult;
    }

    public String[][] getMoviesActive() {
        return moviesActive;
    }

    public String[][] getInfoMovie() {
        return infoMovie;
    }

    public Movies[] getMoviesAll() {
        return moviesAll;
    }

    public Movies[] getMoviesSearch() {
        return moviesSearch;
    }

    public String getActive() {
        return active;
    }
    
    
    
    
    
}
