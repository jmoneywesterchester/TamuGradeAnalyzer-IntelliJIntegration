/**
 * Created by JonathanWesterfield on 2/10/17.
 * This class is nothing but functions that allow the user to access the database
 * through the application. As such it is very long and has very many functions
 */

/* Contains the functions: getAllSubjectDistinct, getAllCourseNumDistinct, getCourseProfessors,
 * getNumASem, getNumBSem, getNumCSem, getNumDSem, getNumFSem, getNumA, getNumB, getNumC, getNumD, getNumF,
 * getNumQDrop, getAvgGPA, getAvgGPASem, getTotalNumStudentsTaught, getPercentA, getPercentB,
  * getPercentC, getPercentD, getPercentF, getPercentQDrops,  getNumSemestersTaught,
  * getProfRawData and the insert into table */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.*;

public class DatabaseAPI
{
    private String connectionString = "jdbc:mysql://tamudata.cgmm1m5yk0wt.us-east-2." +
            "rds.amazonaws.com:3306/TamuData";
    private String password = "TamuDefaultUserHullabaloo2019WHO0P!"; // password for a default user account
    private String username = "DefaultUser"; // default username
    private Connection conn;

    private int numberA;
    private int numberB;
    private int numberC;
    private int numberD;
    private int numberF;
    private int numberQ;
    private double averageGPA;
    private int totalNumStudents;

    private double percentageA;
    private double percentageB;
    private double percentageC;
    private double percentageD;
    private double percentageF;
    private double percentageQ;

    private ArrayList<String> subjects;

    String courseSubject;
    int courseNum;
    String professor;

    /** Main function for testing the class*/
    public static void main(String[] args) {
        String connectionString = "jdbc:mysql://localhost:8889/TamuData";
        String password = "TamuDefaultUserHullabaloo2019WHO0P!"; // password for a default user account
        String username = "DefaultUser"; // default username

        try
        {
            // DatabaseAPI db = new DatabaseAPI("CSCE", 121, "MOORE");

            DatabaseAPI db = new DatabaseAPI();
            /*ArrayList<String> subjects = db.getAllSubjectDistinct();
            for(int i = 0; i < subjects.size(); i++)
            {
                System.out.println(subjects.get(i));
            }

            ArrayList<Integer> courseNumbers = db.getAllCourseNumDistinct("MATH");
            for(int i = 0; i < courseNumbers.size(); i++)
            {
                System.out.println(courseNumbers.get(i));
            }

            ArrayList<String> professors = db.getCourseProfessors("CSCE", 121);
            for(int i = 0; i < professors.size(); i++)
            {
                System.out.println(professors.get(i));
            }

            db.getCourseProfessors("MATH", 152);
            db.getNumA("CSCE", 121, "MOORE");
            db.getNumB("CSCE", 121, "MOORE");
            db.getNumC("CSCE", 121, "MOORE");
            db.getNumD("CSCE", 121, "MOORE");
            db.getNumF("CSCE", 121, "MOORE");
            db.getNumQDrop("CSCE", 121, "MOORE");
            db.getTotalNumStudentsTaught("CSCE", 121, "MOORE");
            System.out.println("Number Semesters " + db.getNumSemestersTaught("CSCE", 121, "MOORE"));


            ArrayList<String> rawData = db.getProfRawData("CSCE", 121, "MOORE");
            for(int i = 0; i < rawData.size(); i++)
            {
                if(i % 10 == 0)
                {
                    //System.out.println();
                    System.out.print("\n" + rawData.get(i) + " ");
                }
                else
                    System.out.print(rawData.get(i) + " ");
            }

            double avgGPA = db.getAvgGPA("CSCE", 121, "MOORE");
            db.getNumASem("CSCE", 121, "MOORE", "fall", 2012);
            db.getPastSemesterGPAs("CSCE", 121, "MOORE"); */

            db.closeDBConn();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // class constructor that throws an exception to the function caller
    DatabaseAPI() throws java.sql.SQLException, java.lang.ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");

        // gets all the subjects for the initial app startup
        getAllSubjectDistinct();
    }

    // constructor that will get all of the info
    DatabaseAPI(String courseSubject, int courseNum, String professor) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");

        this.courseSubject = courseSubject;
        this.courseNum = courseNum;
        this.professor = professor;

        // create all of the data for the class for the given professor
        getNumA();
        getNumB();
        getNumC();
        getNumD();
        getNumF();
        getNumQDrop();

        getAvgGPA();

        getTotalNumStudentsTaught();

        getPercentA();
        getPercentB();
        getPercentC();
        getPercentD();
        getPercentF();
        getPercentQDrops();

        getTotalNumStudentsTaught();
    }

    public void createDBConn() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");
        return;
    }

    //getter functions for all of the data members
    public int getNumberA()
    {
        return numberA;
    }

    public int getNumberB()
    {
        return numberB;
    }

    public int getNumberC()
    {
        return numberC;
    }

    public int getNumberD()
    {
        return numberD;
    }

    public int getNumberF()
    {
        return numberF;
    }

    public int getNumberQ()
    {
        return numberQ;
    }

    public double getAverageGPA()
    {
        return averageGPA;
    }

    public int getTotalNumStudents()
    {
        return  totalNumStudents;
    }

    public double getPercentageA()
    {
        return percentageA;
    }

    public double getPercentageB()
    {
        return percentageB;
    }

    public double getPercentageC()
    {
        return percentageC;
    }

    public double getPercentageD()
    {
        return percentageD;
    }

    public double getPercentageF()
    {
        return percentageF;
    }

    public double getPercentageQ()
    {
        return percentageQ;
    }

    public String getCourseSubject()
    {
        return courseSubject;
    }

    public int getCourseNum()
    {
        return courseNum;
    }

    public String getProfessor()
    {
        return professor;
    }

    public ArrayList<String> getSubjects()
    {
        return subjects;
    }

    /** Manipuates the 'subject' arraylist data member and stores all of the
     * subjects in the database in it in alphabetical order.
     * This is the function that is used for the other classes
     *
     * @throws SQLException
     */
    private void getAllSubjectDistinct() throws SQLException
    {
        String query1 = "SELECT DISTINCT CourseSubject FROM TamuGrades ORDER BY CourseSubject ASC";
        System.out.println("\nSelecting Distinct From Subject");
        Statement selectDistinctSubject = conn.createStatement();
        ResultSet result1 = selectDistinctSubject.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<String> allSubjects = new ArrayList<String>();
        while(result1.next())
        {
            String subject = result1.getString("CourseSubject");
            allSubjects.add(subject);
            System.out.printf("%d\t%s\n", count, subject);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT CourseSubject) FROM TamuGrades";
        System.out.println("Counting number of subjects in Database");
        Statement countCourses = conn.createStatement();
        ResultSet result2 = countCourses.executeQuery(query2);

        System.out.println("Getting number of subjects");

        //printing out the result of the SQL query
        while(result2.next())
        {
            int numSubjects = result2.getInt(1);
            System.out.println("Number of Subjects in database = " + numSubjects);
        }
        this.subjects = allSubjects;
    }

    /** returns an arraylist of all subjects in database in alphabetical order.
     *
     * @return allSubjects which is an arraylist of all of the subjects in the database
     * @throws SQLException
     */
    public ArrayList<String> getAllSubjectDistinctList() throws SQLException
    {
        String query1 = "SELECT DISTINCT CourseSubject FROM TamuGrades ORDER BY CourseSubject ASC";
        System.out.println("\nSelecting Distinct From Subject");
        Statement selectDistinctSubject = conn.createStatement();
        ResultSet result1 = selectDistinctSubject.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<String> allSubjects = new ArrayList<String>();
        while(result1.next())
        {
            String subject = result1.getString("CourseSubject");
            allSubjects.add(subject);
            System.out.printf("%d\t%s\n", count, subject);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT CourseSubject) FROM TamuGrades";
        System.out.println("Counting number of subjects in Database");
        Statement countCourses = conn.createStatement();
        ResultSet result2 = countCourses.executeQuery(query2);

        System.out.println("Getting number of subjects");

        //printing out the result of the SQL query
        while(result2.next())
        {
            int numSubjects = result2.getInt(1);
            System.out.println("Number of Subjects in database = " + numSubjects);
        }

        return allSubjects;
    }

    /** returns an arraylist of all course numbers under a specific subject
     *
     * @param courseSubject
     * @return allCourseNums which are all of the courseNumbers for that subject
     * @throws SQLException
     */
    public ArrayList<Integer> getAllCourseNumDistinct(String courseSubject) throws SQLException
    {
        String query1 = "SELECT DISTINCT CourseNum FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\"";
        System.out.println("\nSelecting Distinct From CourseNum");
        Statement selectDistinctCourseNum = conn.createStatement();
        ResultSet result1 = selectDistinctCourseNum.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<Integer> allCourseNums = new ArrayList<Integer>();
        while(result1.next())
        {
            int courseNum = result1.getInt("CourseNum");
            allCourseNums.add(courseNum);
            System.out.printf("%d\t%d\n", count, courseNum);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT CourseNum) AS total FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\"";
        System.out.println("Counting number of course numbers in Database where " +
                "subject is " + courseSubject);
        Statement countCourses = conn.createStatement();
        ResultSet result2 = countCourses.executeQuery(query2);

        System.out.println("Getting number of Course Numbers");

        //printing out the result of the SQL query
        while(result2.next())
        {
            int numCourseNums = result2.getInt(1);
            System.out.println("Number of CourseNums in database = " + numCourseNums +
                " where Course Subject is " + courseSubject);
        }
        return allCourseNums;
    }

    /** Returns an arraylist of the professors in alphabetical order
     *
     * @param courseSubject
     * @param courseNum
     * @return allCourseProfessors which are all of the professors listed for this course
     * @throws SQLException
     */
    public ArrayList<String> getCourseProfessors(String courseSubject, int courseNum) throws SQLException
    {
        String query1 = "SELECT DISTINCT Professor FROM TamuGrades WHERE CourseSubject=\""
                 + courseSubject + "\" AND CourseNum=" + courseNum + " AND Honors=FALSE " +
                "ORDER BY Professor ASC";
        System.out.println("\nLooking for professors of this subject and course");
        Statement getProfessors = conn.createStatement();
        ResultSet result1 = getProfessors.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<String> allCourseProfessors = new ArrayList<>();
        while(result1.next())
        {
            String professor = result1.getString("Professor");
            allCourseProfessors.add(professor);
            System.out.printf("%d\t%s\n", count, professor);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT Professor) AS total FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\" AND CourseNum=" + courseNum;
        System.out.println("Counting number of professors teaching this course");
        Statement countProfessors = conn.createStatement();
        ResultSet result2 = countProfessors.executeQuery(query2);

        //printing out the result of the SQL query
        while(result2.next())
        {
            int totalNumProfessors = result2.getInt(1);
            System.out.println("Number of Professors teaching this course = "
                    + totalNumProfessors + "\n");
        }
        return allCourseProfessors;
    }

    /** gets the number of A's for a class for a certain semester and year
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return totalNumA which is the number of A's for that semester
     * @throws SQLException
     */
    public int getNumASem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumA = 0;
        String query = "SELECT SUM(NumA) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year + " AND Honors=FALSE";
        System.out.println("\nCounting number of A's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumA = result.getInt(1);
            System.out.println("The total number of A's for " + term + " "
                    + year + " is " + totalNumA);
        }
        return totalNumA;
    }

    /** gets the number of B's for a class for a certain semester and year
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return totalNumB which is the number of B's for that semester
     * @throws SQLException
     */
    public int getNumBSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumB = 0;
        String query = "SELECT SUM(NumB) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year +" AND Honors=FALSE";
        System.out.println("\nCounting number of B's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumB = result.getInt(1);
            System.out.println("The total number of B's for " + term + " "
                    + year + " is " + totalNumB);
        }
        return totalNumB;
    }

    /** gets the number of C's for a class for a certain semester and year
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return totalNumC which is the number of C's for that semester
     * @throws SQLException
     */
    public int getNumCSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumC = 0;
        String query = "SELECT SUM(NumC) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year +" AND Honors=FALSE";
        System.out.println("\nCounting number of C's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumC = result.getInt(1);
            System.out.println("The total number of C's for " + term + " "
                    + year + " is " + totalNumC);
        }
        return totalNumC;
    }

    /** gets the number of D's for a class for a certain semester and year
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return totalNumD which is the number of D's for that semester
     * @throws SQLException
     */
    public int getNumDSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumD = 0;
        String query = "SELECT SUM(NumD) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year +" AND Honors=FALSE";
        System.out.println("\nCounting number of D's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumD = result.getInt(1);
            System.out.println("The total number of D's for " + term + " "
                    + year + " is " + totalNumD);
        }
        return totalNumD;
    }

    /** gets the number of F's for a class for a certain semester and year
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return totalNumF which is the number of F's for that semester
     * @throws SQLException
     */
    public int getNumFSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumF = 0;
        String query = "SELECT SUM(NumF) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year + " AND Honors=FALSE";
        System.out.println("\nCounting number of F's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumF = result.getInt(1);
            System.out.println("The total number of F's for " + term + " "
                    + year + " is " + totalNumF);
        }
        return totalNumF;
    }

    /** counts total number of A's given by a professor in a specific subject and course number.
     * This is the main function used by the other classes */
    private void getNumA() throws SQLException
    {
        int totalNumA = 0;
        String query = "SELECT SUM(NumA) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of A's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumA = result.getInt(1);
            System.out.println("The total number of A's is " + totalNumA);
        }

        this.numberA = totalNumA;
    }

    /** counts total number of A's given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalNumA which is the number of A's for the course
     * @throws SQLException
     */
    public int getNumA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumA = 0;
        String query = "SELECT SUM(NumA) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of A's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumA = result.getInt(1);
            System.out.println("The total number of A's is " + totalNumA);
        }
        return totalNumA;
    }

    /** counts total number of B's given by a professor in a specific subject and course number.
     * This is the main function used by the other classes */
    private void getNumB() throws SQLException
    {
        int totalNumB = 0;
        String query = "SELECT SUM(NumB) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of B's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumB = result.getInt(1);
            System.out.println("The total number of B's is " + totalNumB);
        }

        this.numberB = totalNumB;
    }

    /** counts total number of B's given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalNumB which is the number of B's for the course
     * @throws SQLException
     */
    public int getNumB(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumB = 0;
        String query = "SELECT SUM(NumB) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of B's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumB = result.getInt(1);
            System.out.println("The total number of B's is " + totalNumB);
        }
        return totalNumB;
    }

    /** counts total number of C's given by a professor in a specific subject and course number.
     * This is the main function used by the other classes */
    private void getNumC() throws SQLException
    {
        int totalNumC = 0;
        String query = "SELECT SUM(NumC) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of C's given by this professor in this course");
        Statement countNumC = conn.createStatement();
        ResultSet result = countNumC.executeQuery(query);

        while(result.next())
        {
            totalNumC = result.getInt(1);
            System.out.println("The total number of C's is " + totalNumC);
        }

        this.numberC = totalNumC;
    }

    /** counts total number of C's given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalNumC which is the number of C's for the course
     * @throws SQLException
     */
    public int getNumC(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumC = 0;
        String query = "SELECT SUM(NumC) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of C's given by this professor in this course");
        Statement countNumC = conn.createStatement();
        ResultSet result = countNumC.executeQuery(query);

        while(result.next())
        {
            totalNumC = result.getInt(1);
            System.out.println("The total number of C's is " + totalNumC);
        }
        return totalNumC;
    }

    /** counts total number of D's given by a professor in a specific subject and course number.
     * This is the main function used by the other classes */
    private void getNumD() throws SQLException
    {
        int totalNumD = 0;
        String query = "SELECT SUM(NumD) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of D's given by this professor in this course");
        Statement countNumD = conn.createStatement();
        ResultSet result = countNumD.executeQuery(query);

        while(result.next())
        {
            totalNumD = result.getInt(1);
            System.out.println("The total number of D's is " + totalNumD);
        }

        this.numberD = totalNumD;
    }

    /** counts total number of D's given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalNumD which is the number of D's for the course
     * @throws SQLException
     */
    public int getNumD(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumD = 0;
        String query = "SELECT SUM(NumD) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of D's given by this professor in this course");
        Statement countNumD = conn.createStatement();
        ResultSet result = countNumD.executeQuery(query);

        while(result.next())
        {
            totalNumD = result.getInt(1);
            System.out.println("The total number of D's is " + totalNumD);
        }
        return totalNumD;
    }

    /** counts total number of F's given by a professor in a specific subject and course number.
     * Stores it in the numberF data member
     * This is the main function used by the other classes
     *
     * @throws SQLException
     */
    private void getNumF() throws SQLException
    {
        int totalNumF = 0;
        String query = "SELECT SUM(NumF) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of F's given by this professor in this course");
        Statement countNumF = conn.createStatement();
        ResultSet result = countNumF.executeQuery(query);

        while(result.next())
        {
            totalNumF = result.getInt(1);
            System.out.println("The total number of F's is " + totalNumF);
        }

        this.numberF = totalNumF;
    }

    /** counts total number of F's given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalNumF which is the number of F's for the course
     * @throws SQLException
     */
    public int getNumF(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumF = 0;
        String query = "SELECT SUM(NumF) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of F's given by this professor in this course");
        Statement countNumF = conn.createStatement();
        ResultSet result = countNumF.executeQuery(query);

        while(result.next())
        {
            totalNumF = result.getInt(1);
            System.out.println("The total number of F's is " + totalNumF);
        }
        return totalNumF;
    }

    /** counts total number of Q Drops given by a professor in a specific subject and course number.
     * This is the main function used by the other classes
     *
     * @throws SQLException
     */
    private void getNumQDrop() throws SQLException
    {
        int totalQDrop = 0;
        String query = "SELECT SUM(Num_QDrop) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of Q Drops given by this professor in this course");
        Statement countQDrop = conn.createStatement();
        ResultSet result = countQDrop.executeQuery(query);

        while(result.next())
        {
            totalQDrop = result.getInt(1);
            System.out.println("The total number of Q Drops is " + totalQDrop);
        }

        this.numberQ = totalQDrop;
    }

    /** counts total number of Q Drops given by a professor in a specific subject and course number.
     * However, this one is just in case I need a random SQL query and can't re-setup the class
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalQDrop
     * @throws SQLException
     */
    public int getNumQDrop(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalQDrop = 0;
        String query = "SELECT SUM(Num_QDrop) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Honors=FALSE";
        System.out.println("\nCounting number of Q Drops given by this professor in this course");
        Statement countQDrop = conn.createStatement();
        ResultSet result = countQDrop.executeQuery(query);

        while(result.next())
        {
            totalQDrop = result.getInt(1);
            System.out.println("The total number of Q Drops is " + totalQDrop);
        }
        return totalQDrop;
    }

    /** calculates the average GPA for the professor of this subject and course
     *
     * @throws SQLException
     */
    private void getAvgGPA() throws SQLException
    {
        //gets the total number of A's, B's, etc. for this professor
        int numA = numberA;
        int numB = numberB;
        int numC = numberC;
        int numD = numberD;
        int numF = numberF;
        int total = numA + numB + numC + numD + numF;

        // weights the numbers
        numA *= 4;
        numB *= 3;
        numC *= 2;
        numD *= 1; // redundant but is there to help see the pattern
        numF *= 0; // again redundant but is to help see the pattern

        System.out.printf("Weighted Numbers:\nNumA: %d\nNumB: %d\nNumC: %d\n", numA, numB, numC);
        System.out.printf("NumD: %d\nNumF: %d\n" ,numD, numF);

        // adds the weighted points
        double totalPoints = numA + numB + numC + numD + numF;

        // divides by the total to get the average
        totalPoints /= total;

        System.out.println("The average GPA is: " + totalPoints);

        averageGPA = totalPoints;
    }

    /** calculates the average GPA for the professor of this subject and course independently
     * of the this class.
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalPoints which is the average GPA
     * @throws SQLException
     */
    public double getAvgGPA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        //gets the total number of A's, B's, etc. for this professor
        int numA = getNumA(courseSubject, courseNum, professor);
        int numB = getNumB(courseSubject, courseNum, professor);
        int numC = getNumC(courseSubject, courseNum, professor);
        int numD = getNumD(courseSubject, courseNum, professor);
        int numF = getNumF(courseSubject, courseNum, professor);
        int total = numA + numB + numC + numD + numF;

        // weights the numbers
        numA *= 4;
        numB *= 3;
        numC *= 2;
        numD *= 1; // redundant but is there to help see the pattern
        numF *= 0; // again redundant but is to help see the pattern

        System.out.printf("Weighted Numbers:\nNumA: %d\nNumB: %d\nNumC: %d\n", numA, numB, numC);
        System.out.printf("NumD: %d\nNumF: %d\n" ,numD, numF);

        // adds the weighted points
        double totalPoints = numA + numB + numC + numD + numF;

        // divides by the total to get the average
        totalPoints /= total;

        System.out.println("The average GPA is: " + totalPoints);

        averageGPA = totalPoints;

        return totalPoints;
    }

    /** Counts the number of students a Professor has taught for a particular course
     * Uses the private data members that already exist when the class is created
     */
    private void getTotalNumStudentsTaught()
    {

        System.out.println("\nCounting number of students this professor has taught");

        int totalStudents = numberA + numberB + numberC + numberD + numberF + numberQ;

        this.totalNumStudents = totalStudents;
    }

    /** Counts the number of students a Professor has taught for a particular course.
     * Is used independently of the class in case I it would be best not to re-setup the class.
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return totalStudents which is the total number of students this professor has taught
     *          for this course
     * @throws SQLException
     */
    public int getTotalNumStudentsTaught(String courseSubject, int courseNum,
                                         String professor) throws SQLException
    {
        String query = "SELECT SUM(Num_QDrop + NumA + NumB + NumC + NumD + NumF) FROM" +
                " TamuGrades WHERE CourseSubject=\"" + courseSubject + "\" AND CourseNum=" + courseNum +
                " AND Professor=\"" + professor + "\"AND Honors=FALSE";
        System.out.println("\nCounting number of students this professor has taught");
        Statement getTotalStudents = conn.createStatement();
        ResultSet result = getTotalStudents.executeQuery(query);

        int totalStudents = 0;
        while(result.next())
        {
            totalStudents = result.getInt(1);
            System.out.println("The total number of students this professor has taught is " + totalStudents);
        }

        return totalStudents;
    }

    /**
     * Calculates the percentage of A's given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    //uses data in class to get percentage of A's
    private void getPercentA() throws SQLException
    {
        double total = totalNumStudents;
        double numA = numberA;

        System.out.println("\nThe percentage of A's for this course is " + ((numA / total) * 100));

        this.percentageA = ((numA / total) * 100);
    }

    /**
     * Calculates the percentage of A's given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of A's (double)
     * @throws SQLException
     */
    public double getPercentA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numA = getNumA(courseSubject, courseNum, professor);

        System.out.println("\nThe percentage of A's for this course is " + ((numA / total) * 100));

        return ((numA / total) * 100);
    }

    /**
     * Calculates the percentage of B's given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    private void getPercentB() throws SQLException
    {
        double total = totalNumStudents;
        double numB = numberB;

        System.out.println("\nThe percentage of B's for this course is " + ((numB / total) * 100));

        this.percentageB = ((numB / total) * 100);
    }

    /**
     * Calculates the percentage of B's given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of B's (double)
     * @throws SQLException
     */
    public double getPercentB(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numB = getNumB(courseSubject, courseNum, professor);

        System.out.println("\nThe percentage of B's for this course is " + ((numB / total) * 100));

        return ((numB / total) * 100);
    }

    /**
     * Calculates the percentage of C's given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    private void getPercentC() throws SQLException
    {
        double total = totalNumStudents;
        double numC = numberC;

        System.out.println("\nThe percentage of C's for this course is " + ((numC / total) * 100));

        this.percentageC = ((numC / total) * 100);
    }

    /**
     * Calculates the percentage of C's given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of C's (double)
     * @throws SQLException
     */
    public double getPercentC(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numC = getNumC(courseSubject, courseNum, professor);

        System.out.println("\nThe percentage of C's for this course is " + ((numC / total) * 100));

        return ((numC / total) * 100);
    }

    /**
     * Calculates the percentage of D's given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    private void getPercentD() throws SQLException
    {
        double total = totalNumStudents;
        double numD = numberD;

        System.out.println("\nThe percentage of D's for this course is " + ((numD / total) * 100));

        this.percentageD = ((numD / total) * 100);
    }

    /**
     * Calculates the percentage of D's given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of D's (double)
     * @throws SQLException
     */
    public double getPercentD(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numD = getNumD(courseSubject, courseNum, professor);

        System.out.println("\nThe percentage of D's for this course is " + ((numD / total) * 100));

        return ((numD / total) * 100);
    }

    /**
     * Calculates the percentage of F's given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    private void getPercentF() throws SQLException
    {
        double total = totalNumStudents;
        double numF = numberF;

        System.out.println("\nThe percentage of F's for this course is " + ((numF / total) * 100));

        this.percentageF = ((numF / total) * 100);
    }

    /**
     * Calculates the percentage of F's given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of F's (double)
     * @throws SQLException
     */
    public double getPercentF(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numF = getNumF(courseSubject, courseNum ,professor);

        System.out.println("\nThe percentage of F's for this course is " + ((numF / total) * 100));

        return ((numF / total) * 100);
    }

    /**
     * Calculates the percentage of Q Drops given in this course using the private data members
     * calculated when the class is initialized. The main function used for other classes
     *
     * @throws SQLException
     */
    private void getPercentQDrops() throws SQLException
    {
        double total = totalNumStudents;
        double numQDrops = numberQ;

        System.out.println("\nThe percentage of Q drops for this course is " + ((numQDrops / total) * 100));

        this.percentageQ = ((numQDrops / total) * 100);
    }

    /**
     * Calculates the percentage of Q Drops given by a teacher
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return percentage of Q Drops (double)
     * @throws SQLException
     */
    public double getPercentQDrops(String courseSubject, int courseNum, String professor) throws SQLException
    {
        double total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        double numQDrops = getNumQDrop(courseSubject, courseNum, professor);

        System.out.println("\nThe percentage of Q drops for this course is " + ((numQDrops / total) * 100));

        return ((numQDrops / total) * 100);
    }

    /**
     *
     * If there is no result for a year, the function just returns zero
     * Calculates the average GPA for a class for a specific semester
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @param term
     * @param year
     * @return
     * @throws SQLException
     */
    public double getAvgGPASem(String courseSubject, int courseNum, String professor,
                               String term, int year) throws SQLException
    {
        //gets the total number of A's, B's, etc. for this professor
        int numASem = getNumASem(courseSubject, courseNum, professor, term, year);
        int numBSem = getNumBSem(courseSubject, courseNum, professor, term, year);
        int numCSem = getNumCSem(courseSubject, courseNum, professor, term, year);
        int numDSem = getNumDSem(courseSubject, courseNum, professor, term, year);
        int numFSem = getNumFSem(courseSubject, courseNum, professor, term, year);
        int total = numASem + numBSem + numCSem + numDSem + numFSem;

        System.out.println("Calculated the average GPA for " + term + " "
                        + year + ".");

        // weights the numbers
        numASem *= 4;
        numBSem *= 3;
        numCSem *= 2;
        numDSem *= 1; // redundant but is there to help see the pattern
        numFSem *= 0; // again redundant but is to help see the pattern

        System.out.printf("Weighted Numbers:\nNumA: %d\nNumB: %d\nNumC: %d\n", numASem, numBSem, numCSem);
        System.out.printf("NumD: %d\nNumF: %d\n" ,numDSem, numFSem);

        // in case the records don't exist and return 0
        if(total == 0)
        {
            System.out.println("The average for " + term + " " + year + " GPA is: " + total);
            return 0;
        }

        // adds the weighted points
        double totalPoints = numASem + numBSem + numCSem + numDSem + numFSem;

        // divides by the total to get the average
        totalPoints /= total;

        System.out.println("The average for " + term + " " + year + " GPA is: " + totalPoints);

        return totalPoints;
    }

    /**
     * returns an array of the average GPAs for the last 5 years. Is sized accordingly in case a
     * professor has taught for less than 5 years
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return
     * @throws SQLException
     */
    public ArrayList<Double> getPastSemesterGPAs(String courseSubject, int courseNum, String professor)
            throws SQLException
    {
        ArrayList<Double> GPAList = new ArrayList<Double>();

        int year = Calendar.getInstance().get(Calendar.YEAR) - 5;

        for(int i = 0; i < 5; i++) // goes back 5 years in the database
        {
            // decision structure for getting the GPA of both terms of a year
            for(int j = 0; j < 2; j++)
            {
                if(j == 0 && getAvgGPASem(courseSubject, courseNum, professor, "SPRING", year) != 0)
                {
                    // Gets the grades for that year during the spring
                    GPAList.add(getAvgGPASem(courseSubject, courseNum, professor, "SPRING", year));
                }
                if(j == 1 && getAvgGPASem(courseSubject, courseNum, professor, "FALL", year) != 0)
                {
                    // Gets the grades for that year during the fall
                    GPAList.add(getAvgGPASem(courseSubject, courseNum, professor, "FALL", year));
                }
            }
            year++;
        }
        //prints the contents of GPAList to make sure they are correct
        System.out.println("\nThe recorded GPA's for each semester are:");
        for(int i = 0; i < GPAList.size(); i++)
        {
            System.out.println(GPAList.get(i));
        }

        return GPAList;
    }

    // gets the semester terms to match the output of the getPastSemesterGPAs function

    /**
     * gets the semester terms to match the output of the getPastSemesterGPAs function
     *
     * @param courseSubject
     * @param courseNum
     * @param professor
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getPastSemesters(String courseSubject, int courseNum, String professor)
            throws SQLException
    {
        ArrayList<String> semesterList = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR) - 5;

        for(int i = 0; i < 5; i++) // goes back 5 years in the database
        {
            // decision structure for getting the GPA of both terms of a year
            for(int j = 0; j < 2; j++)
            {
                if(j == 0 && getAvgGPASem(courseSubject, courseNum, professor, "SPRING", year) != 0)
                {
                    // gets the term for that gpa to match the output of the getPastSemesterGPAs function
                    semesterList.add("Spring " + year);
                }
                if(j == 1 && getAvgGPASem(courseSubject, courseNum, professor, "FALL", year) != 0)
                {
                    // Gets the grades for that year during the fall
                    semesterList.add("Fall " + year);
                }
            }
            year++;
        }
        //prints the contents of GPAList to make sure they are correct
        System.out.println("\nThe recorded GPA's for each semester are:");
        for(int i = 0; i < semesterList.size(); i++)
        {
            System.out.println(semesterList.get(i) + "\n");
        }

        return semesterList;
    }

    /**
     * Counts the number of semesters a professor has taught a class for a specific subject
     * and specific course number
     *
     * @param subject
     * @param courseNum
     * @param professor
     * @return
     * @throws SQLException
     */
    public int getNumSemestersTaught(String subject, int courseNum, String professor) throws SQLException
    {
        String query = "SELECT COUNT(DISTINCT Semester_Term, Semester_Year) FROM " +
                "TamuGrades WHERE CourseSubject=\"" + subject + "\" and CourseNum=" +
                courseNum + " AND Professor=\"" + professor +"\"";
        Statement getSemestersTaught = conn.createStatement();
        ResultSet result = getSemestersTaught.executeQuery(query);

        int totalSemestersTaught = 0;
        while(result.next())
        {
            totalSemestersTaught = result.getInt(1);
            System.out.println(professor + " has taught this course a minimum " +
                    "of " + totalSemestersTaught + " times!\nIt is possible he " +
                    "has taught more semesters but this is as many as the database has");
        }

        return totalSemestersTaught;
    }

    /**
     * Gets the raw data from the table and returns it in an arraylist in case I want to
     * display the raw data for the user to view in a non-editable java table
     *
     * @param subject
     * @param courseNum
     * @param professor
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getProfRawData(String subject, int courseNum, String professor) throws SQLException
    {
        String query = "SELECT professor, NumA, NumB, NumC, NumD, NumF, Num_QDrop, " +
                "Avg_GPA, Semester_Term, Semester_Year FROM TamuGrades WHERE " +
                "CourseSubject=\"" + subject + "\" AND CourseNum=" + courseNum + " AND " +
                "professor=\"" + professor + "\" AND Honors=false ORDER BY Semester_Year, Semester_Term DESC";
        Statement getRawData = conn.createStatement();
        ResultSet result = getRawData.executeQuery(query);

        ArrayList<String> data = new ArrayList<String>();
        while(result.next())
        {
            String resultProfessor = result.getString("Professor");
            String NumA = result.getString("NumA");
            String NumB = result.getString("NumB");
            String NumC = result.getString("NumC");
            String NumD = result.getString("NumD");
            String NumF = result.getString("NumF");
            String avgGPA = result.getString("Avg_GPA");
            String Num_QDrop = result.getString("Num_QDrop");
            String semester = result.getString("Semester_Term");
            String year = result.getString("Semester_Year");

            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\n", resultProfessor,
                    NumA, NumB, NumC, NumD, NumF, Num_QDrop, semester, year);

            data.add(resultProfessor);
            data.add(NumA);
            data.add(NumB);
            data.add(NumC);
            data.add(NumD);
            data.add(NumF);
            data.add(Num_QDrop);
            data.add(avgGPA);
            data.add(semester);
            data.add(year);
        }
        return data;
    }

    /**
     * Inserts all of the information given into the database table.
     * This is now unnecessary since all of the insert functionality has been moved to the
     * TamuGradeAnalyzer-Insert project
     *
     * @param Subject
     * @param courseNum
     * @param sectionNum
     * @param avgGPA
     * @param professor
     * @param numA
     * @param numB
     * @param numC
     * @param numD
     * @param numF
     * @param numQdrop
     * @param termSemester
     * @param termYear
     * @param honors
     */
    public void insert(String Subject, int courseNum, int sectionNum, Double avgGPA,
                       String professor, int numA, int numB, int numC, int numD, int numF, int numQdrop,
                       String termSemester, int termYear, boolean honors) // throws java.sql.SQLException
    {
        try
        {

            String query = "INSERT INTO TamuGrades VALUES (\"" + Subject + "\", " +
                    courseNum + ", " + sectionNum + ", " + avgGPA + ", \"" + professor + "\", "
                    + numA + ", " + numB + ", " + numC + ", " + numD + ", " + numF
                    + ", " + numQdrop + ", \"" + termSemester + "\", " + termYear + ", " + honors + ") ";
            System.out.println("Inserting query\n" + query);
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.execute();
        }
        catch(SQLException e)
        {
            // this small block turns the exception into a string to be compared
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();

            if(exceptionMessage.contains("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrity" +
                    "ConstraintViolationException"))
            {
                System.out.println("\n" + e);
                System.out.println("\nThis entry is already in the table\nIGNORING");
            }
            else
            {
                System.out.println("\n" + e);
                System.out.println("Could not create insert statement");
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            // System.out.println("\n" + e);
            System.err.println("Could not Insert into Database because something strange happened");
            e.printStackTrace();
        }
    }

    public void closeDBConn() throws SQLException
    {
        System.out.println("\nClosing Database connection");
        conn.close();
        System.out.println("\nDatabase connection closed");
    }

}
/**What is needed make the database on the laptop CONSTRAINT so that duplicate rows aren't added is:
<BEGIN;

ALTER IGNORE TABLE TamuGrades ADD CONSTRAINT TamuGrades_unique
UNIQUE (CourseSubject, CourseNum, SectionNum, Avg_GPA, Professor,
NumA, Numb, NumC, NumD, NumF, Num_QDrop, Semester_Term, Semester_Year, Honors);>

then use the command <COMMIT>
 */

/** This is for putting the constraint on the Amazon RDS database
BEGIN;

ALTER IGNORE TABLE `TamuData`.`TamuGrades` ADD CONSTRAINT TamuGrades_unique
UNIQUE (CourseSubject, CourseNum, SectionNum, Avg_GPA, Professor,
NumA, Numb, NumC, NumD, NumF, Num_QDrop, Semester_Term, Semester_Year, Honors);
*/


// is the code for listing all of the raw data in case I want to use it in a different file
/** ArrayList<String> rawData = db.getProfRawData("CSCE", 121, "MOORE");
            for(int i = 0; i < rawData.size(); i++)
            {
                if(i % 10 == 0)
                {
                    //System.out.println();
                    System.out.print("\n" + rawData.get(i) + " ");
                }
                else
                    System.out.print(rawData.get(i) + " ");
            } */