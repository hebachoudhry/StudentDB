package CREATE_STUDENTDB;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.*;
import java.io.*;
import COMMON_STUDENT.Student;
import COMMON_STUDENT.FileIO;
import org.apache.commons.lang3.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateStudentDB {

	private JFrame frmCreateStudentDatabase;
	private JTextField textFieldStudentID;
	private JLabel lblEnterFullName;
	private JLabel lblEnterBirthDate;
	private JLabel lblEnterStudentType;
	private JLabel lblEnterMajor;
	private JLabel lblEnterCreditHours;
	private JTextField textFieldFullName;
	private JTextField textFieldBirthDate;
	private JTextField textFieldStudentType;
	private JTextField textFieldMajor;
	private JTextField textFieldCreditHours;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateStudentDB window = new CreateStudentDB();
					window.frmCreateStudentDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateStudentDB() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreateStudentDatabase = new JFrame();
		frmCreateStudentDatabase.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmCreateStudentDatabase.setTitle("Create Student Database");
		frmCreateStudentDatabase.setBounds(100, 100, 342, 378);
		frmCreateStudentDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateStudentDatabase.getContentPane().setLayout(null);
		
		JLabel lblEnterStudentID = new JLabel("Enter Student ID:");
		lblEnterStudentID.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterStudentID.setBounds(16, 22, 130, 16);
		frmCreateStudentDatabase.getContentPane().add(lblEnterStudentID);
		
		textFieldStudentID = new JTextField();
		textFieldStudentID.setBounds(169, 17, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldStudentID);
		textFieldStudentID.setColumns(10);
		
		lblEnterFullName = new JLabel("Enter full name:");
		lblEnterFullName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterFullName.setBounds(16, 73, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(lblEnterFullName);
		
		lblEnterBirthDate = new JLabel("Enter birth date:");
		lblEnterBirthDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterBirthDate.setBounds(16, 119, 155, 16);
		frmCreateStudentDatabase.getContentPane().add(lblEnterBirthDate);
		
		lblEnterStudentType = new JLabel("Enter student type:");
		lblEnterStudentType.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterStudentType.setBounds(14, 164, 132, 16);
		frmCreateStudentDatabase.getContentPane().add(lblEnterStudentType);
		
		lblEnterMajor = new JLabel("Enter major:");
		lblEnterMajor.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterMajor.setBounds(16, 210, 130, 16);
		frmCreateStudentDatabase.getContentPane().add(lblEnterMajor);
		
		lblEnterCreditHours = new JLabel("Enter credit hours:");
		lblEnterCreditHours.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblEnterCreditHours.setBounds(16, 259, 155, 16);
		frmCreateStudentDatabase.getContentPane().add(lblEnterCreditHours);
		
		textFieldFullName = new JTextField();
		textFieldFullName.setBounds(169, 73, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldFullName);
		textFieldFullName.setColumns(10);
		
		textFieldBirthDate = new JTextField();
		textFieldBirthDate.setBounds(169, 114, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldBirthDate);
		textFieldBirthDate.setColumns(10);
		
		textFieldStudentType = new JTextField();
		textFieldStudentType.setBounds(169, 159, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldStudentType);
		textFieldStudentType.setColumns(10);
		
		textFieldMajor = new JTextField();
		textFieldMajor.setBounds(169, 205, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldMajor);
		textFieldMajor.setColumns(10);
		
		textFieldCreditHours = new JTextField();
		textFieldCreditHours.setBounds(169, 254, 130, 26);
		frmCreateStudentDatabase.getContentPane().add(textFieldCreditHours);
		textFieldCreditHours.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try{
					btnSubmit_CLICK();
				}
				catch (Exception e){
					System.out.println(e.toString());
				}
			}
		});	
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSubmit.setBounds(112, 305, 117, 29);
		frmCreateStudentDatabase.getContentPane().add(btnSubmit);
	
	} //End of method initialize
	
	private void btnSubmit_CLICK() {
		
		//-------------- First, validate all the text fields
				// If any problem, a dialog warning pops up to stop the program
				boolean isValidated = validateTextFields();
				
				if (! isValidated) return;
				
				
				//-------------- All the text fields have been validated
				FileIO fileIOHandler = new FileIO();
				
				// Declare output file database: StudentDatabase.txt
				// MUST use \ to qualify '\' in the path of the file
				File outputFile = new File("/Users/hebachoudhry/Documents/JAVA/OUTPUTS/StudentDatabase.txt");
				
				String strID = textFieldStudentID.getText();
				long studentID = Long.parseLong(strID);
				
				String strFullName = textFieldFullName.getText();
				String strBirthDate = textFieldBirthDate.getText();
				String strStudentType = textFieldStudentType.getText();
				String strMajor = textFieldMajor.getText();
				String strHours = textFieldCreditHours.getText();
				long creditHours = Long.parseLong(strHours);
				
				// Create a student object
				Student aStudent = new Student ((int) studentID, strFullName);
				aStudent.setBirthDate(strBirthDate);
				aStudent.setStudentType(strStudentType);
				aStudent.setMajor(strMajor);
				aStudent.setCreditHours((int)creditHours);
				
				// Get the string of student data
				String strStudentInfo = aStudent.toString();
				
				try {	
					// Write the string to the student database file
					// by adding a line to the file
					fileIOHandler.appendOneLineToFile(outputFile, strStudentInfo);
				}
				catch (IOException ex){ 
		      		ex.printStackTrace();
		    		}
			
				// At this point, already successfully inserting a new student record into the database
				JOptionPane.showMessageDialog(frmCreateStudentDatabase, "The new student record has been successfully inserted into the database.");
				
				// After successfully inserting a new book record to the database
				// refresh all the text fields to prepare for the next record
				textFieldStudentID.setText("");
				textFieldFullName.setText("");
				textFieldBirthDate.setText("");
				textFieldStudentType.setText("");
				textFieldMajor.setText("");
				textFieldCreditHours.setText("");	
		
	} //End of btnSubmit_CLICK
	
	private boolean validateTextFields() {
		
		boolean isValidated = true;
		
		//----------- Validate ID text field
				try{
					Validate.notBlank(textFieldStudentID.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - ID cannot be blank!!!");
					textFieldStudentID.requestFocusInWindow(); // make it ready to enter the value
					textFieldStudentID.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}
				
				if (! isValidated) return (isValidated);
				
				// For ID, also need to verify the entered value is a valid numeric
				try{
					long tempLong = Long.parseLong(textFieldStudentID.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "ID must have a Numeric Value.");
					textFieldStudentID.requestFocusInWindow(); // make it ready to enter the value
					textFieldStudentID.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}
				
				if (! isValidated) return (isValidated);
				
				//----------- Validate Full Name text field
				
				try{
					Validate.notBlank(textFieldFullName.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - Full name cannot be blank !!!");
					textFieldFullName.requestFocusInWindow(); // make it ready to enter the value
					textFieldFullName.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}

				if (! isValidated) return (isValidated);

				//----------- Validate Birth Date text field
				
				try{
					Validate.notBlank(textFieldBirthDate.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - Birth date cannot be blank !!!");
					textFieldBirthDate.requestFocusInWindow(); // make it ready to enter the value
					textFieldBirthDate.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}

				if (! isValidated) return (isValidated);
				
				//----------- Validate Student Type text field
				
				try{
					Validate.notBlank(textFieldStudentType.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - Student Type cannot be blank !!!");
					textFieldStudentType.requestFocusInWindow(); // make it ready to enter the value
					textFieldStudentType.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}

				if (! isValidated) return (isValidated);
				
				//----------- Validate Major text field
				
				try{
					Validate.notBlank(textFieldMajor.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - Major cannot be blank !!!");
					textFieldMajor.requestFocusInWindow(); // make it ready to enter the value
					textFieldMajor.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}

				if (! isValidated) return (isValidated);
				
				//----------- Validate Credit Hours text field
				
				try{
					Validate.notBlank(textFieldCreditHours.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "All the text fields must have valid values - Credit Hours cannot be blank !!!.");
					textFieldCreditHours.requestFocusInWindow(); // make it ready to enter the value
					textFieldCreditHours.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}
		
				if (! isValidated) return (isValidated);
		
				// For Credit Hours, also need to verify the entered value is a valid numeric
				try{
					long tempLong = Long.parseLong(textFieldCreditHours.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(frmCreateStudentDatabase, "Credit hours must have a Numeric Value.");
					textFieldCreditHours.requestFocusInWindow(); // make it ready to enter the value
					textFieldCreditHours.selectAll(); // select all text in the text field to delete it or to replace it
					isValidated = false;
				}
		
				if (! isValidated) return (isValidated);
				
				return isValidated;
		
	} //End of validateTextFields
	
} //End of class CreateBookDB