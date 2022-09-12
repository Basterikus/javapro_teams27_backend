package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ComplexRs;
import org.javaproteam27.socialnetwork.model.dto.response.DialogRs;
import org.javaproteam27.socialnetwork.model.dto.response.DialogUserShortListDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.MessageRs;
import org.javaproteam27.socialnetwork.model.dto.response.MessageSendRequestBodyRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.service.DialogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dialogs")
@RequiredArgsConstructor
public class DialogsController {
    
    private final DialogsService dialogsService;
    
    
    @PostMapping
    public ResponseEntity<ResponseRs<ComplexRs>> createDialogs(
            @RequestHeader("Authorization") String token,
            @RequestBody DialogUserShortListDto userIds
    ) {
        ResponseRs<ComplexRs> response = dialogsService.createDialog(token, userIds.getUserIds());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<ListResponseRs<DialogRs>> getDialogs(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "perPage", defaultValue = "10") Integer itemPerPage
    ) {
        ListResponseRs<DialogRs> response = dialogsService.getDialogs(token, offset, itemPerPage);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/unreaded")
    public ResponseEntity<ResponseRs<ComplexRs>> getUnreaded(
            @RequestHeader("Authorization") String token
    ) {
        ResponseRs<ComplexRs> response = dialogsService.getUnreaded(token);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseRs<ComplexRs>> deleteDialog(
            @PathVariable Integer id
    ) {
        ResponseRs<ComplexRs> response = dialogsService.deleteDialog(id);
        return ResponseEntity.ok(response);
    }
    
//    @PutMapping("/{id}/users")
//    public ResponseEntity<ResponseRs<DialogUserShortListDto>> putUserToDialog(
//            @PathVariable Integer id,
//            @RequestBody DialogUserShortListDto userIds
//    ) {
//        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
//    }
    
//    @DeleteMapping("/{id}/users")
//    public ResponseEntity<ResponseRs<DialogUserShortListDto>> deleteUsersFromDialog(
//            @PathVariable Integer id,
//            @RequestParam("user_ids") List<Integer> userIds
//    ) {
//        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
//    }
    
    @PostMapping("/{id}/messages")
    public ResponseEntity<ResponseRs<MessageRs>> sendMessage(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody MessageSendRequestBodyRs text
    ) {
        ResponseRs<MessageRs> response = dialogsService.sendMessage(token, id, text);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/messages")
    public ResponseEntity<ListResponseRs<MessageRs>> getMessagesByDialog(
            @PathVariable Integer id,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "perPage", defaultValue = "10") Integer itemPerPage
    ) {
        ListResponseRs<MessageRs> response = dialogsService.getMessagesByDialog(id, offset, itemPerPage);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity<ResponseRs<MessageRs>> editMessage(
            @PathVariable("dialog_id") Integer dialogId,
            @PathVariable("message_id") Integer messageId,
            @RequestBody MessageSendRequestBodyRs text
    ) {
        ResponseRs<MessageRs> response = dialogsService.editMessage(dialogId, messageId, text);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public ResponseEntity<ResponseRs<ComplexRs>> markAsReadMessage(
            @PathVariable("dialog_id") Integer dialogId,
            @PathVariable("message_id") Integer messageId
    ) {
        ResponseRs<ComplexRs> response = dialogsService.markAsReadMessage(dialogId, messageId);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity<ResponseRs<ComplexRs>> deleteMessage(
            @PathVariable("dialog_id") Integer dialogId,
            @PathVariable("message_id") Integer messageId
    ) {
        ResponseRs<ComplexRs> response = dialogsService.deleteMessage(dialogId, messageId);
        return ResponseEntity.ok(response);
    }
    
//    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
//    public ResponseEntity<ResponseRs<MessageRs>> recoverMessage(
//            @PathVariable Integer dialogId,
//            @PathVariable Integer messageId
//    ) {
//        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
//    }

}
