package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.QueueDTO;
import com.hospital.hospitalmanagement.entities.QueueEntity;
import com.hospital.hospitalmanagement.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QueueServiceImpl {
    @Autowired
    QueueRepository queueRepository;

    public List<QueueEntity>getAllQueue(){
        return this.queueRepository.findAll();
    }

    public QueueEntity getQueueById(Long id){
        Optional<QueueEntity>optionalQueue = this.queueRepository.findById(id);

        if (optionalQueue.isEmpty()){
            return null;
        }
        return optionalQueue.get();
    }

    public QueueEntity createQueue(QueueDTO queueDTO){
        QueueEntity queue = QueueEntity.builder()
                .queueNumber(queueDTO.getQueueNumber())
                .createdAt(LocalDateTime.now())
                .build();

        return this.queueRepository.save(queue);
    }
}
