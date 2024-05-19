package com.example.demo.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ErrorResponseDTO {
    private String errorMessage;
    private int errorCode;

}
