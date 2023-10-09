package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class ShowBook implements Command{
	
	private final int bookId;
	
	public ShowBook(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		Book book = library.getBookByID(bookId); //gets the book for the bookid that is provided 
		
		if(!book.getbookDeleted()) {
    		System.out.println(book.getDetailsShort());	//outputs the book details if it hasn't been deleted from the system
		}else {
			System.out.println(book.getDetailsShort() + " (Removed)"); //outputs the book details if it has been deleted from the system and states it has been removed
		}

	}
}
