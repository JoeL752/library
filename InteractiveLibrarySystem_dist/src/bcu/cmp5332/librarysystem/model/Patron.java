package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Book> books = new ArrayList<>();
    private boolean patronDeleted;
    
    
    
    // TODO: implement constructor here
    //Constructor used when creating a new patron 
    public Patron(int id, String name, String phone, String email) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    	this.patronDeleted = false;
    }
 
    /**
     * Second Constructor, identical to the first, but has the extra boolean property patronDeleted - having 2 constructors so when loading the data from the file,
     * it keeps track of its state regarding if it has been deleted or not. 
     * @param id integer that represents patrons id
     * @param name string that represents patrons name
     * @param phone string that represents the patrons phone number
     * @param email string that represents the patrons email
     * @param patronDeleted boolean that represents if the patron has been deleted or not
     */
    
    
    public Patron(int id, String name, String phone, String email, boolean patronDeleted) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    	this.patronDeleted = patronDeleted;
    }
    
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here

	    	if(book.isOnLoan()) {
	    		throw new LibraryException("Book is already on loan.");
	    	}else {
	    		if(getBooks().size() >= 3) {
	    			throw new LibraryException("Limit Reached. Can only loan 3 books at once.");
	    		}else {
	    			Loan loan = new Loan(this, book, LocalDate.now() ,dueDate);
	        		book.setLoan(loan);
	        		this.addBook(book);
	    		}
	    	}
    	
    	
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    	
    	if(book.isOnLoan() == true) {
    		book.returnToLibrary();
    		Loan loan = new Loan(this, book, LocalDate.now(), dueDate);
    		book.setLoan(loan);		
    	}else {
    		throw new LibraryException("The book is not on loan to this patron");
    	}
      	
    }

    public void returnBook(Book book, int patronId) throws LibraryException {
        // TODO: implementation here
    	
    	if(book.isOnLoan() == true) {
    		if(book.getLoan().getPatron().getId() == patronId) {
    			books.remove(book);
        		book.returnToLibrary();
    		}else {
    			throw new LibraryException("The book is not on loan to this patron");
    		}
    	}else {
    		throw new LibraryException("The book is not on loan to this patron");
    	}
    }
    
    public void addBook(Book book) {
        // TODO: implementation here
    	books.add(book); 
    }
    
    public int getId() {
    	return id;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getPhone() {
    	return phone;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public List<Book> getBooks(){
    	return Collections.unmodifiableList(books);
    }
 
    public String getDetails() {
        return "Patron #" + id + " - " + name;
    }
    
    /**
     * returns whether the patron has been deleted or not
     * @return patronDeleted
     */
    
    public boolean getPatronDeleted() {
    	return patronDeleted;
    }
    
    /**
     * sets if the patron has been deleted or not
     * @param patronDeleted boolean parameter to state if patron has been deleted or not
     */
    public void setPatronDeleted(boolean patronDeleted) {
    	this.patronDeleted = patronDeleted;
    }
    
      
}
 