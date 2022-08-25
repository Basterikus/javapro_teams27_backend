package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationsService {
    
    private final NotificationService notificationService;
    private final NotificationTypeService notificationTypeService;
    private final PersonService personService;
    
    
    public ListResponseRs<NotificationBaseDto> getNotifications(String token, int offset, int itemPerPage) {

        return new ListResponseRs<>("", offset, itemPerPage, List.of(notificationMock()));
    }
    
    public ListResponseRs<NotificationBaseDto> markAsReadNotification(String token, int id, boolean all) {
        
        int itemPerPage = all ? 20 : 1;
        return new ListResponseRs<>("", 0, itemPerPage, List.of(notificationMock()));
    }
    
    private NotificationBaseDto notificationMock() {
    
        NotificationBaseDto notificationBaseDto = new NotificationBaseDto();
    
        notificationBaseDto.setId(1);
        notificationBaseDto.setTypeId(1);
        notificationBaseDto.setSentTime(System.currentTimeMillis());
        notificationBaseDto.setEntityId(1);
        notificationBaseDto.setInfo("Какой-то текст");
        
        return notificationBaseDto;
    }
}
