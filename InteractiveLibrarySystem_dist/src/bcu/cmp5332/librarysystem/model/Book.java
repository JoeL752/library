package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;

public class Book {
    
    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;
    private boolean bookDeleted;
   

    private Loan loan;

    //Constructor used when creating a new book 
    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.bookDeleted = false;
    }
    
    /**
     * Second Constructor, identical to the first, but has the extra boolean property bookDeleted - having 2 constructors so when loading the data from the file,
     * it keeps track of its state regarding if it has been deleted or not. 
     * @param id integer that represents the books unique id
     * @param title string that represents the books title
     * @param author string that represents the books author
     * @param publicationYear string that represents the books publication year
     * @param publisher string that represents the books publisher
     * @param bookDeleted boolean that represents if the book is deleted or not
     */
    
    public Book(int id, String title, String author, String publicationYear, String publisher, boolean bookDeleted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.bookDeleted = bookDeleted;
    }

    //getter and setter methods
    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    public String getPublisher() {
    	return publisher;
    }
    
    public void setPublisher(String publisher) {
    	this.publisher = publisher;
    }
	
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() {
        // TODO: implementation here
        return "Book #" + id
        		+ "\nTitle: " + title
        		+ "\nAuthor: " + author
        		+ "\nPublication year: " + publicationYear
        		+ "\nPublisher: " + publisher;
    }
    
    public boolean isOnLoan() {
    	return (loan != null);
    }

    public String getStatus() {    	
    	if(this.isOnLoan() == true) {
    		return "On loan";
    	}else {
    		return "Available";
    	}
    }

    public LocalDate getDueDate() {
        // TODO: implementation here
        return loan.getDueDate();
    }
    
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    	if(this.isOnLoan() == true) { 
    		dueDate = loan.getDueDate();
    	}else {
    		throw new LibraryException("Due date cannot be set, book is not on loan");
    	}
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void returnToLibrary() {
        loan = null;
    }
    
    /**
     * returns whether the book has been deleted or not
     * @return
     */
    
    public boolean getbookDeleted() {
    	return bookDeleted;
    }
    
    /**
     * sets the state of the book regarding if it has been deleted or not
     * @param bookDeleted boolean parameter to state if it has been deleted or not
     */
    
    public void setbookDeleted(boolean bookDeleted) {
    	this.bookDeleted = bookDeleted;
    }
    
   
}
