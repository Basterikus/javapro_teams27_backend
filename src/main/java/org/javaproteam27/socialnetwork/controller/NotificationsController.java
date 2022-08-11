package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseDto;
import org.javaproteam27.socialnetwork.service.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationsService notificationsService;
    
    @GetMapping
    public ResponseEntity<ListResponseDto<NotificationBaseDto>> getNotifications(@RequestParam("email") String email,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "itemPerPage", required = false, defaultValue = "10") int itemPerPage) {
        
        ListResponseDto<NotificationBaseDto> notifications = notificationsService.getNotifications(email, offset, itemPerPage);
        return ResponseEntity.ok(notifications);
    }
    
    @PutMapping
    public ResponseEntity<ListResponseDto<NotificationBaseDto>> markAsReadNotification(@RequestParam("email") String email,
            @RequestParam(value = "id", required = false, defaultValue = "0") int id,
            @RequestParam(value = "all", required = false, defaultValue = "false") boolean all) {
    
        ListResponseDto<NotificationBaseDto> notifications = notificationsService.markAsReadNotification(email, id, all);
        return ResponseEntity.ok(notifications);
    }

}
