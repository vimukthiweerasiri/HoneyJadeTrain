/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.*;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class TestDBConnection {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/testhoneyjade", "root", "");
        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM stations");
        while (result.next()) {
            System.out.print(result.getString(1));
            System.out.println(" " + result.getString(2));
        }
    }
}
