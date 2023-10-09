package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getBooks(); //creates a list containing books in the system
        int i = 0;
        for (Book book : books) { //loops through the list of books to display short details on each book that hasn't been removed from the system
        	if(!book.getbookDeleted()) {    		
        		System.out.println(book.getDetailsShort());
        		i ++; //counts the amount of times been through the loop
        	}
            
        }
        System.out.println(i + " book(s)"); //outputs the amount of books that were displayed
    }
}
 