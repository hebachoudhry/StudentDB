package SEARCH_STUDENTDB;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;

import java.io.*;

import COMMON_STUDENT.Student;
import COMMON_STUDENT.FileIO;
import org.apache.commons.lang3.Validate;

public class SearchStudentDB {

	private JFrame frmSearchStudentDatabase;
	private JComboBox comboBoxAttribute;
	private JTextField textFieldSearchValue;
	private JTextArea textAreaSearchResults;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchStudentDB window = new SearchStudentDB();
					window.frmSearchStudentDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchStudentDB() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchStudentDatabase = new JFrame();
		frmSearchStudentDatabase.setTitle("Search Student Database");
		frmSearchStudentDatabase.setBounds(100, 100, 674, 552);
		frmSearchStudentDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearchStudentDatabase.getContentPane().setLayout(null);

		JLabel lblSearchAttribute = new JLabel("Select attribute to search:");
		lblSearchAttribute.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSearchAttribute.setBounds(60, 60, 220, 24);
		frmSearchStudentDatabase.getContentPane().add(lblSearchAttribute);

		String[] StudentAttributeList = { "ALL", "Student ID", "Name", "Birth Date", "Student Type", "Major", "Credit Hours" };
		comboBoxAttribute = new JComboBox(StudentAttributeList);
		comboBoxAttribute.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		comboBoxAttribute.setBounds(290, 63, 185, 20);
		frmSearchStudentDatabase.getContentPane().add(comboBoxAttribute);

		JLabel lblEnterSearchValue = new JLabel("Enter value to search:");
		lblEnterSearchValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblEnterSearchValue.setBounds(60, 140, 220, 24);
		frmSearchStudentDatabase.getContentPane().add(lblEnterSearchValue);

		textFieldSearchValue = new JTextField();
		textFieldSearchValue.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textFieldSearchValue.setBounds(290, 143, 185, 20);
		frmSearchStudentDatabase.getContentPane().add(textFieldSearchValue);
		textFieldSearchValue.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					btnSubmit_CLICK();
				} catch (Exception e) {
					System.out.println(e.toString());
				}

			} // End of mouseClicked
		});
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnSubmit.setBounds(186, 213, 170, 29);
		frmSearchStudentDatabase.getContentPane().add(btnSubmit);

		JScrollPane scrollPaneSearchResults = new JScrollPane();
		scrollPaneSearchResults.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPaneSearchResults.setBounds(38, 288, 570, 167);
		frmSearchStudentDatabase.getContentPane().add(scrollPaneSearchResults);

		JLabel lblStudentSearchResults = new JLabel("Student Search Results");
		lblStudentSearchResults.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		scrollPaneSearchResults.setColumnHeaderView(lblStudentSearchResults);

		textAreaSearchResults = new JTextArea();
		textAreaSearchResults.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		scrollPaneSearchResults.setViewportView(textAreaSearchResults);

	} // End of initialize

	private void btnSubmit_CLICK() {

		// Refresh the text area of search results --> make it blank
		textAreaSearchResults.setText("");

		// -------------- First, validate all the text fields
		// If any problem, a dialog warning pops up to stop the program
		boolean isValidated = validateTextFields();

		if (!isValidated)
			return;

		// -------------- All the text fields have been validated
		FileIO fileIOHandler = new FileIO();

		// Declare the target file database: StudentDatabase.txt
		// MUST use \ to qualify '\' in the path of the file
		File fileDB = new File("/Users/hebachoudhry/Documents/JAVA/OUTPUTS/StudentDatabase.txt");

		int numStudentsInDb = 0; // number of students in DB
		int numFoundStudents = 0; // number of found students
		String[] StudentsDbArray; // Array of all Student records in DB --> array of
								// String
		Student[] foundStudentsArray = new Student[FileIO.getMaxNumLines()]; // Array of
																	// found
																	// students -->
																	// array of
																	// Student

		try {
			// Read all the records from Student file database
			numStudentsInDb = fileIOHandler.readLinesFromFile(fileDB);
			StudentsDbArray = fileIOHandler.getStrLinesArray();

			// Get the selected item from ComboBox
			String strSelectedAttribute = (String) comboBoxAttribute.getSelectedItem();

			// Get the search value
			String strSearchValue = textFieldSearchValue.getText();

			// Invoke searchstudents()
			numFoundStudents = searchStudents(numStudentsInDb, StudentsDbArray, foundStudentsArray, strSelectedAttribute,
					strSearchValue);

			// If no matched Student is found, display warning
			if (numFoundStudents == 0) {
				textAreaSearchResults.append("No matched Student is found in the database. \n");
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	} // End of btnSubmit_CLICK

	/****************************
	 * Name: validateTextFields Parameters: None Return: boolean --> TRUE: The
	 * search value text field is successfully validate --> FALSE: The text
	 * field has failed the validation Description: --> This method verify to be
	 * sure the search value text field contains valid data: --> Valid data: not
	 * null, not zero-size data, not empty String, not filled only with blank
	 * space --> If ISBN is selected as search attribute: valid data must also
	 * be numeric, i.e. only consisting of digits
	 * 
	 ****************************/

	private boolean validateTextFields() {

		boolean isValidated = true;

		// ----------- Validate the text field of search value
		// Need to validate for every search attribute except for "ALL"

		if (!comboBoxAttribute.getSelectedItem().equals("ALL")) {

			try {
				Validate.notBlank(textFieldSearchValue.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmSearchStudentDatabase,
						"The text field of search value must have valid values - Cannot be blank !!!");
				textFieldSearchValue.requestFocusInWindow(); // make it ready to
																// enter the
																// value
				textFieldSearchValue.selectAll(); // select all text in the text
													// field to delete it or to
													// replace it
				isValidated = false;
			}
		}

		// If any problem, stop the program
		if (!isValidated)
			return (isValidated);

		// For ID, also need to verify the entered value is a valid numeric
		if (comboBoxAttribute.getSelectedItem().equals("ID")) {
			try {
				Long.parseLong(textFieldSearchValue.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmSearchStudentDatabase, "ID must have a Numeric Value.");
				textFieldSearchValue.requestFocusInWindow(); // make it ready to
																// enter the
																// value
				textFieldSearchValue.selectAll(); // select all text in the text
													// field to delete it ot to
													// replace it
				isValidated = false;
			}
		}

		return (isValidated);

	} // validateTextFields

	/*******************
	 * Name: searchstudents Parameters: --> numstudents: number of students in the
	 * StudentArray --> studentsArray: array of strings, each string is a Student record
	 * (in DB) of CSV format --> foundstudentsArray: array of class Student elements
	 * that are found in the database --> aStrSearchAttr: a String that
	 * represents an attribute that is used to search --> aStrSearchValue: a
	 * String that represents the value of the attribute used to search Return
	 * --> This method returns an array of Student objects - students that are found
	 * in the database Description: This method performs a search for students
	 * whose attribute has the value that is matched with students in the Student
	 * array
	 ********************/

	private int searchStudents(int numStudents, String[] studentsArray, Student[] foundStudentsArray, String aStrSearchAttr,
			String aStrSearchValue) {

		int numFoundStudents = 0;

		if (aStrSearchAttr.equals("ALL")) {

			// ID is used to search for students
			numFoundStudents = searchStudentByAll(numStudents, studentsArray, foundStudentsArray);

		} // End of if (ID)

		if (aStrSearchAttr.equals("ID")) {

			// ID is used to search for students
			numFoundStudents = searchStudentByID(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (ID)

		if (aStrSearchAttr.equals("Name")) {

			// Name is used to search for students
			numFoundStudents = searchStudentByName(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (Name)

		if (aStrSearchAttr.equals("Birth Date")) {

			// BirthDate is used to search for students
			numFoundStudents = searchStudentByBirthDate(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (Birth Date)

		if (aStrSearchAttr.equals("Student Type")) {

			// Student Type is used to search for students
			numFoundStudents = searchStudentByStudentType(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (Student Type)

		if (aStrSearchAttr.equals("Major")) {

			// Credit Hours is used to search for students
			numFoundStudents = searchStudentByMajor(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (Major)

		if (aStrSearchAttr.equals("Credit Hours")) {

			// Credit Hours is used to search for students
			numFoundStudents = searchStudentByCreditHours(numStudents, aStrSearchValue, studentsArray, foundStudentsArray);

		} // End of if (Credit Hours)

		return (numFoundStudents);

	} // End of searchStudents

	private int searchStudentByAll(int numstudents, String[] studentsArray, Student[] foundstudentsArray) {

		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numstudents; i++) {

			aStrStudentRecord = studentsArray[i];

			// a Student is found --> add Student into found-Student array
			Student aFoundStudent = new Student(aStrStudentRecord);

			foundstudentsArray[numFoundStudents] = aFoundStudent;

			// Increment numFoundstudents to indicate one more Student is found
			numFoundStudents++;

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundstudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByAll

	private int searchStudentByID(int numStudents, String strID, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);

			// First convert string value to long
			long anID = Long.parseLong(strID);

			if (anID == aStudent.getStudentID()) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByID

	private int searchStudentByName(int numStudents, String strName, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		String strDbStudentName = ""; // Name of Student in database
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);

			strDbStudentName = aStudent.getFullName(); // Get name of the Student in db

			if ((strDbStudentName.equals(strName))) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByName

	private int searchStudentByBirthDate(int numStudents, String strBirthDate, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		String strDbStudentBirthDate = ""; // BirthDate of Student in database
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);

			strDbStudentBirthDate = aStudent.getBirthDate(); // Get BirthDate of the Student in db

			if ((strDbStudentBirthDate.equals(strBirthDate))) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByBirthDate

	private int searchStudentByStudentType(int numStudents, String strStudentType, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		String strDbStudentStudentType = ""; // StudentType of Student in database
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);

			strDbStudentStudentType = aStudent.getStudentType(); // Get StudentType of the
														// Student in db

			if ((strDbStudentStudentType.equals(strStudentType))) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByStudentType

	private int searchStudentByMajor(int numStudents, String strMajor, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		String strDbStudentMajor = ""; // Major of Student in database
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);

			strDbStudentMajor = aStudent.getMajor(); // Get major of
														// the Student in db

			if ((strDbStudentMajor.equals(strMajor))) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByMajor

	private int searchStudentByCreditHours(int numStudents, String strCreditHours, String[] studentsArray, Student[] foundStudentsArray) {

		Student aStudent;
		int numFoundStudents = 0;
		String aStrStudentRecord = "";

		for (int i = 0; i < numStudents; i++) {

			aStrStudentRecord = studentsArray[i];
			aStudent = Student.recreateStudentFromString(aStrStudentRecord);
			
			long creditHours = Long.parseLong(strCreditHours);

			if (creditHours == aStudent.getCreditHours()) {
				// a Student is found --> add Student into found-Student array
				Student aFoundStudent = new Student(aStrStudentRecord);

				foundStudentsArray[numFoundStudents] = aFoundStudent;

				// Increment numFoundstudents to indicate one more Student is found
				numFoundStudents++;
			}

		} // End of for (scan Student array)

		// Write Student record of each found Student into the text area (Student search
		// results)
		for (int j = 0; j < numFoundStudents; j++) {

			// Append a Student record into the search results text area
			textAreaSearchResults.append((foundStudentsArray[j]).toString() + "\n");

		}

		return (numFoundStudents);

	} // End of searchStudentByCreditHours
} // End of class SearchStudentDb

