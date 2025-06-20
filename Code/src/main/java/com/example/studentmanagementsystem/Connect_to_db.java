
package com.example.studentmanagementsystem;

import javafx.scene.control.DatePicker;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class Connect_to_db {

    public String first_name,last_name,birth,department,email,phone;

    public Connect_to_db(String first_name, String last_name, String birth, String department, String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth = birth;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public static Connection getCon() throws IOException, SQLException {
        Properties prop = new Properties();
        FileInputStream input;

        {
            try {
                input = new FileInputStream("C:\\Users\\lenovo\\OneDrive\\Bureau\\MyProjects\\Projects\\Student Management System\\Code\\src\\main\\resources\\com\\example\\studentmanagementsystem\\db.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        prop.load(input);

        String url          = prop.getProperty("db.url");
        String user         = prop.getProperty("db.username");
        String password     = prop.getProperty("db.password");
        Connection con      = DriverManager.getConnection(url, user, password);
        System.out.println("Connection has been established successfully!");

        return (con);
    }

    public static int accRegister(String username, String first_name, String last_name, String email, String password, String rePassword){

        // Generate queries
        String query = "INSERT INTO users(Username, password, first_name, last_name, email) VALUES (?,?,?,?,?) ";
        String query1 = "SELECT username FROM users WHERE username = ?";

        //establish the connection Generate statements
        try (Connection con = getCon()) {
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);



            // Check if the username already exists
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();

            // Check if a result has found
            if (rs.next())
                return 1;
            if (!password.equals(rePassword))
                return 2;
            ArrayList<String> check = new ArrayList<>();
            check.add(0, username);
            check.add(1, first_name);
            check.add(2, last_name);
            check.add(3, email);
            check.add(4, password);
            check.add(5, rePassword);

            for (String str : check)
            {
                if (str == null || str.isEmpty())
                    return 3;
            }

            // set the info of the user into the DB
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, first_name);
            ps.setString(4, last_name);
            ps.setString(5, email);

            ps.executeUpdate();
            return 0;
        }catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errors During registration", e);
        }

    }

    public static boolean checkEmail(String email) throws SQLException, IOException {
        Connection con = getCon();

        PreparedStatement ps = con.prepareStatement("SELECT email FROM users WHERE email = ?");
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return true;
        con.close();
        rs.close();
        return false;
    }

    public static void change_password(String pass, String email) throws SQLException, IOException {
        Connection con = getCon();
        String query = "UPDATE users SET password = ? WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, pass);
        ps.setString(2, email);
        ps.executeUpdate();
        con.close();
    }

    public static boolean check_student(String fName, String lName, LocalDate birth) throws SQLException, IOException {
        Connection con = getCon();
        String query = "SELECT first_name, last_name, birth FROM students WHERE first_name=? AND last_name=? AND birth= ?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,fName);
        ps.setString(2,lName);
        ps.setDate(3, Date.valueOf(birth));


        ResultSet rs = ps.executeQuery();

        if (rs.next())
            return false;

        return true;


    }

    public static void addToDB(String fName, String lName, LocalDate birth, String bp, String dept, String phone, String email) throws SQLException, IOException {
        Connection con = getCon();
        String query = "INSERT INTO students(first_name, last_name, birth, birth_place, department, phone, email) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,fName);
        ps.setString(2,lName);
        ps.setDate(3, Date.valueOf(birth));
        ps.setString(4,bp);
        ps.setString(5,dept);
        ps.setString(6,phone);
        ps.setString(7,email);
        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public static Connect_to_db check_by_ID(int id) throws SQLException, IOException {
        Connection con = getCon();
        String query = "SELECT first_name, last_name, birth, department, email, phone FROM students WHERE id = ? ";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            return new Connect_to_db(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("birth"),
                    rs.getString("department"),
                    rs.getString("email"),
                    rs.getString("phone")
            );

        }
        else
            return null;
    }

    public static HashMap<String, String> get_students(int id) throws SQLException, IOException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        HashMap<String, String> students = new HashMap<>();

        try {
            con = getCon();
            st = con.createStatement();

            String query = null;

            if (id == 2205){
                query = "SELECT student_first_name, student_last_name FROM 2205";
            }else if (id == 2306){
                query = "SELECT student_first_name, student_last_name FROM 2306";
            }else if (id == 1028){
                query = "SELECT student_first_name, student_last_name FROM 1028";
            }else if (id == 2245){
                query = "SELECT student_first_name, student_last_name FROM 2245";
            }else
                throw new IllegalAccessError("Invalid course ID");

            rs = st.executeQuery(query);


            if (!rs.next()) {
                while (rs.next())
                    students.put(rs.getString("student_first_name"),rs.getString("student_last_name"));
            }

        }finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); }}
            if (st != null) { try { st.close(); } catch (Exception e) { e.printStackTrace(); }}
            if (con != null) { try { con.close(); } catch (Exception e) { e.printStackTrace(); }}
        }

        return students;
    }
}


