package org.techtest.starling.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtest.starling.controller.api.RoundingUpApi;
import org.techtest.starling.model.RoundUpRequest;
import org.techtest.starling.model.RoundUpResponse;
import org.techtest.starling.service.RoundingUpService;

@RestController
@RequestMapping("/api/v1/roundup")
@RequiredArgsConstructor
public class RoundUpController implements RoundingUpApi {
    private final RoundingUpService roundingUpService;

    @PutMapping
    public ResponseEntity<RoundUpResponse> roundUp(@RequestBody RoundUpRequest roundUpRequest) {
        return new ResponseEntity<>(roundingUpService.roundUp(roundUpRequest), HttpStatus.OK);
    }
}
