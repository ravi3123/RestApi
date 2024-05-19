package com.example.demo.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Valid
public class StudentDTO {
    @Valid

    @NotNull(message = "firstName is mandatory")
    @NotEmpty(message = "firstName is mandatory")
    @NotBlank(message = "firstName is mandatory")
    @Size(min = 1, max = 255, message = "firstName length should be between 1 and 255")
    private String firstName;
    private String lastName;

}
