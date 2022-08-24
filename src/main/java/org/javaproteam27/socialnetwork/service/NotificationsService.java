package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseRs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationsService {
    
    private final NotificationService notificationService;
    private final NotificationTypeService notificationTypeService;
    private final PersonService personService;
    
    
    public ListResponseRs<NotificationBaseRs> getNotifications(String token, int offset, int itemPerPage) {

        return new ListResponseRs<>("", offset, itemPerPage, List.of(notificationMock()));
    }
    
    public ListResponseRs<NotificationBaseRs> markAsReadNotification(String token, int id, boolean all) {
        
        int itemPerPage = all ? 20 : 1;
        return new ListResponseRs<>("", 0, itemPerPage, List.of(notificationMock()));
    }
    
    private NotificationBaseRs notificationMock() {
    
        NotificationBaseRs notificationBaseRs = new NotificationBaseRs();
    
        notificationBaseRs.setId(1);
        notificationBaseRs.setTypeId(1);
        notificationBaseRs.setSentTime(System.currentTimeMillis());
        notificationBaseRs.setEntityId(1);
        notificationBaseRs.setInfo("Какой-то текст");
        
        return notificationBaseRs;
    }
}
