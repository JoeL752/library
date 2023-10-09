package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class BorrowBook implements Command{
	
	private final int patronId;
	private final int bookId;
	
	public BorrowBook(int patronId, int bookId){
		this.patronId = patronId;
		this.bookId = bookId;
	}
	
	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {	
		LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod()); //Variable dueDate is created in the LocalDate format and adds the loan period from the Library method getLoanPeriod, meaning it is set 7 days from the time it is issued.
			
		Patron patron = library.getPatronByID(patronId); //Local variable for getting the patron's ID by matching it with the user's input; runs getPatronById in the Library class.
		Book book = library.getBookByID(bookId); //Local variable for getting the book's ID by matching it with the user's input; runs getBooknById in the Library class.
		
		
		patron.borrowBook(book, dueDate); //The borrowBook method in the Patron class is run with the due date and book being sent
		book.setDueDate(dueDate); //The due date is firmly set by sending the calculated date above to the Book's setDueDate method.
		
	
		LoanDataManager loanData = new LoanDataManager(); //A new LoanDataManager is created upon the attempt of borrowing a book
		//Try to store the loan transaction by using the data manager into the loans text file, else catch and display the error.
		try {
			loanData.storeData(library);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Book has been borrowed, and must be return by " + dueDate); //After successfully issuing a book, this message will be outputted.
	}
}
