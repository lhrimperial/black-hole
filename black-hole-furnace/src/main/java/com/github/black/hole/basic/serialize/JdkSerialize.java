package com.github.black.hole.basic.serialize;

import com.github.black.hole.basic.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class JdkSerialize {

    public static void main(String[] args) throws Exception {
        String objPath = Constants.SERIAL_DIR+"student.txt";
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(objPath));
        Student student = new Student();
        student.setAge(25);
        student.setName("jayWei");
        objectOutputStream.writeObject(student);
        objectOutputStream.flush();
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objPath));
        Student student1 = (Student) objectInputStream.readObject();
        System.out.println("name=" + student1.getName());
    }
}
