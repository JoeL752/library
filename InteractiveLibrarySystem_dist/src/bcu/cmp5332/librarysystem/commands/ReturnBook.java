package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ReturnBook implements Command{

	private final int patronId;
	private final int bookId;
	
	public ReturnBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		
		Patron patron = library.getPatronByID(patronId); //gets the patron for the patronid that is provided
		Book book = library.getBookByID(bookId); //gets the book for the bookid that is provided
		
				
		patron.returnBook(book, patronId); //returns the book the patron loaned

		LoanDataManager loanData = new LoanDataManager();
		try {
			loanData.storeData(library); //saves to text file that the book is no longer being loaned
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Book has been returned.");
			
	}

}
