package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.RemoveBooks;
import bcu.cmp5332.librarysystem.commands.RemovePatron;
import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener, MouseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksRenew;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;
    
    private JTable Booktable;
    private JTable Membertable;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System"); //names the window "Library Management System"

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksRenew = new JMenuItem("Renew");
        booksReturn = new JMenuItem("Return");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksRenew);
        booksMenu.add(booksReturn);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);//sets the size of the window

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, LibraryException {
//        Library library = LibraryData.load();
//        new MainWindow(library);			
//    }


    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
        	if(Booktable != null) {
				int row = Booktable.getSelectedRow(); //checks to see which row has been selected on the book table
				if(row != -1) {
					int bookID = (int)Booktable.getValueAt(row, 0); //get the value of the first row which contains the book id
						deleteBook(bookID); //runs the deleteBook method. Deletes book corresponding to the bookId
				}else {
					JOptionPane.showMessageDialog(this, "No book selected","Error",JOptionPane.ERROR_MESSAGE); //outputs an error message if no book is selected
				}
        	}          
        } else if (ae.getSource() == booksIssue) {
            new BorrowBookWindow(this);
            
        }else if (ae.getSource() == booksRenew){
        	new RenewBookWindow(this);
            
        } else if (ae.getSource() == booksReturn) {
        	new ReturnBookWindow(this);
            
        } else if (ae.getSource() == memView) {
            displayMembers();
            
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
            
        } else if (ae.getSource() == memDel) {
        	if(Membertable != null) {
				int row = Membertable.getSelectedRow(); //checks to see which row has been selected on the member table
				if(row != -1) {
					int memberID = (int)Membertable.getValueAt(row, 0); //get the value of the first row which contains the member id
						deleteMember(memberID); //runs the deleteMember method. Deletes member corresponding to the member Id
				}else {
					JOptionPane.showMessageDialog(this, "No member selected","Error",JOptionPane.ERROR_MESSAGE); //outputs an error message if no member is selected
				}
        	}  
            
        }
    }

    public void displayBooks() {
    	List<Book> booksList = new ArrayList<>(); //creates an empty list for book objects 
        List<Book> booksList2 = library.getBooks(); //creates a list for book objects and populates it with all the books in the system
        // headers for the table
        String[] columns = new String[]{"ID", "Title", "Author", "Pub Date", "Status", "View Member"};

        Iterator<Book> iterator = booksList2.iterator();
  
        while(iterator.hasNext()) {
			Book f = iterator.next();
			if(!f.getbookDeleted()) { //checks to see whether the book has been deleted or not 
				booksList.add(f); //adds the book to the list if it hasn't been deleted				
			}
		}
        
        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i); //loops through each book in the new list and adds it to the book table
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus();
            data[i][5] = "View Member";
        }
        Booktable = new JTable(data, columns);
        Booktable.addMouseListener(this);
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(Booktable));
        this.revalidate();
    
    }
    
    public void displayMembers() {
    	List<Patron> patronsList = new ArrayList<>(); //creates an empty list for patron objects 
        List<Patron> patronsList2 = library.getPatrons(); //creates a list for patron objects and populates it with all the patrons in the system
        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Phone", "No. books currently on loan", "View Books"};

        Iterator<Patron> iterator = patronsList2.iterator();
        
        while(iterator.hasNext()) {
			Patron p = iterator.next();
			if(!p.getPatronDeleted()) { //checks to see whether the patron has been deleted or not 
				patronsList.add(p); //adds the book to the list if it hasn't been deleted					
			}
		}
            
        Object[][] data = new Object[patronsList.size()][5];
        for (int i = 0; i < patronsList.size(); i++) { //loops through each member in the new list and adds it to the member table
            Patron patron = patronsList.get(i);
            data[i][0] = patron.getId();
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhone();
            data[i][3] = patron.getBooks().size();
            data[i][4] = "View Books";
        }

        Membertable = new JTable(data, columns);
        Membertable.addMouseListener(this);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(Membertable));
        this.revalidate();
    }
    
    /**
     * Deletes the selected book from the system. Book is set to deleted using the setbookDeleted method,
     * so the book isn't completely gone from the system  
     * @param bookID integer that represents the book that is going to be deleted
     */
    
    public void deleteBook(int bookID) {
			Book selectedBook = null;
			for(Book b : library.getBooks()) { //loops through each book in the system
				if(b.getId() == bookID) { 
					selectedBook = b; //stores the selected book as a variable
				}
			}
			
			selectedBook.setbookDeleted(true); //sets the selected book as being deleted
			
			Command removeBook = new RemoveBooks(selectedBook.getId()); //runs the removeBook class to delete the selected book
			try {
				removeBook.execute(getLibrary(), LocalDate.now());
			} catch (LibraryException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BookDataManager bookData = new BookDataManager();
	        try {
				bookData.storeData(library); //saves the book as being deleted to the text file
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        displayBooks();

    }
    
    /**
     * Deletes the selected member from the system. Member is set to deleted using the setmemberDeleted method,
     * so the book isn't completely gone from the system 
     * @param memberID parameter that represents the member that is going to be deleted
     */
    
    public void deleteMember(int memberID) {
		Patron selectedPatron = null;
		for(Patron p : library.getPatrons()) { //loops through each patron in the system
			if(p.getId() == memberID) {
				selectedPatron = p; //stores the selected member as a variable
			}
		}
		
		selectedPatron.setPatronDeleted(true); //sets the member as being deleted
		
		Command removePatron = new RemovePatron(selectedPatron.getId()); //runs the remove patron class to delete the selected patron
		try {
			removePatron.execute(getLibrary(), LocalDate.now());
		} catch (LibraryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PatronDataManager patronData = new PatronDataManager();
        try {
			patronData.storeData(library); //saves the patron as being deleted to the text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        displayMembers();

}

    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Booktable) { //checks to see if there is mouse input on the book table
			int row = Booktable.rowAtPoint(e.getPoint());
			int col = Booktable.columnAtPoint(e.getPoint());
			if (col == 5) {
				final int bookID = (int)Booktable.getValueAt(row, 0); //gets the value of the first row in the book table
				final String bookName = (String)Booktable.getValueAt(row, 1); //gets the value of the second row in the book table
				new DisplayPatronDetails(this, bookID, bookName); //displays the patrons details thats loaning the book that has been clicked on 
		}
		}else if(e.getSource() == Membertable) { //checks to see if there is mouse input on the member table
			int row = Membertable.rowAtPoint(e.getPoint());
			int col = Membertable.columnAtPoint(e.getPoint());
			if(col == 4) {
				final int memberID = (int)Membertable.getValueAt(row, 0); //gets the value of the first row in the member table
				final String memberName = (String)Membertable.getValueAt(row, 1); //gets the value of the second row in the member table
				new DisplayBookDetails(this, memberID, memberName); //displays the book details that the patron that has been clicked on is loaning 
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
