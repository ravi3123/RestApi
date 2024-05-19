package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id", nullable = false, unique = true)
    private Long studentId;

    @Column(unique = true, nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;








}
