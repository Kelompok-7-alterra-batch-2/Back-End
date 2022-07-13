package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.QueueDTO;
import com.hospital.hospitalmanagement.entities.QueueEntity;
import com.hospital.hospitalmanagement.service.QueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/queue")
public class QueueController {
    @Autowired
    QueueServiceImpl queueService;

    @GetMapping
    public ResponseEntity<List<QueueEntity>>getAllQueue(){
        return ResponseEntity.ok().body(this.queueService.getAllQueue());
    }

    @PostMapping
    public ResponseEntity<QueueEntity> createQueue(@RequestBody QueueDTO queueDTO){
        return ResponseEntity.ok().body(this.queueService.createQueue(queueDTO));
    }
}
