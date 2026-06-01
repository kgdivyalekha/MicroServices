package com.bank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name="Error Response",
        description = "Schema to hold error response information"
)
public class ErrorResponseDTO {
    @Schema(
            description = "API path invoked by the client"
    )
    private String apiPath;
    @Schema(
            description = "Error code of the response"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "Error message of the response"
    )
    private String errorMessage;
    @Schema(
            description = "Time the error occurred"
    )
    private LocalDateTime errorTime;
}
