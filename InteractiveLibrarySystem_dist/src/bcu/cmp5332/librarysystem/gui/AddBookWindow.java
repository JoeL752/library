package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddBookWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField pubText = new JTextField();

    private JButton addBtn = new JButton("Add"); //Button to add a book
    private JButton cancelBtn = new JButton("Cancel"); //Button to cancel the process

    public AddBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame. 
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 

        setTitle("Add a New Book"); //Names the window title "Add a New Book".

        setSize(300, 200); //Sets the size of the window
        JPanel topPanel = new JPanel(); // Creates a new top panel for the window
        topPanel.setLayout(new GridLayout(5, 2)); //Creates a grid layout for this container
        topPanel.add(new JLabel("Title : ")); //Label asking the user to input the book's title.
        topPanel.add(titleText); //The box next to the Title heading updates the titleText variable from above with what the user has entered.
        topPanel.add(new JLabel("Author : "));//Label asking the user to input the book's author.
        topPanel.add(authText);//Updates authText variable.
        topPanel.add(new JLabel("Publishing Date : "));//Label asking the user to input the book's publishing date.
        topPanel.add(pubDateText); //Updates pubDateText variable.
        topPanel.add(new JLabel("Publisher : "));//Label asking the user to input the book's publisher.
        topPanel.add(pubText);//Updates pubText variable.

        JPanel bottomPanel = new JPanel();//Creates a new bottom panel for the window
        bottomPanel.setLayout(new GridLayout(1, 3)); //Sets the grid layout for the container
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn); //Adds the addBtn button from above
        bottomPanel.add(cancelBtn);  //Adds the cancelBtn button from above

        addBtn.addActionListener(this); //Adds an action listener to the addBtn button
        cancelBtn.addActionListener(this); //Adds an action listener to the cancelBtn button

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //Returns the content pane with everything included in the topPanel and places it in the center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //Returns the content pane with everything included in the bottomPanel and places it in the bottom
        setLocationRelativeTo(mw); //Places the window relative the main window 

        setVisible(true); //Show the window as it is set to true

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void addBook() {
        try {
            String title = titleText.getText();
            String author = authText.getText();
            String publicationYear = pubDateText.getText();
            String publisher = pubText.getText();
            // create and execute the AddBook Command
            Command addBook = new AddBook(title, author, publicationYear, publisher);
            addBook.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the AddBookWindow
            this.setVisible(false);
            JOptionPane.showMessageDialog(this, "Book added");
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
