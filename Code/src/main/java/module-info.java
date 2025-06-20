module com.example.studentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.mail;


    opens com.example.studentmanagementsystem to javafx.fxml;
    exports com.example.studentmanagementsystem;
}