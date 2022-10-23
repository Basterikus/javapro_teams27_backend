package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.MessageRq;
import org.javaproteam27.socialnetwork.model.dto.response.DialogUserShortListDto;
import org.javaproteam27.socialnetwork.service.DialogsService;
import org.javaproteam27.socialnetwork.service.PersonService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DialogsWebSocketController {

    private final DialogsService dialogsService;

    private final PersonService personService;

    private final SimpMessagingTemplate messagingTemplate;


//    @MessageMapping("/dialogs/chat")
//    @SendTo("/topic/activity")
    /*@MessageMapping("dialogs/send_message")
    public void sendMessage(@Header("token") String token,
            @Header("id_dialog") Integer dialogId,
            @Payload MessageRq messageRq) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.sendMessage(token, dialogId, messageRq));
    }*/

    @MessageMapping("/dialogs/create_dialog")
    public void createDialog(@Header("token") String token,
                             @Payload DialogUserShortListDto userIds) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.createDialog(token, userIds.getUserIds()));
    }

    @MessageMapping("/dialogs/get_dialogs")
    public void getDialog(@Header("token") String token) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.getDialogs(token, 0, 10));
    }

    @MessageMapping("/dialogs/get_unreaded")
    public void getUnread(@Header("token") String token) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.getUnread(token));
    }

    @MessageMapping("/dialogs/delete_dialog")
    public void deleteDialog(@Header("token") String token,
                             @Header("id_dialog") Integer dialogId) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.deleteDialog(dialogId));
    }

    @MessageMapping("/dialogs/send_message")
    public void sendMessage(@Header("token") String token,
                            @Header("id_dialog") Integer dialogId,
                            @Payload MessageRq text) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.sendMessage(token, dialogId, text));
    }

    @MessageMapping("/dialogs/get_messages")
    public void getMessages(@Header("token") String token,
                            @Header("id_dialog") Integer dialogId) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.getMessagesByDialog(dialogId, 0, 100));
    }

    @MessageMapping("/dialogs/edit_message")
    public void editMessage(@Header("token") String token,
                            @Header("id_message") Integer messageId,
                            @Payload MessageRq text) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.editMessage(messageId, text));
    }

    @MessageMapping("/dialogs/mark_readed")
    public void markReaded(@Header("token") String token,
                            @Header("id_message") Integer messageId) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.markAsReadMessage(messageId));
    }

    @MessageMapping("/dialogs/delete_message")
    public void deleteMessage(@Header("token") String token,
                           @Header("id_message") Integer messageId,
                           @Header("id_dialog") Integer dialogId) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.deleteMessage(dialogId, messageId));
    }

    @MessageMapping("/dialogs/recover_message")
    public void recoverMessage(@Header("token") String token,
                              @Header("id_message") Integer messageId,
                              @Header("id_dialog") Integer dialogId) {

        messagingTemplate.convertAndSendToUser(personService.getPersonByToken(token).getId().toString(),
                "/queue/messages", dialogsService.recoverMessage(dialogId, messageId));
    }
}
