package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PatronDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/patrons.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        // TODO: implementation here
    	try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) { //checks to see if the next line is empty
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1); //stores data from text file in an array, separated by ::
                try {
                    int id = Integer.parseInt(properties[0]); //assigns different lines from text file to variables 
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3];
                    boolean patronDeleted = Boolean.parseBoolean(properties[4]);
                    Patron patron = new Patron(id, name, phone, email, patronDeleted); //creates a new patron object
                    library.addPatron(patron); //adds the newly created patron objects to the system
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex); //throws an error if unable to parse patron id
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        // TODO: implementation here
    	try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //write to a text file
            for (Patron patron : library.getPatrons()) { //loops through all the patrons in the system and stores them in the text file
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhone() + SEPARATOR);
                out.print(patron.getEmail() + SEPARATOR);
                out.print(patron.getPatronDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
 