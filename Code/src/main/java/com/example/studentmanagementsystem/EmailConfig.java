
package com.example.studentmanagementsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailConfig {

    public static Properties getEmailProperties ()
    {
        Properties prop = new Properties();
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\Mabdo\\Desktop\\Projects\\Student Management System\\Code\\src\\main\\resources\\com\\example\\studentmanagementsystem\\mail.properties");
            prop.load(input);
        }catch ( IOException e) {
            System.out.println("Error occurred :" + e.getMessage());
        }
        return prop;
    }

    public static String getMail()
    {
        return getEmailProperties().getProperty("email.username");
    }

    public static String getPass()
    {
        return getEmailProperties().getProperty("email.password");
    }
}
