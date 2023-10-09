package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.model.Book;


public class DisplayPatronDetails extends JFrame  {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookID;
	private String bookName;
	private MainWindow mw;
	private String patron;
	
	public DisplayPatronDetails(MainWindow mw, int bookID, String bookName) {
		this.mw = mw;
		this.bookID = bookID;
		this.bookName = bookName;
        initialize();
	}

	private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }
             
        List<Book> books = mw.getLibrary().getBooks(); //creates a list of book objects and populates it with all the books in the system
        
        for(Book b : books) { //loops through each book in the list
        	if(b.getId() == bookID) {
        		if(b.isOnLoan() == true) { //if the book is on loan and matches the book id given the patron details are returned
        			patron = b.getLoan().getPatron().getDetails();
        		}else {
        			patron = null;
        		}
        		
        	}
        }
        

        setTitle("Member loaning "+ bookName); //names the window after the book that is on loan

        setSize(500, 220);//sets the size of the window
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

         
        this.getContentPane().add(panel);
        
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        //topPanel.add(new JLabel("Patron :" + patron));
        
        if(patron != null) { //checks to see if patron contains any data and outputs the data to the window
            	topPanel.add(new JLabel(patron));
        }else {
        	topPanel.add(new JLabel("This book is currently not on loan."));//outputs this message if book isn't on loan
        }


        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        
        setLocationRelativeTo(mw);
        setVisible(true);
	}
}