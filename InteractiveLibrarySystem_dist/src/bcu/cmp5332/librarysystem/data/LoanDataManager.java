package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/loans.txt";
    

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        // TODO: implementation here
    	try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) { //checks to see if the next line is empty 
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1); //stores data from text file in an array, separated by ::
                try {
                    int patronid = Integer.parseInt(properties[0]); //assigns different lines from text file to variables 
                    int bookid = Integer.parseInt(properties[1]);
                    LocalDate startDate = LocalDate.parse(properties[2]);
                    LocalDate dueDate = LocalDate.parse(properties[3]);  
                    Loan loan = new Loan(library.getPatronByID(patronid), library.getBookByID(bookid), startDate, dueDate); //creates a new loan object
                    Book book = library.getBookByID(bookid);
                    book.setLoan(loan); //assigns the loan to the correct book
                    Patron patron = library.getPatronByID(patronid);
                    patron.addBook(book); //adds the book that is on loan to the correct patron
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex); //throws an error if unable to parse the patron id
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
    	// TODO: implementation here   	
    	List<Loan> loans = new ArrayList<>(); //creates an empty list for loan objects    
        for (Patron patron : library.getPatrons()) { //loops tnrough the systems patrons
        	try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //write to a text file
        		for(Book book: patron.getBooks()) { //loops through the patrons books that they have on loan
        			if(book.isOnLoan() == true) { //if the books the patrons have in the list are on loan they are added to the newly created loan list
        				loans.add(book.getLoan());
        			}
        		}
                for (Loan loan: loans) { //loops through the loan list and adds the data to the text file
                    out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    out.println();
                }
            }
        }
       
    }
}
    
 