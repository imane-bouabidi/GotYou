package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.dtos.request.CreateRequestDto;
import com.wora.gotYou.dtos.request.UpdateRequestDto;
import com.wora.gotYou.services.interfaces.RequestServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestServiceInter requestService;

    @PostMapping
    public ResponseEntity<RequestDto> createRequest(@RequestBody CreateRequestDto dto) {
        RequestDto savedRequest = requestService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestDto> updateRequest(
            @PathVariable Long id,
            @RequestBody UpdateRequestDto dto
    ) {
        RequestDto updatedRequest = requestService.update(dto, id);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.findAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id) {
        RequestDto request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/student")
    public ResponseEntity<List<RequestDto>> getRequestsByStudentId() {
        List<RequestDto> requests = requestService.getRequestsByStudentId();
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}