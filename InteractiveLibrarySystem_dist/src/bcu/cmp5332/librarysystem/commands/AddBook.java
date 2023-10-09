package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class AddBook implements  Command {

    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException{
        int maxId = 0;
    	if (library.getBooks().size() > 0) { //checks to see if there is books in the system
    		int lastIndex = library.getBooks().size() - 1;
            maxId = library.getBooks().get(lastIndex).getId(); //sets the value of how many books there are in the system
    	}
        Book book = new Book(++maxId, title, author, publicationYear, publisher); //creates a new book object
        library.addBook(book); //adds book to library
        System.out.println("Book #" + book.getId() + " added.");
        BookDataManager bookData = new BookDataManager();
        try {
			bookData.storeData(library); //saves the newly created book object to text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
 