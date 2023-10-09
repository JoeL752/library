package bcu.cmp5332.librarysystem.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;

import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class BookTest { //WARNING: PERFORMING THESE TESTS WILL REMOVE THE EXISTING BOOKS FROM THE FILES, AFTER 
	//PERFORMING TESTS GO TO THE BACK UP FILES TO RESTORE BOOK DATA (THE NEW BOOK NEEDS TO REMAIN IN THE FILE
	//IN ORDER FOR THE SYSTEM TO WORK)

	Book book = new Book(44,"Harry Potter and the Goblet of Fire","J. K. Rowling","2000", "Bloomsbury"); //A new book is made with valid data for the test cases.
	
	@Test 
	public void getPublisherTest() {
		assertEquals("Bloomsbury", book.getPublisher()); //Checks if the publishers match by getting it from the system.
	}
	
	@Test
	public void setPublisherTest() {
		book.setPublisher("New Publisher"); //The publisher parameter above has been modified to hold a new value.
		assertEquals("New Publisher", book.getPublisher()); //Checks to see if this new value has been updated in the system and matches this current one.
	}
	

	@Test
	public void storePublisherTest() throws LibraryException, IOException   {
		String RESOURCE = "./resources/data/books.txt";
		 
		Library lib = new Library();
		lib.addBook(book); //adds the new book to the system
        BookDataManager data = new BookDataManager();
		data.storeData(lib); //adds the new data to text file
		Scanner sc = new Scanner(new File(RESOURCE));
		while (sc.hasNextLine()) { //checks to see if the next line is empty
			   final String lineFromFile = sc.nextLine();
				  @SuppressWarnings("static-access")
				String[] properties = lineFromFile.split(data.SEPARATOR, -1);
				  String publisher = properties[4]; //gets the publisher value from text file
				  assertEquals("Bloomsbury", publisher); //checks to see if the values are equal
			}
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void loadPublisherTest() throws LibraryException, IOException   {
		String RESOURCE = "./resources/data/books.txt";
		Library lib = new Library();
		BookDataManager data = new BookDataManager();
		PrintWriter out = new PrintWriter(new FileWriter(RESOURCE)); //writes to a text file
	     out.print(book.getId() + data.SEPARATOR);
         out.print(book.getTitle() + data.SEPARATOR);
         out.print(book.getAuthor() + data.SEPARATOR);
         out.print(book.getPublicationYear() + data.SEPARATOR);
         out.print(book.getPublisher() + data.SEPARATOR);
         out.println();
         out.close();
		 	data.loadData(lib);
		 	Book b = lib.getBookByID(44);
		 	assertEquals(book.getPublisher(), b.getPublisher()); //checks to see if the values are equal
	}
	
	

}
