/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author leojk
 */
public class View extends JFrame {
    
    
    ViewController viewController;
    
    private JFrame main;
    private JFrame welcome;
    private JFrame returnn;
    private JFrame faq;
    private JFrame info;
    private JPanel infoMainCard;
    private JPanel movieSection;
    private JPanel payPanel;
    private JLabel emailLabel;
    private JComboBox sort;
    private JCheckBox emailButton;
    private JTextField searchBar;
    private JTextField emailInput;
    private JTextField lastDigits;
    private JTextField cardPin;
    private CardLayout movieSectionL;
    private CardLayout infoCardL;
    private ScrollPane sp;
    
    
    private Font txt;
    private Font txt1;

    
    private int countResult;
    private int countIndex;
    private String headerLabel; //shows what the user searched/what the movie panel is currently displaying
    private String[] movieID;
    private String panelActive; //for the rent button
    

    public View(ViewController viewController){  
        this.viewController = viewController;
        // We encapsulated the building process of the window
        welcome();  //calls the home page
        
    }
    
    //welcome page
    public void welcome(){
        
        welcome = new JFrame();
            welcome.setVisible(true);
            welcome.setSize(1600, 900);
            welcome.setTitle("Xtra Vision");
            BorderLayout welcomeLayout = new BorderLayout();
            welcome.setLayout(welcomeLayout);
        //header     
        Font tFont = new Font("Arial Black",Font.BOLD,50);
        Font tFont1 = new Font("Arial Black",Font.BOLD,20);
        
        JPanel header = new JPanel();
        JLabel xtra = new JLabel("Welcome to");
            xtra.setFont(tFont);
            xtra.setForeground(new Color(255, 228, 0));
            
            welcome.add(header, BorderLayout.PAGE_START);
            
        ImageIcon logoxtra = new ImageIcon(getClass().getResource("images/logoxtravision.png"));
            JLabel logo = new JLabel(logoxtra);
            GridLayout head = new GridLayout(2,1);
                header.setLayout(head);
                header.setBackground(new Color(160,0,0));//personalized colors
            header.add(xtra);
            header.add(logo);
            
        //center    
        JButton rent = new JButton("Rent a movie");
            rent.addActionListener(viewController);
            rent.setActionCommand("goMain");
        JButton retur = new JButton("Return a movie");
            retur.addActionListener(viewController);
            retur.setActionCommand("retur");
            
            JPanel rr = new JPanel();
                welcome.add(rr, BorderLayout.CENTER);
                rr.setBackground(new Color(160,0,0));
            GridLayout rentreturn = new GridLayout(1,2);
                rr.setLayout(rentreturn);
            rr.add(rent);
            rr.add(retur);
            
        //footer
        JPanel footer = new JPanel();
            welcome.add(footer, BorderLayout.PAGE_END);
            footer.setBackground(new Color(160,0,0));
        JLabel txtfooter = new JLabel("Xtra easy and Xtra fast way to rent a movie!");
            txtfooter.setFont(tFont1);
        footer.add(txtfooter);
            txtfooter.setForeground(new Color(255, 228, 0));   
            
        welcome.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcome.validate();
        welcome.repaint();  
    }
    
    //the main page for the rent
    public void main() {
        //main frame (will change only the panels
        main = new JFrame();
            main.setVisible(true);
            main.setSize(1600, 900);
            main.setTitle("Xtra Vision");
            BorderLayout mainLayout = new BorderLayout();
            main.setLayout(mainLayout);
            
        txt = new Font("Arial Black", Font.PLAIN,18);
        main.add(searchBar(), BorderLayout.PAGE_START);   
        main.add(categories(), BorderLayout.LINE_START);    

        movieSectionL = new CardLayout(); //cardlayout manager
        movieSection = new JPanel();
        movieSection.setLayout(movieSectionL);
        main.add(movieSection,BorderLayout.CENTER);
        movieSection.add(moviePanel(),"moviesHome");

        txt1 = new Font("Arial Black", Font.BOLD, 30);

        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        main.validate();
        main.repaint();      
        
            
    }
    
    //================================================================Movie Cards================================================================\\
    
    //card for showing all the movies
    public JPanel moviePanel() {
        
        panelActive = "movieAll";
        BorderLayout movieL = new BorderLayout();
        JPanel moviePanel = new JPanel();
            moviePanel.setLayout(movieL);
        moviePanel.add(moviePanelHeader(),BorderLayout.NORTH);
        
        sp = new ScrollPane();
        GridLayout movieListL = new GridLayout(0,5); //makes the grid to have 5 rows and dynamic number of columns
        JPanel movieListP = new JPanel();  //panel for the movie list (images and title tiles
            movieListP.setLayout(movieListL);
            moviePanel.add(sp, BorderLayout.CENTER);
            sp.add(movieListP);
            
        countResult = viewController.getAllMovieCount();
        countIndex = 0; //initialized the count index
        movieID = new String[countResult];
        
        JPanel[] movieArray = new JPanel[countResult];
        
        for(int i=0;i<countResult;i++){

            movieArray[i] = new JPanel();
            JPanel movieTile = new JPanel();
                JPanel movieDetails = new JPanel();
                movieDetails.setLayout(new BoxLayout(movieDetails, BoxLayout.Y_AXIS));
                movieTile.add(movieDetails);
                    ImageIcon movieImage = new ImageIcon(getClass().getResource("images/"+viewController.getAllMovies()[i][5]+".jpg"));
                    movieImage.setImage(movieImage.getImage().getScaledInstance(250, 375, java.awt.Image.SCALE_SMOOTH));
                    JLabel itt = new JLabel(movieImage);
                    movieDetails.add(itt);
                    JLabel movieTitle = new JLabel(viewController.getAllMovies()[i][0]);
                    movieDetails.add(movieTitle);
                    JLabel movieDate = new JLabel(viewController.getAllMovies()[i][2]);
                    movieDetails.add(movieDate);
                    JLabel movieGenre = new JLabel(viewController.getAllMovies()[i][1]);
                    movieDetails.add(movieGenre);
                    movieID[i] = viewController.getAllMovies()[i][5];
                    JButton rentB = new JButton("Info");
                    rentB.addActionListener(viewController);
                    rentB.setActionCommand("info"+movieID[i]);
                    movieDetails.add(rentB);

          movieArray[i].add(movieTile);
          movieListP.add(movieArray[i]);
        }

        return moviePanel;
    }
    
    //card for showing the search result
    public JPanel movieSearchPanel(){

        panelActive = "movieSearch";
        BorderLayout movieL = new BorderLayout();
        JPanel movieSearchPanel = new JPanel();
            movieSearchPanel.setLayout(movieL);
            
        JPanel moviePanel = new JPanel();
            moviePanel.setLayout(movieL);

        movieSearchPanel.add(moviePanelHeader(),BorderLayout.NORTH);
        sp = new ScrollPane();
        GridLayout movieListL = new GridLayout(0,5); //makes the grid to have 5 rows and dynamic number of columns
        JPanel movieListP = new JPanel();  //panel for the movie list (images and title tiles
            movieListP.setLayout(movieListL);
            movieSearchPanel.add(sp, BorderLayout.CENTER);
            sp.add(movieListP);
            
        countResult = viewController.getCountSearch();
        countIndex = 0; //initialized the count index
        movieID = new String[countResult];
        
        JPanel[] movieArray = new JPanel[countResult];
        
        for(int i=0;i<countResult;i++){

        movieArray[i] = new JPanel();
        JPanel movieTile = new JPanel();
            JPanel movieDetails = new JPanel();
            movieDetails.setLayout(new BoxLayout(movieDetails, BoxLayout.Y_AXIS));
            movieTile.add(movieDetails);
                ImageIcon movieImage = new ImageIcon(getClass().getResource("images/"+viewController.getSearchMovieResult()[i][5]+".jpg"));
                movieImage.setImage(movieImage.getImage().getScaledInstance(250, 375, java.awt.Image.SCALE_SMOOTH));
                JLabel itt = new JLabel(movieImage);
                movieDetails.add(itt);
                JLabel movieTitle = new JLabel(viewController.getSearchMovieResult()[i][0]);
                movieDetails.add(movieTitle);
                JLabel movieDate = new JLabel(viewController.getSearchMovieResult()[i][2]);
                movieDetails.add(movieDate);
                JLabel movieGenre = new JLabel(viewController.getSearchMovieResult()[i][1]);
                movieDetails.add(movieGenre);
                movieID[i] = viewController.getSearchMovieResult()[i][5];
                JButton rentB = new JButton("Info");
                rentB.addActionListener(viewController);
                rentB.setActionCommand("info"+movieID[i]);
                movieDetails.add(rentB);




      movieArray[i].add(movieTile);
      movieListP.add(movieArray[i]);
    }
        
            
        return movieSearchPanel;
    }
    
    //card for sorting all the movies
    public JPanel moviePanelSort(){ //View panel for all the movies
        
        panelActive = "movieSort";
        BorderLayout movieL = new BorderLayout();
        JPanel moviePanelSort = new JPanel();
            moviePanelSort.setLayout(movieL);
        
        sp = new ScrollPane();
        GridLayout movieListL = new GridLayout(0,5); //makes the grid to have 5 rows and dynamic number of columns
        JPanel movieListP = new JPanel();  //panel for the movie list (images and title tiles
            movieListP.setLayout(movieListL);
            moviePanelSort.add(sp, BorderLayout.CENTER);
            sp.add(movieListP);
            
        if(viewController.getActive().equalsIgnoreCase("all")){
            
            countResult = viewController.getAllMovieCount();
            
        }else if(viewController.getActive().equalsIgnoreCase("search")){
            
            countResult = viewController.getCountSearch();
            
        }
        
        
        countIndex = 0; //initialized the count index
        movieID = new String[countResult];
        
        JPanel[] movieArray = new JPanel[countResult];
        
        if(countResult == 0){
            System.out.println("No movies found"); //will change later
        }
        else{
            for(int i=0;i<countResult;i++){
                
                movieArray[i] = new JPanel();
                JPanel movieTile = new JPanel();
//                movieTile.setBorder(new EmptyBorder(10,10,10,10));
                    JPanel movieDetails = new JPanel();
                    movieDetails.setLayout(new BoxLayout(movieDetails, BoxLayout.Y_AXIS));
                    movieTile.add(movieDetails);
                        ImageIcon movieImage = new ImageIcon(getClass().getResource("images/"+viewController.getMoviesActive()[i][5]+".jpg"));
                        movieImage.setImage(movieImage.getImage().getScaledInstance(250, 375, java.awt.Image.SCALE_SMOOTH));
                        JLabel itt = new JLabel(movieImage);
                        movieDetails.add(itt);
                        JLabel movieTitle = new JLabel(viewController.getMoviesActive()[i][0]);
                        movieDetails.add(movieTitle);
                        JLabel movieDate = new JLabel(viewController.getMoviesActive()[i][2]);
                        movieDetails.add(movieDate);
                        JLabel movieGenre = new JLabel(viewController.getMoviesActive()[i][1]);
                        movieDetails.add(movieGenre);
                        movieID[i] = viewController.getMoviesActive()[i][5];
                        JButton rentB = new JButton("Info");
                        rentB.addActionListener(viewController);
                        rentB.setActionCommand("info"+movieID[i]);
                        movieDetails.add(rentB);
                
                
                        
              movieArray[i].add(movieTile);
              movieListP.add(movieArray[i]);
            }
        }
        
        moviePanelSort.add(moviePanelHeader(),BorderLayout.NORTH);

        return moviePanelSort;
    }
    
    //card for the chosen categories
    public JPanel movieCategories() {
        
        panelActive = "movieCategory";
        BorderLayout movieL = new BorderLayout();
        JPanel movieCategories = new JPanel();
            movieCategories.setLayout(movieL);
        
        movieCategories.add(categoryPanelHeader(),BorderLayout.NORTH);
        sp = new ScrollPane();
        GridLayout movieListL = new GridLayout(0,5); //makes the grid to have 5 rows and dynamic number of columns
        JPanel movieListP = new JPanel();  //panel for the movie list (images and title tiles
            movieListP.setLayout(movieListL);
            movieCategories.add(sp, BorderLayout.CENTER);
            sp.add(movieListP);
         
        countResult = viewController.getCategoriesCount();
        countIndex = 0; //initialized the count index
        movieID = new String[countResult];
        
        JPanel[] movieArray = new JPanel[countResult];
        
        
        for(int i=0;i<countResult;i++){

            movieArray[i] = new JPanel();
            JPanel movieTile = new JPanel();
                JPanel movieDetails = new JPanel();
                movieDetails.setLayout(new BoxLayout(movieDetails, BoxLayout.Y_AXIS));
                movieTile.add(movieDetails);
                    ImageIcon movieImage = new ImageIcon(getClass().getResource("images/"+viewController.getCategoriesResult()[i][5]+".jpg"));
                    movieImage.setImage(movieImage.getImage().getScaledInstance(250, 375, java.awt.Image.SCALE_SMOOTH));
                    JLabel itt = new JLabel(movieImage);
                    movieDetails.add(itt);
                    JLabel movieTitle = new JLabel(viewController.getCategoriesResult()[i][0]);
                    movieDetails.add(movieTitle);
                    JLabel movieDate = new JLabel(viewController.getCategoriesResult()[i][2]);
                    movieDetails.add(movieDate);
                    JLabel movieGenre = new JLabel(viewController.getCategoriesResult()[i][1]);
                    movieDetails.add(movieGenre);
                    movieID[i] = viewController.getCategoriesResult()[i][5];
                    JButton rentB = new JButton("Info");
                    rentB.addActionListener(viewController);
                    rentB.setActionCommand("info"+movieID[i]);
                    movieDetails.add(rentB);


          movieArray[i].add(movieTile);
          movieListP.add(movieArray[i]);
        }
        
        return movieCategories;
}
    
 //================================================================"Return" page================================================================\\
 JTextField movieCode2;
        public void returnn(){
        returnn = new JFrame();
        returnn.setVisible(true);
        returnn.setSize(700, 700);
        returnn.setTitle("Xtra Vision - Movie Info");
        BorderLayout rentBorder = new BorderLayout();
        returnn.setLayout(rentBorder);
        
        ImageIcon logoxtra = new ImageIcon(getClass().getResource("images/logoxtravision.png"));        
                logoxtra.setImage(logoxtra.getImage().getScaledInstance(680, 125, 100));    
                JLabel logo = new JLabel(logoxtra);
                    returnn.add(logo, BorderLayout.PAGE_START);
                    
        
        JPanel inputCardDetails = new JPanel(new GridLayout(8,1,5,5));
        returnn.add(inputCardDetails, BorderLayout.CENTER);
            JLabel movieCode = new JLabel("Insert the movie code");
            movieCode2 = new JTextField();
            JLabel cardNumLabel = new JLabel("Please enter the last 4 digits of your card");
            lastDigits = new JTextField(4);
            JLabel cardPinLabel = new JLabel("Please enter your pin");
            cardPin = new JTextField(4);
            inputCardDetails.add(movieCode);
            inputCardDetails.add(movieCode2);
            inputCardDetails.add(cardNumLabel);
            inputCardDetails.add(lastDigits);
            inputCardDetails.add(cardPinLabel);
            inputCardDetails.add(cardPin);
            
        JButton submit = new JButton("Submit");
            submit.addActionListener(viewController);
            submit.setActionCommand("submit");
            inputCardDetails.add(submit);
                    
        JButton back = new JButton("Back");
            back.addActionListener(viewController);
            back.setActionCommand("exit1"); //to exit the return screen and go back yo the welcome screen
            inputCardDetails.add(back);
          
                    
        returnn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        returnn.validate();
        returnn.repaint();

    }   
    
    
    //================================================================Frequently Asked Question Frame================================================================\\
    public void faq(){
        faq = new JFrame();
            faq.setVisible(true);
            faq.setSize(1600, 900);
            faq.setTitle("Xtra Vision");
            BorderLayout faqBorder = new BorderLayout();
            faq.setLayout(faqBorder);
            ImageIcon logoxtra = new ImageIcon(getClass().getResource("images/logoxtravision.png"));        
                logoxtra.setImage(logoxtra.getImage().getScaledInstance(1350, 175, 100));    
                JLabel logo = new JLabel(logoxtra);
                    faq.add(logo, BorderLayout.PAGE_START);
        
        JPanel faqP1 = new JPanel();
        GridLayout faqGrid = new GridLayout(5, 2);
        faqP1.setLayout(faqGrid);
            faq.add(faqP1, BorderLayout.CENTER);
        
        JLabel q1 = new JLabel("How long can I keep the movie?");
            q1.setFont(txt1);
            faqP1.add(q1);
        JLabel a1 = new JLabel("You can keep the movie for 3 days");
            a1.setFont(txt);
            faqP1.add(a1);
            
        JLabel q2 = new JLabel("How much cost each movie?");
            q2.setFont(txt1);
            faqP1.add(q2);
        JLabel a2 = new JLabel("Each movie costs £3,99");
            a2.setFont(txt);
            faqP1.add(a2);
        
        JLabel q3 = new JLabel("What happen if I do not return the disk?");
            q3.setFont(txt1);
            faqP1.add(q3);
        JLabel a3 = new JLabel("We will automatically charge on your credit card the security deposit");
            a3.setFont(txt);
            faqP1.add(a3);
            
        JLabel q4 = new JLabel("Why do I need to pay a security deposit?");
            q4.setFont(txt1);
            faqP1.add(q4);
        JLabel a4 = new JLabel("We change a security deposit in case any damage cause on the disk, scretches for example");
            a4.setFont(txt);
            faqP1.add(a4);
            
        JLabel q5 = new JLabel("How much is the security deposit?");
            q5.setFont(txt1);
            faqP1.add(q5);
        JLabel a5 = new JLabel("The security desposit is £7,99");
            a5.setFont(txt);
            faqP1.add(a5);
        JButton back = new JButton("Back");
            back.addActionListener(viewController);
            back.setActionCommand("back");
            faq.add(back, BorderLayout.PAGE_END);
            
            
        faq.validate();
        faq.repaint();
                
          
    }
    
    //================================================================panel elements================================================================\\
    
    //For the search bar and the buttons beside it
    public JPanel searchBar(){
    
        JPanel search = new JPanel();

            search.setBackground(new Color(160,0,0));
            GridLayout searchGrid = new GridLayout(1,5);
            search.setLayout(searchGrid);
            //image    
            ImageIcon logoxtra = new ImageIcon(getClass().getResource("images/logoxtravision.png"));        
                logoxtra.setImage(logoxtra.getImage().getScaledInstance(150, 45, 100));    
                JLabel logo = new JLabel(logoxtra);
                    search.add(logo);

                searchBar = new JTextField();
                    search.add(searchBar);   
                JButton searchButton = new JButton("Search");
                    search.add(searchButton);
                    searchButton.addActionListener(viewController);
                    searchButton.setActionCommand("search");
                JButton cart = new JButton("Home");
                    search.add(cart);
                    cart.addActionListener(viewController);
                    cart.setActionCommand("home");
                JButton exit = new JButton("Exit");
                    exit.addActionListener(viewController);
                    exit.setActionCommand("exit3");
                    search.add(exit);

                
        return search;        
    }
    
    //for the side bar (categories button)
    public JPanel categories(){
        JPanel categories = new JPanel();
        categories.setBackground(new Color(160,0,0));

        GridLayout menuGrid = new GridLayout(13,1);
            categories.setLayout(menuGrid);

        JLabel cate = new JLabel("Categories");
            cate.setFont(txt);
            cate.setForeground(new Color(255, 228, 0)); 
            categories.add(cate);

        JButton action = new JButton("Action and Adventure");
            action.addActionListener(viewController);
            action.setActionCommand("action");
        JButton comedy = new JButton("Comedy");
            comedy.addActionListener(viewController);
            comedy.setActionCommand("comedy");
        JButton drama = new JButton("Drama");
            drama.addActionListener(viewController);
            drama.setActionCommand("drama");
        JButton fantasy = new JButton("Fantasy");
            fantasy.addActionListener(viewController);
            fantasy.setActionCommand("fantasy");
        JButton horror = new JButton("Horror");
            horror.addActionListener(viewController);
            horror.setActionCommand("horror");
        JButton mystery = new JButton("Mystery");
            mystery.addActionListener(viewController);
            mystery.setActionCommand("mystery");
        JButton romance = new JButton("Romance");
            romance.addActionListener(viewController);
            romance.setActionCommand("romance");
        JButton scifi = new JButton("Sci-Fi");
            scifi.addActionListener(viewController);
            scifi.setActionCommand("scifi");
        JButton thriller = new JButton("Trhiller");
            thriller.addActionListener(viewController);
            thriller.setActionCommand("thriller");
        JButton western = new JButton("Western");
            western.addActionListener(viewController);
            western.setActionCommand("western");

            categories.add(action);
            categories.add(comedy);
            categories.add(drama);
            categories.add(fantasy);
            categories.add(horror);
            categories.add(mystery);
            categories.add(romance);
            categories.add(scifi);
            categories.add(thriller);
            categories.add(western);

        JLabel hiw = new JLabel("How it Works?");
            hiw.setFont(txt);
            hiw.setForeground(new Color(255, 228, 0)); 
            categories.add(hiw);

        JButton faq = new JButton("FAQ");
            faq.addActionListener(viewController);
            faq.setActionCommand("faq");
            categories.add(faq);
                
        return categories;
    }
    
    //Header for all the movie panels except for the categories; Contains the sort option and a label for what they searched for/if they're in the all movie section
    public JPanel moviePanelHeader(){
         JPanel moviePanelHeader = new JPanel();
         BoxLayout moviePanelHeaderL = new BoxLayout(moviePanelHeader,BoxLayout.X_AXIS);
         moviePanelHeader.setLayout(moviePanelHeaderL);
             JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
             labelPanel.setAlignmentX(LEFT_ALIGNMENT);//align not working
             moviePanelHeader.add(labelPanel);
                 JLabel label = new JLabel(headerLabel);
                 labelPanel.add(label);

             JPanel sortCB = new JPanel(new FlowLayout(FlowLayout.TRAILING));
             sortCB.setAlignmentX(RIGHT_ALIGNMENT);//align not working
             moviePanelHeader.add(sortCB);
                 String[] sortOption = {"Name","Popularity","Genre","Release Date"};
                 sort = new JComboBox(sortOption);
                 sort.setVisible(true); // Prevents the dropdown to be hidden behind another component; Always display it on top
                 sort.setLightWeightPopupEnabled(true);
                 JButton sortConfirm = new JButton("Sort");
                 sortCB.add(sort);
                 sortConfirm.addActionListener(viewController);
                 sortConfirm.setActionCommand("sort");
                 sortCB.add(sortConfirm);

         return moviePanelHeader;    
     }
    
    //header for the category panels; Contains a label stating what category is the user in
    public JPanel categoryPanelHeader(){
         JPanel moviePanelHeader = new JPanel();
         BoxLayout moviePanelHeaderL = new BoxLayout(moviePanelHeader,BoxLayout.X_AXIS);
         moviePanelHeader.setLayout(moviePanelHeaderL);
             JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
             labelPanel.setAlignmentX(LEFT_ALIGNMENT);//align not working
             moviePanelHeader.add(labelPanel);
                 JLabel label = new JLabel(headerLabel);
                 labelPanel.add(label);

         return moviePanelHeader;    
     }
    
    //================================================================INFO PAGE FOR THE MOVIES================================================================\\
    
    //a pop-up frame for the movie info pane
    public JFrame info(){
        info = new JFrame();
        info.setVisible(true);
        info.setSize(700, 700);
        info.setTitle("Xtra Vision - Movie Info");
        BorderLayout rentBorder = new BorderLayout();
        info.setLayout(rentBorder);
        
        ImageIcon logoxtra = new ImageIcon(getClass().getResource("images/logoxtravision.png"));        
                logoxtra.setImage(logoxtra.getImage().getScaledInstance(680, 125, 100));    
                JLabel logo = new JLabel(logoxtra);
                    info.add(logo, BorderLayout.PAGE_START);
        
        infoCardL = new CardLayout();
        infoMainCard = new JPanel();
        infoMainCard.setLayout(infoCardL);
        info.add(infoMainCard, BorderLayout.CENTER);
        
        infoMainCard.add(infoPanel(), "info");

        info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        info.validate();
        info.repaint();
        
        return info;
    }

    //card for the main info panel frame; Can be switched to the payment page via Card Layour
    public JPanel infoPanel(){
        
        JPanel rentCenter = new JPanel();
        BorderLayout borderPanel = new BorderLayout();
        rentCenter.setLayout(borderPanel);
        
        JPanel center2 = new JPanel();
        rentCenter.add(center2, BorderLayout.CENTER);
        
        
            JPanel movieDetails = new JPanel();
            GridLayout movieGrid = new GridLayout(1,2);
            movieDetails.setLayout(movieGrid);
            
            center2.add(movieDetails);
                ImageIcon movieImage = new ImageIcon(getClass().getResource("images/"+viewController.getInfoMovie()[0][5]+".jpg"));
                movieImage.setImage(movieImage.getImage().getScaledInstance(300, 450, java.awt.Image.SCALE_SMOOTH));
                JLabel itt = new JLabel(movieImage);
                movieDetails.add(itt);
                
                JPanel detailsMovie = new JPanel();
                detailsMovie.setLayout(new BoxLayout(detailsMovie, BoxLayout.Y_AXIS));
                detailsMovie.setBorder(new EmptyBorder(20,20,20,20));
                movieDetails.add(detailsMovie);
                    JLabel movieTitle = new JLabel(viewController.getInfoMovie()[0][0]);
                    detailsMovie.add(movieTitle);
                    JLabel movieDate = new JLabel(viewController.getInfoMovie()[0][2]);
                    detailsMovie.add(movieDate);
                    JLabel movieGenre = new JLabel(viewController.getInfoMovie()[0][1]);
                    detailsMovie.add(movieGenre);
                    JTextArea movieSynopsis = new JTextArea("Synopsis: \n"+viewController.getInfoMovie()[0][6]);
                    movieSynopsis.setWrapStyleWord(true);
                    movieSynopsis.setLineWrap(true);
                    movieSynopsis.setEditable(false);
                    detailsMovie.add(movieSynopsis);

        JPanel footer = new JPanel();
        GridLayout footerGrid = new GridLayout(2,1);
        footer.setLayout(footerGrid);
            if(Integer.parseInt(viewController.getInfoMovie()[0][3])>0){ // checks if the machine still has stock for the selected movie, changes the "rent" button to "not available" if there's no stock left
                JButton rent = new JButton("Rent");
                rent.addActionListener(viewController);
                rent.setActionCommand("rent");
                footer.add(rent);
            }else{
                JButton notAvailable = new JButton("Not Available");
                notAvailable.setForeground(Color.GRAY);
                footer.add(notAvailable);
            }
            
            JButton exit = new JButton("Exit");
                exit.addActionListener(viewController);
                exit.setActionCommand("exit");

                footer.add(exit);
            rentCenter.add(footer, BorderLayout.PAGE_END);
        
        return rentCenter;
        
    }
    
    //card for the payment page
    public JPanel payPanel(){
        
        JPanel payCenter = new JPanel();
        BorderLayout borderPanel = new BorderLayout();
        payCenter.setLayout(borderPanel);
            
        JPanel paymentDetails = new JPanel();
        paymentDetails.setLayout(new BorderLayout());
        payCenter.add(paymentDetails);
            JPanel titlePanel = new JPanel(new GridLayout(3,1));
            titlePanel.setBorder(new EmptyBorder(20,20,60,20));
            paymentDetails.add(titlePanel, BorderLayout.PAGE_START);
                JLabel movieTitle = new JLabel("Movie: "+viewController.getInfoMovie()[0][0]);
                JLabel priceLabel = new JLabel("Price: €2.99");
                JLabel movieCode = new JLabel("Movie Code: "+viewController.getInfoMovie()[0][5]);
                titlePanel.add(movieTitle);
                titlePanel.add(priceLabel);
                titlePanel.add(movieCode);

                JPanel cardDetails = new JPanel(new GridLayout(1,2));
                cardDetails.setBorder(new EmptyBorder(0,20,0,20));
                paymentDetails.add(cardDetails, BorderLayout.CENTER);
                    JButton insertCard = new JButton("Insert Card");
                    insertCard.addActionListener(viewController);
                    insertCard.setActionCommand("insertCard");
                    cardDetails.add(insertCard);
                    JButton createCard = new JButton("Generate Card");
                    createCard.addActionListener(viewController);
                    createCard.setActionCommand("createCard");
                    cardDetails.add(createCard);
                   
            JPanel emailPanel = new JPanel();
            paymentDetails.add(emailPanel, BorderLayout.PAGE_END);
                
                JPanel emailButtonP = new JPanel(new GridLayout(3,1,2,2));
                emailPanel.add(emailButtonP);
                    emailButton = new JCheckBox("Get a receipt through email"); //hides/show the email textfield depends on its selected state
                    emailButton.setSelected(false);
                    emailButton.addActionListener(viewController);
                    emailButton.setActionCommand("show/hide");
                    emailButtonP.add(emailButton);
               
                    emailLabel = new JLabel("Enter your email:");
                    emailLabel.setVisible(false);
                    emailButtonP.add(emailLabel);
                    emailInput = new JTextField();
                    emailInput.setVisible(false);
                    emailButtonP.add(emailInput);
                                 
                

        JPanel footer = new JPanel();
        footer.setBorder(new EmptyBorder(50,20,0,20));
        GridLayout footerGrid = new GridLayout(3,1);
        footer.setLayout(footerGrid);
            JButton rent = new JButton("Confirm");
                rent.addActionListener(viewController);
                rent.setActionCommand("confirm");
            JButton back = new JButton("Back");
                back.addActionListener(viewController);
                back.setActionCommand("infoBack");
            JButton cancel = new JButton("Cancel");
                cancel.addActionListener(viewController);
                cancel.setActionCommand("exit2");

                footer.add(rent);
                footer.add(back);
                footer.add(cancel);
            payCenter.add(footer, BorderLayout.PAGE_END);
        
        return payCenter;
        
    }
    
    
    //================================================================Panels for the JOptionPane================================================================\\
    
    public JPanel inputCardDetails(){
        
        JPanel inputCardDetails = new JPanel(new GridLayout(4,1,5,5));
            JLabel cardNumLabel = new JLabel("Please enter the last 4 digits of your card");
            lastDigits = new JTextField(4);
            JLabel cardPinLabel = new JLabel("Please enter your pin");
            cardPin = new JTextField(4);
            inputCardDetails.add(cardNumLabel);
            inputCardDetails.add(lastDigits);
            inputCardDetails.add(cardPinLabel);
            inputCardDetails.add(cardPin);
        
        return inputCardDetails;
        
        
    }

    //================================================================getters and setters================================================================\\
    
    public JFrame getWelcome() {
        return welcome;
    }

    public JFrame getMain() {
        return main;
    }

    public JTextField getSearchBar() {
        return searchBar;
    }

    public JFrame getFaq() {
        return faq;
    }

    public JPanel getMovieSection() {
        return movieSection;
    }

    public CardLayout getMovieSectionL() {
        return movieSectionL;
    }

    public String getHeaderLabel() {
        return headerLabel;
    }

    public void setHeaderLabel(String headerLabel) {
        this.headerLabel = headerLabel;
    }

    public JComboBox getSort() {
        return sort;
    }

    public JFrame getInfo() {
        return info;
    }

    public int getCountResult() {
        return countResult;
    }

    public String[] getMovieID() {
        return movieID;
    }

    public String getPanelActive() {
        return panelActive;
    }

    public CardLayout getInfoCardL() {
        return infoCardL;
    }

    public JPanel getInfoMainCard() {
        return infoMainCard;
    }

    public JTextField getLastDigits() {
        return lastDigits;
    }

    public JTextField getCardPin() {
        return cardPin;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    
    public JTextField getEmailInput() {
        return emailInput;
    }
    
    public JCheckBox getEmailButton() {
        return emailButton;
    }

    public JFrame getReturnn() {
        return returnn;
    }

    public JTextField getMovieCode2() {
        return movieCode2;
    }

    public JPanel getPayPanel() {
        return payPanel;
    }

    

    
    
    
    
    
    
    
}
