
package com.example.studentmanagementsystem;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Controller {

    // Login scene Info

    @FXML
    private PasswordField passField;
    @FXML
    private TextField userField;
    @FXML
    private Button login;
    @FXML
    private Label invalid_Label;
    @FXML
    private Label user_name;
    private static Connection connection;

    // Register Scene info
    @FXML
    private TextField R_username;
    @FXML
    private TextField R_firstName;
    @FXML
    private TextField R_lastName;
    @FXML
    private TextField R_email;
    @FXML
    private PasswordField R_password;
    @FXML
    private PasswordField R_rePassword;
    @FXML
    private Label label_register;

    // Reset pass scene info
    @FXML
    private TextField reset_text;
    @FXML
    private Label Reset_l1;
    @FXML
    private Label Reset_l2;
    @FXML
    private Label Reset_l3;
    @FXML
    private Label Reset_l4;
    @FXML
    private Button reset_button;
    @FXML
    private Button reset_submit;
    @FXML
    private GridPane new_pass;
    @FXML
    private TextField new_password;
    @FXML
    private TextField re_new_pass;
    static String ID ;
    static String mail;

    // Dashboard info
    @FXML
    private GridPane addPane;
    @FXML
    private Label condition;
    @FXML
    private TextField firstName_addStudent;

    @FXML
    private TextField lastName_addStudent;
    @FXML
    private DatePicker dob_addStudent;
    @FXML
    private TextField birthPlace_addStudent;
    @FXML
    private TextField dept_addStudent;

    @FXML
    private TextField phone_addStudent;
    @FXML
    private TextField email_addStudent;
    @FXML
    private Label top_user;

    // check student
    @FXML
    private GridPane check_grid;
    @FXML
    private Label check_label_firstName;
    @FXML
    private Label check_label_lastName;
    @FXML
    private Label check_label_dateOfBirth;
    @FXML
    private Label check_label_dept;
    @FXML
    private Label check_label_email;
    @FXML
    private Label check_label_phone;
    @FXML
    private Label check_label_condition;
    @FXML
    private TextField check_text_id;
    @FXML
    private Label check_label_stdID;
    @FXML
    private HBox check_hbox;

    @FXML
    private TableView<Student> Student_lists = new TableView<>();
    private ObservableList<Student> std;
    @FXML
    private TableColumn<Student, String> list_first_name = new TableColumn<>("First Name");

    @FXML
    private TableColumn<Student, String> list_last_name = new TableColumn<>("Last Name");



    @FXML
    private MenuButton button_list;
    @FXML
    private Label label_list;

    @FXML
    private MenuItem cs2205 = new MenuItem("CS2205");

    @FXML
    private MenuItem cs2306 =  new MenuItem("CS2306"); ;
    @FXML
    private MenuItem math1028 = new MenuItem("Math1028");





    // Login scene functions
    public boolean isValid(String username, String password) throws SQLException, IOException {

        connection= Connect_to_db.getCon();
        String query = "SELECT username, password FROM users WHERE username = ? AND password = ?";

        Statement st = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement(query);
        st.execute("USE school_db");
        ps.setString(1,username);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            return true;
        else
            return false;
    }

    @FXML
    void back_to_login(ActionEvent event) throws IOException {
        MainApp.showLoginScene();
    }

    @FXML
    public void login(ActionEvent event) throws SQLException, IOException {

        String user_input = userField.getText();
        String pass_input = passField.getText();


       if (isValid(user_input,pass_input))
       {
           MainApp.dashboard();
           System.out.println("Successful Login!");
       }
        else {
           invalid_Label.setText("Invalid username or password!");
        }
    }

    @FXML
    void goToRegister(ActionEvent event) throws IOException {
        MainApp.register();
    }

    @FXML
    void forgot_password(ActionEvent event) throws IOException {
        MainApp.resetPassword();
    }

    void clear()
    {
        R_username.clear();
        R_firstName.clear();
        R_lastName.clear();
        R_email.clear();
        R_password.clear();
        R_rePassword.clear();
    }

    // Register scene functions
    @FXML
    void sign_up(ActionEvent event) throws SQLException, IOException {


        String username     = R_username.getText();
        String first_name   = R_firstName.getText();
        String last_name    = R_lastName.getText();
        String email        = R_email.getText();
        String password     = R_password.getText();
        String re_password  = R_rePassword.getText();

        int result = Connect_to_db.accRegister(username,first_name,last_name,email,password,re_password);
        if (result == 0) {
            label_register.setText("Registration successful! Welcome aboard!");
            clear();
        }
        else if (result == 1) {
            label_register.setText("Already registered!!");
            clear();
        }
        else if (result == 2)
            label_register.setText("The passwords are not compatible, Try again !!");
        else if (result == 3)
            label_register.setText("Please fill in all fields!!");


    }


    // Reset password scene functions
    @FXML
    void submit_reset(ActionEvent event) throws SQLException, IOException {
        String email = reset_text.getText();
        UUID id = UUID.randomUUID();
        if (Connect_to_db.checkEmail(email) && !email.isEmpty())
        {
            reset_text.clear();
            Reset_l1.setVisible(true);
            Reset_l2.setVisible(true);
            Reset_l3.setText("Enter the code :");
            reset_button.setVisible(true);
            reset_submit.setVisible(false);
            Mailing.sendEmail(email,"Recover the password", "this is your code : " + id);
            ID = id.toString();
            mail = email;


        }else
            Reset_l4.setVisible(true);
            Reset_l4.setText("Invalid email, Try again!!");
    }

    @FXML
    void valid_reset(ActionEvent event) throws IOException {

        String id = reset_text.getText();
        if (id.equals(ID))
        {
            Reset_l1.setVisible(false);
            Reset_l2.setVisible(false);
            Reset_l3.setVisible(false);
            Reset_l4.setVisible(false);
            reset_text.setVisible(false);
            reset_button.setVisible(false);
            new_pass.setVisible(true);

        }else
            Reset_l4.setText("Invalid Code!!");
    }

    @FXML
    void valid_code(ActionEvent event) throws IOException, SQLException {
        String pass = new_password.getText();
        String re_pass = re_new_pass.getText();

        if (pass.equals(re_pass) && !pass.equals(null))
        {
            Connect_to_db.change_password(pass, mail);
            MainApp.showLoginScene();
        }
        else
        {
            Reset_l4.setVisible(true);
            Reset_l4.setText("The passwords you entered don't match!");
        }

    }

    // Dashboard scene functions
    @FXML
    void log_out(ActionEvent event) throws IOException, SQLException {
        MainApp.showLoginScene();
        connection.close();
    }

    void clear_scenes()
    {
        Student_lists.setVisible(false);
        addPane.setVisible(false);

        check_grid.setVisible(false);
        check_hbox.setVisible(false);
        check_text_id.clear();

        Student_lists.setVisible(false);
        label_list.setVisible(false);
        button_list.setVisible(false);
    }

    @FXML
    void add_student(ActionEvent event) throws IOException {
        clear_scenes();
        addPane.setVisible(true);

    }
    void clearNode()
    {
        firstName_addStudent.clear();
        lastName_addStudent.clear();
        dob_addStudent.cancelEdit();
        birthPlace_addStudent.clear();
        dept_addStudent.clear();
        phone_addStudent.clear();
        email_addStudent.clear();
    }
    @FXML
    void submit(ActionEvent event) throws SQLException, IOException {
        String fName = firstName_addStudent.getText();
        String lName = lastName_addStudent.getText();
        LocalDate dob = dob_addStudent.getValue();
        String bp = birthPlace_addStudent.getText();
        String dept = dept_addStudent.getText();
        String phone = phone_addStudent.getText();
        String email = email_addStudent.getText();

        if (Connect_to_db.check_student(fName,lName,dob))
        {
            condition.setVisible(true);
            condition.setText("Student has been added successfully!!");
            Connect_to_db.addToDB(fName,lName,dob,bp,dept,phone,email);
            clearNode();
        }else
            condition.setText("The student already exists");
    }

    @FXML
    void check_student(ActionEvent event){
        clear_scenes();
        check_hbox.setVisible(true);
        check_label_stdID.setText("");
    }
    void check_clear()
    {
        check_label_stdID.setVisible(false);
        check_label_firstName.setVisible(false);
        check_label_lastName.setVisible(false);
        check_label_dateOfBirth.setVisible(false);
        check_label_dept.setVisible(false);
        check_label_phone.setVisible(false);
        check_label_email.setVisible(false);
    }

    @FXML
    void check_enter(ActionEvent event) throws SQLException, IOException {
        String fsnm = "";
        String lsnm = "";
        String birth = "";
        String dept = "";
        String email = "";
        String phone = "";
        int id = Integer.parseInt(check_text_id.getText());
        Connect_to_db student = Connect_to_db.check_by_ID(id);
        if (student != null) {
            check_grid.setVisible(true);
            check_label_stdID.setText(String.valueOf(id));
            check_label_firstName.setText(student.first_name);
            check_label_lastName.setText(student.last_name);
            check_label_dateOfBirth.setText(student.birth);
            check_label_dept.setText(student.department);
            check_label_phone.setText(student.phone);
            check_label_email.setText(student.email);
        } else {
            check_label_condition.setText("Invalid ID!");
            check_clear();
        }
    }


    @FXML
    void view_students(ActionEvent event)
    {
        clear_scenes();
        Student_lists.setVisible(true);
        label_list.setVisible(true);
        button_list.setVisible(true);

    }


    @FXML
    public void initialize(){
        std = FXCollections.observableArrayList();
        Student_lists.setItems(std);

        list_first_name.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        list_last_name.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        cs2205.setOnAction(event -> {
            try {
                ft_cs2205(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        cs2306.setOnAction(event -> {
            try {
                ft_Cs2306(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void ft_cs2205(ActionEvent event) throws SQLException, IOException {
        std.clear();
        HashMap<String, String> students = Connect_to_db.get_students(2205);
        for (Map.Entry<String, String> entry : students.entrySet()) {
            std.add(new Student(entry.getKey(), entry.getValue()));
        }




    }
    private void ft_Cs2306(ActionEvent event) throws SQLException, IOException {
        std.clear();
        HashMap<String, String> students = Connect_to_db.get_students(2306);
        for (Map.Entry<String, String> entry : students.entrySet()) {
            std.add(new Student(entry.getKey(), entry.getValue()));
        }
    }



}