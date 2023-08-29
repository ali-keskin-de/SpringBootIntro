package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // optional peki neden yaziyoruz : kod okunurlulugu acisindan yaziyoruz.
            // biz Repository'lerimizi generic bir class olan JpaRepository'da extends ediyoruz
            // ve bu generic class'a parametre olarak Entity classin ismini ve  id'i veriyoruz.
public interface StudentRepository extends JpaRepository <Student, Long>{
    boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade") // JPQL
        //@Query("SELECT s FROM Student s WHERE s.grade=:pGrade") // HQL
        //@Query(value = "SELECT * FROM Student s WHERE s.grade=:pGrade", nativeQuery = true) // native SQL
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id") // StudentDTO st1 = new StudentDTO(Student);
    Optional<StudentDTO> findStudentDtoById(@Param("id") Long id);


}
