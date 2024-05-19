package com.example.demo.Controller;

import com.example.demo.DTO.ErrorResponseDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.DTO.StudentsDTO;
import com.example.demo.Entity.StudentEntity;
import com.example.demo.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Validated
public class StudentController {

    @Autowired
    StudentService studentService;

    @Operation( summary = "Add New Unique Student to System" )
    @ApiResponses( value = {
            @ApiResponse(responseCode ="201", description = "Student added Successfully.",
                    content = { @Content( mediaType ="application/json",
                    schema = @Schema( implementation = StudentEntity.class)) }),
            @ApiResponse(responseCode = "400",description = "Bad Request."),
            @ApiResponse(responseCode = "500",description = "Internal server Error",
                    content = { @Content( mediaType ="application/json",
                    schema = @Schema( implementation = ErrorResponseDTO.class)) }) })
    @PostMapping("/AddStudent")
    public ResponseEntity AddStudent(@Valid @RequestBody StudentDTO studentDto){
        return studentService.AddNewStudent(studentDto);
    }

    @Operation( summary = "Get All Students" )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",description = "Fetch All Students",
                    content = { @Content( mediaType ="application/json",
                    schema = @Schema( implementation = StudentsDTO.class)) }),
            @ApiResponse(responseCode = "400",description = "Bad Request."),
            @ApiResponse(responseCode = "404",description = "Not Found."),
            @ApiResponse(responseCode = "500",description = "Internal server Occured",
                    content = { @Content( mediaType ="application/json",
                    schema = @Schema( implementation = ErrorResponseDTO.class)) }) })
    @GetMapping("/Students")
    public StudentsDTO FetchStudents(){
        return studentService.FetchStudents();
    }

    @Operation( summary = "Get Student ById" )
    @ApiResponses( value = {
            @ApiResponse(responseCode ="200", description = "Fetch Student Details",
                    content = { @Content( mediaType ="application/json",
                    schema = @Schema( implementation = ResponseEntity.class)) }),
            @ApiResponse(responseCode = "400",description = "Bad Request."),
            @ApiResponse(responseCode = "404",description = "Student Not Found.",
                    content = { @Content( mediaType ="application/json",
                     schema = @Schema( implementation = ErrorResponseDTO.class)) }),
            @ApiResponse(responseCode = "500",description = "Internal server Error",
                    content = { @Content( mediaType ="application/json",
                            schema = @Schema( implementation = ErrorResponseDTO.class)) }) })
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @GetMapping("/Student/{id}")
    public ResponseEntity FetchStudent(@PathVariable("id") Long studentId) {
           return studentService.FetchStudent(studentId);
    }

    @Operation( summary = "Delete Student ById" )
    @ApiResponses( value = {
            @ApiResponse(responseCode ="204", description = "Student Deleted Successful"),
            @ApiResponse(responseCode = "400",description = "Bad Request."),
            @ApiResponse(responseCode = "404",description = "Student Not Found.",
                    content = { @Content( mediaType ="application/json",
                            schema = @Schema( implementation = ErrorResponseDTO.class)) }),
            @ApiResponse(responseCode = "500",description = "Internal server Occured",
                    content = { @Content( mediaType ="application/json",
                            schema = @Schema( implementation = ErrorResponseDTO.class)) }) })
    @DeleteMapping("/Student/{id}")
    public ResponseEntity DeleteStudent(@PathVariable("id") Long studentId){
        return studentService.DeleteStudent(studentId);
    }

    @Operation( summary = "Update Student ById" )
    @ApiResponses( value = {
            @ApiResponse(responseCode ="200", description = "Student Updated Successfully"),
            @ApiResponse(responseCode = "400",description = "Bad Request."),
            @ApiResponse(responseCode = "404",description = "Student Not Found.",
                    content = { @Content( mediaType ="application/json",
                            schema = @Schema( implementation = ErrorResponseDTO.class)) }),
            @ApiResponse(responseCode = "500",description = "Internal server Occured",
                    content = { @Content( mediaType ="application/json",
                            schema = @Schema( implementation = ErrorResponseDTO.class)) }) })
    @PutMapping("/Student/{id}")
    public ResponseEntity UpdateStudent(@PathVariable("id") Long studentId, @RequestBody StudentDTO studentDto){
        return studentService.UpdateStudent(studentId, studentDto);
    }

/*@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
          errors.put(((FieldError) error).getField(),error.getDefaultMessage());
        });
        return errors;
    }*/
}
