package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ShowPatron implements Command{
	
	private final int patronId;
	
	public ShowPatron(int patronId) {
		this.patronId = patronId;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId); //Gets the patron via the ID the user has given.
		
		if(!patron.getPatronDeleted()) {
			System.out.println(patron.getDetails()); //Prints out the Patron details for the one found above.
		}else {
			System.out.println(patron.getDetails() + " (Removed)");
		}
		
		
	}

}
