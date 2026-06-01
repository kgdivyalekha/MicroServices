package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Schema(
        name= "Error Response",
        description = "Schema to hold error response information of REST APIs"
)
@Data@AllArgsConstructor
public class ErrorResponseDTO {
    @Schema(
            description = "API invoked by the client"
    )
    private String apiPath;
    @Schema(
            description = "Error code"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "Error message of the error"
    )
    private String errorMessage;
    @Schema(
            description = "Time the error occurred"
    )
    private LocalDateTime errorTime;
}
