package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Notification;
import org.javaproteam27.socialnetwork.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    
    public Notification findById(int id) {
        return notificationRepository.findById(id);
    }
    
    public List<Notification> findByPersonId(int personId) {
        return notificationRepository.findByPersonId(personId);
    }
    
}
