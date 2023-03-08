/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Model {
    
    private int countResult;
    
    //================================================================All Movies================================================================\\
    //counts all the movies on the database
    public int allMoviesCount(){
        
        countResult = 0;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String countQuery = "Select count(*) from movie_list";
            
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(countQuery);
            rs.next();
            countResult = rs.getInt(1);
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
   
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return countResult;
    }
    
    //feth all the movies on the database
    public String[][] allMovies() 
    {
        int allMoviesCount = countResult;
        String[][] allMovieResult = new String[allMoviesCount][6];

        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String searchQuery = "Select * from movie_list order by movie_title";
            

            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                allMovieResult[row][0] = rs.getString("movie_title");
                allMovieResult[row][1] = rs.getString("movie_genre");
                allMovieResult[row][2] = rs.getString("release_year");
                allMovieResult[row][3] = rs.getString("num_avail");
                allMovieResult[row][4] = rs.getString("num_rented");
                allMovieResult[row][5] = rs.getString("movie_code");
                
                row++;
                
            }

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allMovieResult;
    }
    
    //================================================================Search================================================================\\
    //counts the search through name results
    public int countSearchTitle(String search){
        
        int countResult = 0;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String countQuery = "Select count(*) from movie_list where movie_title like '%" + search + "%'";
            
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(countQuery);
            rs.next();
            countResult = rs.getInt(1);
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
   
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return countResult;
    }
    
    //search for a match on movie title
    public String[][] searchTitle(String search)
    {
        
        int countMovie = countSearchTitle(search);
        String[][] searchMovieResult = new String[countMovie][6];

        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String searchQuery = "Select * from movie_list where movie_title like '%" + search + "%'";
            

            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            //takes the name and locaion information
            String[][] searchMovie = new String[countMovie][6];

            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                searchMovie[row][0] = rs.getString("movie_title");
                searchMovie[row][1] = rs.getString("movie_genre");
                searchMovie[row][2] = rs.getString("release_year");
                searchMovie[row][3] = rs.getString("num_avail");
                searchMovie[row][4] = rs.getString("num_rented");
                searchMovie[row][5] = rs.getString("movie_code");
                
                row++;
                
            }
            
            searchMovieResult = searchMovie;

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return searchMovieResult;
    }
    
    //================================================================Categories================================================================\\
    //cout the results for the categories function first
    public int categoriesMoviesCount(String categories) 
    {
        
        countResult=0;
        
        String cateTypes = categories;
        String searchQuery=null;
        System.out.println("count result"+ cateTypes);
        switch(cateTypes){
            case "action": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='action' or movie_genre='adventure'"; break;
            case "comedy": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre ='comedy'";break;
            case "drama": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='drama'";break;
            case "fantasy": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='fantasy'";break;
            case "horror": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='horror'";break;
            case "mystery": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='mystery'";break;
            case "romance": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='romance'";break;
            case "scifi": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='sci-fi'";break;
            case "thriller": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='thriller'";break;
            case "western": searchQuery = "SELECT count(*) FROM movie_list WHERE movie_genre='western'";break;
        }
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";

            System.out.println("Sort Type: "+cateTypes+" Query: "+searchQuery);
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);

            rs.next();
            countResult=rs.getInt(1);

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return countResult;
    }
    
    //fetch all the movies that matches the chosen category
    public String[][] categoriesMovies(String categories) 
    {
        
        int allMoviesCount = countResult;
        String[][] cateResult = new String[allMoviesCount][6];
        String cateTypes = categories;
        String searchQuery=null;
        switch(cateTypes){
            case "action": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='action' or movie_genre='adventure'"; break;
            case "comedy": searchQuery = "SELECT * FROM movie_list WHERE movie_genre ='comedy'";break;
            case "drama": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='drama'";break;
            case "fantasy": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='fantasy'";break;
            case "horror": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='horror'";break;
            case "mystery": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='mystery'";break;
            case "romance": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='romance'";break;
            case "scifi": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='sci-fi'";break;
            case "thriller": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='thriller'";break;
            case "western": searchQuery = "SELECT * FROM movie_list WHERE movie_genre='western'";break;
        }
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";

            System.out.println("Sort Type: "+cateTypes+" Query: "+searchQuery);
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                cateResult[row][0] = rs.getString("movie_title");
                cateResult[row][1] = rs.getString("movie_genre");
                cateResult[row][2] = rs.getString("release_year");
                cateResult[row][3] = rs.getString("num_avail");
                cateResult[row][4] = rs.getString("num_rented");
                cateResult[row][5] = rs.getString("movie_code");
                
                row++;
                
            }
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return cateResult;
    }
    
    //================================================================Sort================================================================\\
    //Sorts all the movies to the selected order
    public String[][] allMoviesSort(String sort) 
    {
        
        int allMoviesCount = countResult;
        String[][] sortMoviesResult = new String[allMoviesCount][6];
        String sortType = sort;
        String searchQuery=null;
        switch(sortType){
            case "Name": searchQuery = "Select * from movie_list order by movie_title";break;
            case "Popularity": searchQuery = "Select * from movie_list order by num_rented";break;
            case "Genre": searchQuery = "Select * from movie_list order by movie_genre";break;
            case "Release Date": searchQuery = "Select * from movie_list order by release_year";break;
        }
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            System.out.println("Sort Type: "+sortType+" Query: "+searchQuery);
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                sortMoviesResult[row][0] = rs.getString("movie_title");
                sortMoviesResult[row][1] = rs.getString("movie_genre");
                sortMoviesResult[row][2] = rs.getString("release_year");
                sortMoviesResult[row][3] = rs.getString("num_avail");
                sortMoviesResult[row][4] = rs.getString("num_rented");
                sortMoviesResult[row][5] = rs.getString("movie_code");
                
                row++;
                
            }
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return sortMoviesResult;
    }
    
    //sorts the search results
    public String[][] searchTitleSort(String search, String sort)
    {
        
        int countMovie = countSearchTitle(search);
        String[][] searchMovieResult = new String[countMovie][6];
        String sortType = sort;
        String searchQuery=null;
        switch(sortType){
            case "Name": searchQuery = "Select * from movie_list where movie_title like '%" + search + "%'" + " order by movie_title";break;
            case "Popularity": searchQuery = "Select * from movie_list where movie_title like '%" + search + "%'" + " order by num_rented";break;
            case "Genre": searchQuery = "Select * from movie_list where movie_title like '%" + search + "%'" + " order by movie_genre";break;
            case "Release Date": searchQuery = "Select * from movie_list where movie_title like '%" + search + "%'" + " order by release_year";break;
        }
            
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";

            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                searchMovieResult[row][0] = rs.getString("movie_title");
                searchMovieResult[row][1] = rs.getString("movie_genre");
                searchMovieResult[row][2] = rs.getString("release_year");
                searchMovieResult[row][3] = rs.getString("num_avail");
                searchMovieResult[row][4] = rs.getString("num_rented");
                searchMovieResult[row][5] = rs.getString("movie_code");
                
                row++;
                
            }
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return searchMovieResult;
    }
    
    //================================================================Renting================================================================\\
    //Model for the info pane
    public String[][] infoMovies(int movieCode) 
    {
        
        String[][] allMovieResult = new String[1][7];

        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String searchQuery = "Select * from movie_list where movie_code = "+movieCode;
            

            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                allMovieResult[0][0] = rs.getString("movie_title");
                allMovieResult[0][1] = rs.getString("movie_genre");
                allMovieResult[0][2] = rs.getString("release_year");
                allMovieResult[0][3] = rs.getString("num_avail");
                allMovieResult[0][4] = rs.getString("num_rented");
                allMovieResult[0][5] = rs.getString("movie_code");
                allMovieResult[0][6] = rs.getString("movie_info");
                row++;
                
            }

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return allMovieResult;
    }
    
    //fetching the movie ID
    public int movieID(int movieCode)
    {
        int movieID=0;
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select * from movie_list where movie_code = "+movieCode;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set
            rs.next();
            movieID = rs.getInt("id_movie");
            

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return movieID;
        
    }
    
    //insert a row int the rent table with the details from the Rent object
    public boolean setRent(Rent rent)
    {
        boolean result = false;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String update = "INSERT INTO rent (id_customer,id_movie,date_rent)" +
                    "VALUES ("+rent.getCustomerID()+","+rent.getMovieID()+",'"+rent.getRentDate()+"');";

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            stmt.executeUpdate(update);
            
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: ");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return result;
    }
    
    //checks if the card details input matches a row in the database (if a card exists)
    public boolean userCardInput(User user)
    {
        
        boolean result = false;
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "select * from movie_customers where card_num like '%"+user.getCardNumber()+"' and card_pin like '"+user.getPin()+"'";

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set
            if (rs.next()) {
                result = true;
            }

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return result;
        
    }
    
    //Fetch the customer id and balance of each users that mathces the input
    public int[] userID(User user)
    {
        int[] userNumber= new int[2]; //stores the userNumber in index 0 and the balance on index 1
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select * from movie_customers where card_num like '%"+user.getCardNumber()+"' and card_pin like '"+user.getPin()+"'";

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set
            rs.next();
            userNumber[0] = rs.getInt("id_customer");
            userNumber[1] = rs.getInt("balance");
            
            

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return userNumber;
        
    }
    
    //update the number of available disk in the kiosk and also adds 1 to the number of time the movie is rented
    public void updateNumAvailRent(int movieID) 
    {
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Update movie_list "
                    + "Set num_avail = num_avail-1, num_rented = num_rented + 1 "
                    + "where id_movie = "+movieID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);

            // Close the statement and the connection
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (num Avail):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    //charges the customer 2.99 euros for their rent and add 1 to the "times_used" column (the number of times they rented)
    public void deductBalance(int customerID) 
    {
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Update movie_customers "
                    + "Set balance = balance - 2.99, times_used = times_used + 1 "
                    + "where id_customer = "+customerID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);
            
            // Close the statement and the connection
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception(deduct)");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    //creates a new card based on the generated card details and put it in the database 
    public boolean newCard(Card cardDetails)
    {
        boolean result = false;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String update = "INSERT INTO movie_customers (card_num,card_pin, balance,times_used)" +
                    "VALUES ('"+cardDetails.getCardNumber()+"','"+cardDetails.getPin()+"','"+cardDetails.getBalance()+"',0);";

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            stmt.executeUpdate(update);
            
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: ");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return result;
    }
    
    //================================================================RETURN================================================================\\
    
    //checks if the movie code input is linked to any movie in the database
     public boolean checkMovieCode(int movieCode)
    {
        boolean result = false;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select * from movie_list where movie_code = " + movieCode;

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                result = true;
            }
            
            rs.close();
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: ");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return result;
    }
    
    //checks if the movie isn't returned yet by the user of the card inserted
    public boolean checkValidRent(int movieID, int customerID)
    {
        boolean result = false;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select * from rent where id_movie = " + movieID + " and id_customer = " + customerID + " and date_return is null"; //the date return should be null meaning the movie isn't returned yet.
                                                                                                                                               //it avoids a user "returning" a movie that was already returned

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                result = true;
            }
            
            rs.close();
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: afasdsadfa");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return result;
    }
    
    //update the number of available disk in the kiosk and also adds 1 to the number of time the movie is rented
    public void updateNumAvailReturn(int movieID) 
    {
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Update movie_list "
                    + "Set num_avail = num_avail+ 1 "
                    + "where id_movie= "+movieID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);

            // Close the statement and the connection
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (num Avail):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }

    //Updates the date_return in the rent table in the database if all the details are valid
    public void setReturnDate(int movieID, int customerID, String returnDate) 
    {
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "UPDATE rent set date_return = '"+returnDate+ "' WHERE id_customer ="
                    +customerID + " and id_movie = " +movieID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);
            
            // Close the statement and the connection
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (num Avail):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    //saves the email if the user input their email
    public void setemail(int customerID,User userEmail) 
    {
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "UPDATE movie_customers set email = '"+userEmail.getEmail()+ "' WHERE id_customer ="
                    +customerID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);
            
            // Close the statement and the connection
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (num Avail):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    //================================================================Late Return Charges================================================================\\
    
    //fetch the rent id based on the rent details the customer provided and the return date
    public int rentID(int movieID, int customerID, String returnDate)
    {
        int rentID = 0; //stores the userNumber in index 0 and the balance on index 1
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select * from rent where id_movie = "+ movieID + " and id_customer = "+ customerID + " and date_return = '" + returnDate + "'";

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set
            rs.next();
            rentID = rs.getInt(1);
            

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (rentID):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return rentID;
        
    }
    
    //Counts the number of days past the due date
    public int countLateDaysReturn(int rentID)
    {
        int numDaysLate=0;
        
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "SELECT DATEDIFF((select date_return from rent where id_rent = " + rentID + "),(select date_rent from rent where id_rent = " + rentID + ")) - 1"; //the formula has (-1) because the user should return the movie the day 
                                                                                                                                                                             //after they rented it, so that doesn't count as a "late day"
            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set
            rs.next();
            numDaysLate = rs.getInt(1);
            

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception (daysLate):");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return numDaysLate;
        
    }
    
    //Takes the customer ID and the total charge based on the number of days past the due date and deducts it on the card balance of the customer
    public void deductLateCharge(int customerID, double charge) //charges the customer 2.99 euros for their rent and add 1 to the "times_used" column (the number of times they rented)
    {
        try {

            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Update movie_customers "
                    + "Set balance = balance - "+charge
                    + "where id_customer = "+customerID;

            // Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Execute the query
            stmt.executeUpdate(query);
            
            // Close the statement and the connection
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception(deduct)");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    //================================================================"Never returned" checker"================================================================\\
    //Charges 15 euros automatically on users that didn't returned their movies for 10 days after the due
    
    //counts the result of rents that doesn't have a return date yet
    public int countReturn(){
        
        int countResult = 0;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String countQuery = "Select count(*) from rent where date_return is null";
            
            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(countQuery);
            rs.next();
            countResult = rs.getInt(1);
            
            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
   
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return countResult;
    }
    
    //fetch the results of rents that doesn't have a return date yet
    public String[][] returnChecker(int count) 
    {
        System.out.println(count);
        String[][] returnResult = new String[count][5];

        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String searchQuery = "Select * from rent where date_return is null";
            

            Connection conn = DriverManager.getConnection(dbServer,dbUser,dbPassword);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(searchQuery);
            int row = 0;
            
            while (rs.next()) {                     
                
                returnResult[row][0] = rs.getString("id_rent");
                returnResult[row][1] = rs.getString("id_customer");
                returnResult[row][2] = rs.getString("id_movie");
                returnResult[row][3] = rs.getString("date_rent");
                returnResult[row][4] = rs.getString("date_return");
                row++;
                
            }

            // Close the result set, statement and the connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return returnResult;
    }
    
    //Gets the difference between the current date and the rent date 
    public int lateDaysCounter(int count, String returnDate, int idRent)
    {
        int dateDifference = 0;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "SELECT DATEDIFF('"+returnDate+"',(select date_rent from rent where id_rent = "+idRent+"));";

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            dateDifference=  rs.getInt(1);
                
            
            rs.close();
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: afasdsadfa");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return dateDifference;
    }
    
    
    
    //charges the customer if the difference between the current date and the rent date is more than 11.
    //(11 days because they have a day to return the movie after they rent it, then they have 10 days to
    //return it before they are charged the maximum 15 euros. They will be automatically charged even if
    //they don't access the system using this method
    public boolean chargeLateMax(int idRent)
    {
        boolean result = false;
        
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String update = "Update rent join movie_customers\n" +
                                "on rent.id_customer = movie_customers.id_customer\n" +
                                "set movie_customers.balance = movie_customers.balance-15, rent.date_return = '2999-12-31'\n" + //set the return date to 2999-12-31 to indicate that the disc isn't returned and charges the
                                "where id_rent = "+idRent+";";                                                                  //customer 15 euros

            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            stmt.executeUpdate(update);
            
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: ");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return result;
    }
    
    //fetch the email of the user if it's not null
    public String fetchLateMaxEmail(int idRent)
    {
        String email = null;
        try
        {
            String dbServer = "jdbc:mysql://52.50.23.197:3306/Jon_2019395?useSSL=false";
            String dbUser = "Jon_2019395";
            String dbPassword = "2019395";
            String query = "Select email from rent join movie_customers\n" +
                            "on rent.id_customer = movie_customers.id_customer\n" +
                            "where id_rent = "+idRent+";";                                                                  
            
            //Get a connection to the database
            Connection conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);

            //get a statement from the connection
            Statement stmt = conn.createStatement();
            
            //execute the query
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            email=  rs.getString("email");
            
            
            stmt.close();
            conn.close();
        
        }catch (SQLException se) 
        {
            System.out.println("SQL Exception: ");
            
            //Loop through the Exceptions
            while(se != null) 
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                
                se = se.getNextException();
            } 
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        return email;
    }
}
    

