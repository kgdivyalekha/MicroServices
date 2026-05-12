package com.bank.cards.controller;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsDTO;
import com.bank.cards.dto.ErrorResponseDTO;
import com.bank.cards.dto.ResponseDTO;
import com.bank.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name="CRUD REST APIs for Bank Cards Management",
        description = "CRUD REST API in Bank"
)
@RestController
@AllArgsConstructor
@Validated
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {
    private ICardsService iCardsService;

    @Operation(
            summary = "Create Card in REST API",
            description = "REST API to create a new card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCard(@Valid @RequestParam @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Card in REST API",
            description = "REST API to fetch card details using mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardsDTO> fetchCardDetails(@RequestParam @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        CardsDTO cardsDTO=iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDTO);
    }
    @Operation(
            summary = "Update Card in REST API",
            description = "REST API to update an existing card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCardDetails(@Valid @RequestBody CardsDTO cardsDTO)
    {
        boolean isUpdated= iCardsService.updateCard(cardsDTO);
        if(isUpdated)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
    }
    @Operation(
            summary = "Update Card in REST API",
            description = "REST API to update an existing card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCardDetails(@RequestParam @Pattern(regexp="($|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        boolean isDeleted= iCardsService.deleteCard(mobileNumber);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
    }
}
