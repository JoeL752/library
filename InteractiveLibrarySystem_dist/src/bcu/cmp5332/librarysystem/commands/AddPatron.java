package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation here
    	int maxId = 0;
    	if (library.getPatrons().size() > 0) { //checks to see if there is patrons in the system
    		int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId(); //sets the value to how many patrons there are in the system
    	}
        Patron patron = new Patron(++maxId, name, phone, email); //creates a new patron object
        library.addPatron(patron); //adds the new patron to the system
        System.out.println("Patron #" + patron.getId() + " added.");
        PatronDataManager patronData = new PatronDataManager();
        try {
			patronData.storeData(library); //saves the newly created patron to text file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
 