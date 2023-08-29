package com.tpe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpe.domain.Student;

import javax.persistence.*;

@Entity
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @JsonProperty("bookName")// artik json dosyasinda bu field icin bookName yazacagim
    private String name;

   //JoinColumn olusturulan tarafin tablosunda bir stütun olusturulur. name="student_id" attriputu ile sütuna isim veririz

   @ManyToOne
   @JoinColumn(name="student_id")
   @JsonIgnore // infinit loop'u kirmak icin kullanilir eger yazilmazsa nesneler arasinda gider gelir StackOverOf exception'ni alinir.
   private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
