package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.NotificationType;
import org.javaproteam27.socialnetwork.repository.NotificationTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    
    private final NotificationTypeRepository notificationTypeRepository;
    
    
    public NotificationType findById(int id) {
        return notificationTypeRepository.findById(id);
    }
    
}
