package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>(); //creates an empty TreeMap 
    private final Map<Integer, Book> books = new TreeMap<>(); //creates an empty TreeMap

    public int getLoanPeriod() {
        return loanPeriod;
    }


    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Patron> getPatrons(){
    	List<Patron> out = new ArrayList<>(patrons.values());
        return Collections.unmodifiableList(out);
    }
    
    

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }

    public Patron getPatronByID(int id) throws LibraryException {
        // TODO: implementation here
    	if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no such patron with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) { //adds a book to the system
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    public void addPatron(Patron patron) { //adds a patron to the system
        // TODO: implementation here
    	if(patrons.containsKey(patron.getId())) {
    		throw new IllegalArgumentException("Duplicate patron ID.");
    	}
    	patrons.put(patron.getId(), patron);
    }
    
    public void removeBook(Book book) { //removes book from system
    	book.setbookDeleted(true);        
    }
    
    public void removePatron(Patron patron) { //removes patron from system
    	patron.setPatronDeleted(true);        
    }

    
}
 