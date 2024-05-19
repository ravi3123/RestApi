package com.example.demo.DTO;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class StudentsDTO {
    private List<StudentDTO> students;
}
