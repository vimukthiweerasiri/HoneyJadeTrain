/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class ClassRoom {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Student.getInstance(100).getNum());
        Student.getInstance(100).changeVariable(300);
        System.out.println(Student.getInstance(200).getNum());
        System.out.println(Student.getInstance(100).getNum());
        Student.getInstance(100).changeVariable(500);
        System.out.println(Student.getInstance(100).getNum());
        System.out.println(Student.getInstance(400).getNum());
        System.out.println(Student.getInstance(400).getNum());

    }

}
