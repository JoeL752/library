package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;


import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class RemoveBooks implements Command{
	
	private final int bookId;
	
	public RemoveBooks(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		Book book = library.getBookByID(bookId); //gets the book object for the certain bookid that has been provided	
		library.removeBook(book); //removes the book from the librarys list of books
		book.setbookDeleted(true); //sets the book to being deleted rather than deleted it entirely from the system
		System.out.println("Book #" + book.getId() + " removed.");
		BookDataManager bookData = new BookDataManager();
        try {
			bookData.storeData(library); //saves that the book has been deleted in the text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
