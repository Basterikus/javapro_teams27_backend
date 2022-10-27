package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.MessageRq;
import org.javaproteam27.socialnetwork.model.dto.response.DialogUserShortListDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.service.DialogsService;
import org.javaproteam27.socialnetwork.service.PersonService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DialogsWebSocketController {

    private final DialogsService dialogsService;

    private final PersonService personService;

    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/dialogs/start_typing")
    public void startTyping(@Header("dialog_id") Integer dialogId, @Payload DialogUserShortListDto userIds) {

        Map<String, Object> header = new HashMap<>();
        header.put("type", "start_typing");
        messagingTemplate.convertAndSendToUser(dialogId.toString(), "/queue/messages", userIds, header);
    }

    @MessageMapping("/dialogs/stop_typing")
    public void stopTyping(@Header("dialog_id") Integer dialogId, @Payload DialogUserShortListDto userIds) {

        Map<String, Object> header = new HashMap<>();
        header.put("type", "stop_typing");
        messagingTemplate.convertAndSendToUser(dialogId.toString(), "/queue/messages", userIds, header);
    }

    @MessageMapping("/dialogs/send_message")
    public void sendMessages(@Header("token") String token,
                            @Header("dialog_id") Integer dialogId,
                            @Payload MessageRq text) {

        String authorId = personService.getPersonByToken(token).getId().toString();
        Map<String, Object> header = new HashMap<>();
        header.put("type", "send_messages");
        header.put("author_id", authorId);
        ListResponseRs<MessageRq> response = new ListResponseRs<>();
        response.setData(new ArrayList<>());
        response.getData().add(text);
        messagingTemplate.convertAndSendToUser(dialogId.toString(), "/queue/messages", response, header);
        dialogsService.sendMessage(token, dialogId, text);
    }

    /*@MessageMapping("/dialogs/get_unreaded")
    public void getUnread(@Header("token") String token) {

        Map<String, Object> header = new HashMap<>();
        header.put("type", "get_unreaded");
        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.getUnread(token), header);
    }*/

    @MessageMapping("/dialogs/mark_readed")
    public void markReaded(@Header("token") String token,
                           @Header(value = "id_message", defaultValue = "-1") Integer messageId,
                           @Header(value = "dialog_id") Integer dialogId) {

        Map<String, Object> header = new HashMap<>();
        header.put("type", "mark_readed");
        Integer authorId = personService.getPersonByToken(token).getId();
        if (messageId >= 0){
            messagingTemplate.convertAndSendToUser(dialogId.toString(), "/queue/messages",
                    dialogsService.markAsReadMessage(messageId), header);

        } else {
            messagingTemplate.convertAndSendToUser(dialogId.toString(),
                    "/queue/messages", dialogsService.markDialogAsReadMessage(dialogId, authorId), header);
        }
    }
}
