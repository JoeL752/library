package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class RemovePatron implements Command{
	
	private int patronID;
	
	public RemovePatron(int patronID) {
		this.patronID = patronID;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		// TODO Auto-generated method stub
		Patron patron = library.getPatronByID(patronID); //gets the patron object for the certain patronid that has been provided
		library.removePatron(patron); //removes the patron from the librarys list of patrons
		patron.setPatronDeleted(true); //sets the patron to being deleted rather than deleted it entirely from the system
		System.out.println("Patron " + patron.getName() + " removed.");
		PatronDataManager patronData = new PatronDataManager();
        try {
			patronData.storeData(library); //stores that the patron has been deleted in the text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
