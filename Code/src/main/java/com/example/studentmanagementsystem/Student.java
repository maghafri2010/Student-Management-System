package com.example.studentmanagementsystem;

import javafx.beans.property.SimpleStringProperty;


public class Student {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;

    public Student(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }
}

