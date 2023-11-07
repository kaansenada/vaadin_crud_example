package org.example.entities;

import javax.persistence.*;
import java.util.Scanner;

@Entity
@Table(name="students")
public class Student {
    //variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="fname")
    private String fName;
    @Column(name="lname")
    private String lName;
    @Column(name="book")
    private String bookName;

    //constructors
    public Student() {
    }

    public Student(long id, String fName, String lName, String bookName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.bookName = bookName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }



}
