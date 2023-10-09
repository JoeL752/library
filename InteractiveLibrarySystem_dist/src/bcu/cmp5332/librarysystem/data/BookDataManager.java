package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/books.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) { 
            int line_idx = 1;
            while (sc.hasNextLine()) { //checks to see if the next line is empty
                String line = sc.nextLine(); 
                String[] properties = line.split(SEPARATOR, -1); //stores data from text file in an array, separated by ::
                try {
                    int id = Integer.parseInt(properties[0]); //assigns different lines from text file to variables 
                    String title = properties[1];
                    String author = properties[2];
                    String publicationYear = properties[3];
                    String publisher = properties[4];
                    boolean bookDeleted = Boolean.parseBoolean(properties[5]);
                    Book book = new Book(id, title, author, publicationYear, publisher, bookDeleted); //creates new book object
                    library.addBook(book); //adds the newly created book to the system
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex); //throws an error if unable to parse book id
                }
                line_idx++;
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //writes to a text file
            for (Book book : library.getBooks()) { //loops through all the books in the system and writes them into the text file
                out.print(book.getId() + SEPARATOR);
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                out.print(book.getPublisher() + SEPARATOR);
                out.print(book.getbookDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
 