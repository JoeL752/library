package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;

public class ListPatrons implements Command {

	@Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons(); //creates a list of all the patrons in the system
        int i = 0;
        for (Patron patron : patrons) { //loops through each patron in the list and outputs short details on each patron that hasn't been removed from the system
        	if(!patron.getPatronDeleted()) {
        		System.out.println(patron.getDetails());
        		i ++; //counts the amount of times been through the loop
        	}
            
        }
        System.out.println(i + " patron(s)"); //outputs the amount of patrons that were displayed
    }
	
}
