package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.QueueDTO;
import com.hospital.hospitalmanagement.entities.QueueEntity;
import com.hospital.hospitalmanagement.service.QueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/queue")
public class QueueController {
    @Autowired
    QueueServiceImpl queueService;

    @GetMapping
    public List<QueueEntity>getAllQueue(){
        return this.queueService.getAllQueue();
    }

    @PostMapping
    public QueueEntity createQueue(@RequestBody QueueDTO queueDTO){
        return this.queueService.createQueue(queueDTO);
    }
}
