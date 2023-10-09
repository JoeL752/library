package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class RenewBook implements Command{

	private final int patronId;
	private final int bookId;
	
	public RenewBook(int patronId, int bookId) {
		this.patronId = patronId;
		this.bookId = bookId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
			
		LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod()); //sets the value of the dueDate. From the library class the loan period is gathered and this is added to the current date
		
		Patron patron = library.getPatronByID(patronId); //gets the patron for the patronid that is provided
		Book book = library.getBookByID(bookId); //gets the book for the bookid that is provided
		
		patron.renewBook(book, dueDate); //renews the book for the patron
		book.setDueDate(dueDate); //sets a new due date for the book that is being loaned
		
		LoanDataManager loanData = new LoanDataManager();
		try {
			loanData.storeData(library); //saves that the book has been renewed to text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Book has been renewed, and must be returned by " + dueDate);
		
		
		
	}

}
