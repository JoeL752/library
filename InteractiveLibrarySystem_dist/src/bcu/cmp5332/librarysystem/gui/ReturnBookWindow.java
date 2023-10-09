package bcu.cmp5332.librarysystem.gui;

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

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.main.LibraryException;

public class ReturnBookWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField bookIDText = new JTextField();
    private JTextField patronIDText = new JTextField();


    private JButton returnBtn = new JButton("Return");
    private JButton cancelBtn = new JButton("Cancel");

    public ReturnBookWindow(MainWindow mw) {
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

        setTitle("Return Loaned Book "); //names window "Return Loaned Book"

        setSize(300, 200);//sets size of window
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Book ID : "));
        topPanel.add(bookIDText);
        topPanel.add(new JLabel("Patron ID : "));
        topPanel.add(patronIDText);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(returnBtn);
        bottomPanel.add(cancelBtn);

        returnBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnBtn) {
            ReturnBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void ReturnBook() {
        try {
            int bookID = Integer.parseInt(bookIDText.getText()); //gets values from text fields
            int patronID =Integer.parseInt(patronIDText.getText());
                     
            // create and execute the AddBook Command
            Command ReturnBook = new ReturnBook(patronID, bookID); //runs returnBook class
            ReturnBook.execute(mw.getLibrary(), LocalDate.now());
            
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the AddBookWindow
            this.setVisible(false);
            JOptionPane.showMessageDialog(this, "Book has been returned"); //displays info message stating book has been returned
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE); //displays error message stating there has been a library exception error
        }
    }

}
