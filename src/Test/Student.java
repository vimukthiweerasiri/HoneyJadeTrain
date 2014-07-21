/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.util.ArrayList;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class Student {

    public static ArrayList<Student> studentList = new ArrayList<>();

    private Student(int id) {
        ID = id;
        num = Math.random() * 1000;

    }

    public static Student getInstance(int check) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).ID == check) {
                return studentList.get(i);
            }
        }
        Student s = new Student(check);
        studentList.add(s);

        return s;
    }

    public double getNum() {
        return this.num;
    }

    public void changeVariable(int i) {
        num = i;
    }

    private int ID;
    private double num;

}
