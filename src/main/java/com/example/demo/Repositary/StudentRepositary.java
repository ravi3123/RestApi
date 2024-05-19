package com.example.demo.Repositary;

import com.example.demo.Entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositary extends CrudRepository<StudentEntity,Long> {


}
