package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseDto;
import org.javaproteam27.socialnetwork.service.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationsService notificationsService;
    
    @GetMapping
    public ResponseEntity<ListResponseDtoRs<NotificationBaseDto>> getNotifications(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage) {
        
        ListResponseDtoRs<NotificationBaseDto> notifications = notificationsService.getNotifications(token, offset, itemPerPage);
        return ResponseEntity.ok(notifications);
    }
    
    @PutMapping
    public ResponseEntity<ListResponseDtoRs<NotificationBaseDto>> markAsReadNotification(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "id", defaultValue = "0") int id,
            @RequestParam(value = "all", defaultValue = "false") boolean all) {
        
        ListResponseDtoRs<NotificationBaseDto> notifications = notificationsService.markAsReadNotification(token, id, all);
        return ResponseEntity.ok(notifications);
    }
    
}
