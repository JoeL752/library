package bcu.cmp5332.librarysystem.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;
import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class PatronTest { //WARNING: PERFORMING THESE TESTS WILL REMOVE THE EXISTING PATRONS FROM THE FILES, AFTER 
	//PERFORMING TESTS GO TO THE BACK UP FILES TO RESTORE PATRON DATA (THE NEW PATRON NEEDS TO REMAIN IN THE FILE
	//IN ORDER FOR THE SYSTEM TO WORK)
	
	Patron patron = new Patron(4, "Aniket Singh", "077123123789", "Aniket.Singh@gmail.com"); //A valid new member is created for test case use

	@Test
	public void getPhoneTest() {
		assertEquals("077123123789", patron.getPhone()); //Checks if the phone number in the system matches the value on the left side.
	}
	
	@Test
	public void storePhoneTest() throws IOException   {
		String RESOURCE = "./resources/data/patrons.txt";
		 
		Library lib = new Library();
		lib.addPatron(patron);
        PatronDataManager data = new PatronDataManager();
		data.storeData(lib);
		Scanner sc = new Scanner(new File(RESOURCE));
		while (sc.hasNextLine()) { //checks to see if the next line is empty
			   final String lineFromFile = sc.nextLine();
				  @SuppressWarnings("static-access")
				String[] properties = lineFromFile.split(data.SEPARATOR, -1);
				  String phone = properties[2]; //gets the phone value from the text file
				  assertEquals("077123123789", phone);
			}
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void loadPhoneTest() throws LibraryException, IOException   {
		String RESOURCE = "./resources/data/patrons.txt";
		Library lib = new Library();
		PatronDataManager data = new PatronDataManager();
		PrintWriter out = new PrintWriter(new FileWriter(RESOURCE)); //writes to a text file
	     out.print(patron.getId() + data.SEPARATOR);
         out.print(patron.getName() + data.SEPARATOR);
         out.print(patron.getPhone() + data.SEPARATOR);
         out.print(patron.getEmail() + data.SEPARATOR);
         out.println();
         out.close();
		 	data.loadData(lib);
		 	Patron p = lib.getPatronByID(4);
		 	assertEquals(patron.getPhone(), p.getPhone()); //checks to see if the values are equal
	}
	
}
