package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
@Schema(
        name= "Response",
        description = "Schema to hold response information of REST APIs"
)
public class ResponseDTO {
    @Schema(
            description = "Status Code in the response"
    )
    String statusCode;
    @Schema(
            description = "Status Message in the response"
    )
    String statusMsg;
}
