package com.example.demo.Service;

import com.example.demo.DTO.ErrorResponseDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.DTO.StudentsDTO;
import com.example.demo.Entity.StudentEntity;
import com.example.demo.ExceptionHandlers.ValidationResult;
import com.example.demo.Repositary.StudentRepositary;
import jakarta.validation.*;
import org.apache.catalina.connector.Response;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StudentService {
    public StudentService() {
    }

    private Validator validator;
    private StudentsDTO studentsDTO;

    public StudentService(StudentsDTO studentsDTO) {
        this.studentsDTO = studentsDTO;
    }

    public StudentService(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    StudentRepositary studentRepositary;

    ResponseEntity responseEntity;

    StringBuilder errorList= new StringBuilder();

    public ResponseEntity AddNewStudent(StudentDTO studentDto) {
        StudentEntity studentEntity= new StudentEntity();
        try {

                validateInputWithInjectedValidator(studentDto);

                studentEntity.setFirstName(studentDto.getFirstName());
                studentEntity.setLastName(studentDto.getLastName());
                studentEntity=studentRepositary.save(studentEntity);
                return responseEntity.ok().body(studentEntity);

        } catch (Exception e) {
            return responseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage(),500));
        }
    }

    public StudentsDTO FetchStudents() {
        StudentsDTO students=new StudentsDTO();
        Iterable<StudentEntity> studentsEntity = studentRepositary.findAll();
        Arrays.stream(new Iterable[]{studentsEntity}).forEach(a->students.setStudents((List<StudentDTO>) a));
        return students;
    }

    /*public Optional<StudentEntity> FetchStudent(Long studentId) {
        Optional<StudentEntity> students=studentRepositary.findById(studentId);
        return studentRepositary.findById(studentId);
    }*/

    public ResponseEntity FetchStudent(Long studentId) {
        Optional<StudentEntity> students;
        try {
            students=studentRepositary.findById(studentId);
            if (! students.isEmpty() ){
                return  responseEntity.ok().body(students.get());
            }else {
                return  responseEntity.status (404).body(new ErrorResponseDTO("Student Not Found.", 404));
            }
        } catch (Exception e) {
            return responseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponseDTO(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }


    }
    public ResponseEntity DeleteStudent(Long studentId) {
        Optional<StudentEntity> studentDetails = studentRepositary.findById(studentId);
        try {
            if(!studentDetails.isEmpty()){
                studentRepositary.deleteById(studentId);
                return responseEntity.ok("Student Deleted Successfully.");
            }else{
                return responseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponseDTO("Student not Found.",404));
            }
        } catch (Exception e) {
            return responseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponseDTO(e.getMessage(),500));
        }
    }

    public ResponseEntity UpdateStudent(Long studentId, StudentDTO studentDto) {
        StudentEntity studentEntity= new StudentEntity();

        try {
            Optional<StudentEntity> studentDetails = studentRepositary.findById(studentId);

            if(!studentDetails.isEmpty()){
                studentEntity=studentDetails.get();
                studentEntity.setFirstName(studentDto.getFirstName());
                studentEntity.setLastName(studentDto.getLastName());

                return responseEntity.ok().body(studentRepositary.save(studentEntity));
            }else{
                return responseEntity.status(404).body(new ErrorResponseDTO("Student not found.",404));
            }
        } catch (Exception e) {
                return responseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage(),500));
        }
    }

    public void validateInputWithInjectedValidator(StudentDTO studentDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
