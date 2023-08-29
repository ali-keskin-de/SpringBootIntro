package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
// Normalde bir class'tan bean olusturulmasi icin @Component ann. olmali bunuda @Service saglamaktadir eger bu interface gidersek bunu görebilirz.
@Service // Bu annotation'u koyduktan sonra bu class'in artik springframework biliyor ki bu class bizim bussinuss logic'in yazilacagi katman.
         // kontrollarin yapildigi controller tarafindan gelen request'lerin karsilandigi ve burdan da Repository katmanina gidecek requestlerin yönetildigi class.
@RequiredArgsConstructor
public class StudentService {
    // neden field injection yapmadik ? -> cünkü disardan biz parametresi private olan field'a ulasamayiz oysaki constructor(public'tir) ulasilabilir.
    //@RequiredArgsConstructor Annotation'i sayesinde burda biz görmesekte final field'lardan bir constructor  olusturur.
    // Cont. injection'da  @Autowired annotation'u optional.
    // burda StudentRepository  classini constructor injection ile injectte ettik.
    private final StudentRepository studentRepository;


    // Not: getAll() *********************************
    public List<Student> getAll() {

        return studentRepository.findAll(); // SELECT * FROM student ;
    }

    // Not: createStudent()************************************
    public void createStudent(Student student) {

        // !!! email conflict kontrolu
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Email is already exist!");
        }
        studentRepository.save(student);
    }

    // Not: getByIdWithRequestParam()**********************************************
    public Student findStudent(Long id) {

        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id: "+ id));
    }

    // Not: delete() *********************************************************
    public void deleteStudent(Long id) {

        Student student = findStudent(id);

        studentRepository.delete(student);
        //studentRepository.deleteById(id);

    }

    // Not:update()**************************************************************
    public void updateStudent(Long id, StudentDTO studentDTO) {

        // !!! id li ogrenci var mi ?
        Student student = findStudent(id);
        // !!! email unique mi ???

        /*
                1) mevcut email: mrc , yeni : mrc   --> TRUE
                2) mevcut email : mrc, yeni : ahmt( DB de zaten var) --> FALSE
                3) mevcut email : mrc, yeni : mhmt( DB de yok) --> TRUE
         */
        boolean emailExist = studentRepository.existsByEmail(studentDTO.getEmail());
        if( emailExist && !studentDTO.getEmail().equals(student.getEmail())  ){
            throw new ConflictException("Email is already exist!") ;
        }

        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);
    }

    // Not: pageable *************************************************************
    public Page<Student> getAllWithPage(Pageable pageable) {

        return studentRepository.findAll(pageable);
    }

    // Not: JPQL **********************************************************************
    public List<Student> findAllEqualsGrade(Integer grade) {

        return studentRepository.findAllEqualsGrade(grade);
    }

    // Not: Db den direk DTO olarak verileri alabilir miyim
    public StudentDTO findStudentDtoById(Long id) {

        return studentRepository.findStudentDtoById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id : " + id));
    }

}
