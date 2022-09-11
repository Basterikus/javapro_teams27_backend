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
    
    
    @GetMapping
    public ResponseEntity<ListResponseRs<DialogRs>> getDialogs(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "perPage", defaultValue = "10") Integer itemPerPage
    ) {
        ListResponseRs<DialogRs> dialogs = dialogsService.getDialogs(token, query, offset, itemPerPage);
        return ResponseEntity.ok(dialogs);
    }
    
    @PostMapping
    public ResponseEntity<ResponseRs<ComplexRs>> createDialogs(
            @RequestHeader("Authorization") String token,
            @RequestBody DialogUserShortListDto userIds
    ) {
        ResponseRs<ComplexRs> dialog = dialogsService.createDialog(token, userIds.getUserIds());
        return ResponseEntity.ok(dialog);
    }
    
    @GetMapping("/unreaded")
    public ResponseEntity<ResponseRs<ComplexRs>> getUnreaded(
            @RequestHeader("Authorization") String token
    ) {
        ResponseRs<ComplexRs> unreaded = dialogsService.getUnreaded(token);
        return ResponseEntity.ok(unreaded);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseRs<ComplexRs>> deleteDialog(
            @PathVariable Integer number
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @PutMapping("/{id}/users")
    public ResponseEntity<ResponseRs<DialogUserShortListDto>> putUserToDialog(
            @PathVariable Integer id,
            @RequestBody DialogUserShortListDto userIds
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @DeleteMapping("/{id}/users")
    public ResponseEntity<ResponseRs<DialogUserShortListDto>> deleteUsersFromDialog(
            @PathVariable Integer id,
            @RequestParam List<Integer> userIds
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @GetMapping("/{id}/messages")
    public ResponseEntity<ListResponseRs<MessageRs>> getMessages(
            @PathVariable Integer id,
            @RequestParam("query") String query,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "perPage", defaultValue = "10") Integer itemPerPage
    ) {
        return ResponseEntity.ok(new ListResponseRs<>());
    }
    
    @PostMapping("/{id}/messages")
    public ResponseEntity<ResponseRs<MessageRs>> sendMessage(
            @PathVariable Integer id,
            @RequestBody MessageSendRequestBodyRs text
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity<ResponseRs<ComplexRs>> deleteMessage(
            @PathVariable Integer dialogId,
            @PathVariable Integer messageId
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @PutMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity<ResponseRs<MessageRs>> editMessage(
            @PathVariable Integer dialogId,
            @PathVariable Integer messageId,
            @RequestBody MessageSendRequestBodyRs text
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
    public ResponseEntity<ResponseRs<MessageRs>> recoverMessage(
            @PathVariable Integer dialogId,
            @PathVariable Integer messageId
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
    
    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public ResponseEntity<ResponseRs<ComplexRs>> markAsReadMessage(
            @PathVariable Integer dialogId,
            @PathVariable Integer messageId
    ) {
        return ResponseEntity.ok(new ResponseRs<>(null, null, null));
    }
}
