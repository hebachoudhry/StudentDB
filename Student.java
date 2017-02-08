package COMMON_STUDENT;

public class Student {
	
	//--------------- Declare attributes
	private int studentID;
	private String fullName;
	private String birthDate;
	private String studentType;
	private String major; 
	private int creditHours; 
	
	//--------------- Declare constructors
	public Student() {
		
		studentID = 0;
		fullName = "";
		birthDate = "";
		studentType = "";
		major = "";
		creditHours = 0;
		
	} // End of default constructor

	// Another constructor
	public Student(int anID, String aFullName) {
		
		studentID = anID;
		fullName = aFullName;
		birthDate = "";
		studentType = "";
		major = "";
		creditHours = 0;
		
	} // End of another constructor

	// Another constructor
	// This constructor recreates a student object out of a string of student info
	// The string aStrStudentData is of CSV format - has been verified: NOT NULL and NOT EMPTY STRING
	public Student(String aStrStudentData) {
				
		Student aStudent = recreateStudentFromString(aStrStudentData);
		
		studentID = aStudent.getStudentID();
		fullName = aStudent.getFullName();
		birthDate = aStudent.getBirthDate();
		studentType = aStudent.getStudentType();
		major = aStudent.getMajor();
		creditHours = aStudent.getCreditHours();
		
	} // End of another constructor

	//--------------- GET & SET methods
	
	public int getStudentID() {
		return (studentID);
	}
	
	public void setStudentID(int anID) {
		studentID = anID;
	}
	
	public String getFullName() {
		return (fullName);
	}
	
	public void setFullName(String aFullName) {
		fullName = aFullName;
	}
	
	public String getBirthDate() {
		return (birthDate);
	}
	
	public void setBirthDate(String aBirthDate) {
		birthDate = aBirthDate;
	}
	
	public String getStudentType() {
		return (studentType);
	}
	
	public void setStudentType(String aStudentType) {
		studentType = aStudentType;
	}

	public String getMajor() {
		return (major);
	}
	
	public void setMajor(String aMajor) {
		major = aMajor;
	}
	
	public int getCreditHours() {
		return (creditHours);
	}
	
	public void setCreditHours(int aCreditHours) {
		creditHours = aCreditHours;
	}
	
	//--------------- Other methods
	
	// This method returns a string that stores all data fields of a student record
	// The fields are separated by a comma ','
	// The string can be used to insert a student record (a line) into a .CSV file database
	// This method overrides the same method of class Object from which all Java classes inherit
	@Override
	public String toString() {
				
		String StudentStr = Long.toString(studentID) 
				+ ", " + fullName 
				+ ", " + birthDate 
				+ ", " + studentType 
				+ ", " + major 
				+ ", " + creditHours;

				
		return (StudentStr);
		
	} // End of toString
	
	// This method recreate a Student object out of a string of CSV format.
	// This string contains all data fields of the student
	// After recreating the student, the method returns the object
	public static Student recreateStudentFromString(String aStrStudentData) {
		
		Student aStudent = new Student();
		
		if (aStrStudentData.isEmpty()) {
			return (null);
		}
		
		int preCommaIndex = 0; // index of the preceding comma in the CSV string
		int nextCommaIndex = 0; // index of the next comma in the CSV string
		String nextSubstring = "";
		
		//---------- Retrieve the ID number
		// Get the index of the 1st comma that separates ID and student full name
		nextCommaIndex = aStrStudentData.indexOf(',');
		
		// Get the substring of the ID number
		// BE CAREFUL!!!: substring (int startIndex, int endIndex) - See manual of this method
		// Actually, the substring starts at startIndex and extends to (endIndex - 1) - NOT endIndex
		// So, endIndex must be nextCommaIndex, NOT nextCommaIndex - 1
		nextSubstring = aStrStudentData.substring(0, nextCommaIndex);
		
		// Convert this string to long'
		int anID = (int) Long.parseLong(nextSubstring);
		
		// Set ID number
		aStudent.setStudentID(anID);
		
		//---------- Retrieve the full name
		// Get the index of the 2nd comma that separates full name and birth date
		preCommaIndex = nextCommaIndex;
		nextCommaIndex = aStrStudentData.indexOf(',', preCommaIndex + 1);
		
		// Get the substring of full name
		nextSubstring = aStrStudentData.substring(preCommaIndex + 1, nextCommaIndex);
				
		// Set full name
		aStudent.setFullName(nextSubstring);

		//---------- Retrieve the birth date
		// Get the index of the 3rd comma that separates birth date and student type
		preCommaIndex = nextCommaIndex;
		nextCommaIndex = aStrStudentData.indexOf(',', preCommaIndex + 1);
		
		// Get the substring of birth date
		nextSubstring = aStrStudentData.substring(preCommaIndex + 1, nextCommaIndex);
				
		// Set birth date
		aStudent.setBirthDate(nextSubstring);
		
		//---------- Retrieve the student type
		// Get the index of the 4th comma that separates student type and major
		preCommaIndex = nextCommaIndex;
		nextCommaIndex = aStrStudentData.indexOf(',', preCommaIndex + 1);
		
		// Get the substring of student type
		nextSubstring = aStrStudentData.substring(preCommaIndex + 1, nextCommaIndex);
				
		// Set student type
		aStudent.setStudentType(nextSubstring);

		//---------- Retrieve major
		// Get the index of the 5th comma that separates major and credit hours
		preCommaIndex = nextCommaIndex;
		nextCommaIndex = aStrStudentData.indexOf(',', preCommaIndex + 1);
		
		// Get the substring of major
		nextSubstring = aStrStudentData.substring(preCommaIndex + 1, nextCommaIndex);
				
		// Set major
		aStudent.setMajor(nextSubstring);

		//---------- Retrieve credit hours		
		// Get the substring of credit hours - last substring starting from index of last comma + 1
		preCommaIndex = nextCommaIndex;
		nextSubstring = aStrStudentData.substring(preCommaIndex + 1);
		
		// Convert String to Long
		long aCreditHours = Long.parseLong(nextSubstring);
		
		// Set credit hours
		aStudent.setCreditHours((int) aCreditHours);
		
		return (aStudent);
	}
	
	public void displayStudentInfo() {
		
		System.out.println(toString());
		
	} // End of displayStudentInfo

} // End of class Student

