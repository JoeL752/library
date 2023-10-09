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
import bcu.cmp5332.librarysystem.model.Patron;

public class DisplayBookDetails extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int memberID;
	private String memberName;
	private MainWindow mw;

	public DisplayBookDetails(MainWindow mw, int memberID, String memberName) {
		this.mw = mw;
		this.memberID = memberID;
		this.memberName = memberName;
        initialize();
	}

	private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }
             
        List<Patron> patrons = mw.getLibrary().getPatrons(); //creates a list of patron objects and populate it with all the patrons in the system
        List<Book> books = null; //creates an empty list of book objects
        
       
        for(Patron p : patrons) { //loops through the patrons list and gathers all the books that each patron has on loan
        	if(p.getId() == memberID) {
        		books = p.getBooks();
        	}
        }
        

        setTitle(memberName + " books currently being loaned"); //names the window after the patrons name

        setSize(500, 220);//sets the size of the window
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

         
        this.getContentPane().add(panel);
        
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        //topPanel.add(new JLabel("Patron :" + patron));
        

        if(!books.isEmpty()) {//checks to see if the books list is populated and outputs the book details that specific patron has on loan
        	for(Book b : books) {
        		topPanel.add(new JLabel(b.getDetailsShort()));
        	}    	
        }else {
        	topPanel.add(new JLabel("This patron isn't loaning any books.")); //outputs this message if the patron isn't loaning any books
        }


        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        
        setLocationRelativeTo(mw);
        setVisible(true);
	}
}
