package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.handler.exception.UnableCreateEntityException;
import org.javaproteam27.socialnetwork.model.dto.response.ComplexRs;
import org.javaproteam27.socialnetwork.model.dto.response.DialogRs;
import org.javaproteam27.socialnetwork.model.dto.response.DialogUserShortListDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.MessageRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.entity.Dialog;
import org.javaproteam27.socialnetwork.model.entity.Message;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.DialogRepository;
import org.javaproteam27.socialnetwork.repository.MessageRepository;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DialogsService {
    
    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    
    
    private Person getPerson(String token) {
        String email = jwtTokenProvider.getUsername(token);
        return personRepository.findByEmail(email);
    }
    
    
    public ResponseRs<ComplexRs> getUnreaded(String token) {
        
        Integer personId = getPerson(token).getId();
        Integer unreadedCount = messageRepository.getUnreadedCount(personId);
        ComplexRs complexRs = ComplexRs.builder().count(unreadedCount).build();
        
        return new ResponseRs<>(null, complexRs, null);
    }
    
    public ListResponseRs<DialogRs> getDialogs(String token, String query, Integer offset, Integer itemPerPage) {
        
        Person person = getPerson(token);
        Integer personId = person.getId();
    
        Stream<Dialog> dialogStream;
        
        if (query != null && !query.isBlank()) {
            
            List<Person> persons = new ArrayList<>();
            String[] splittedQuery = query.split(" ");
            
            switch (splittedQuery.length) {
                case 1: {
                    persons.addAll(personRepository.findPeople(person, splittedQuery[0], null,
                            null, null, null, null));
                    persons.addAll(personRepository.findPeople(person, null, splittedQuery[0],
                            null, null, null, null));
                    break;
                }
                case 2: {
                    persons.addAll(personRepository.findPeople(person, splittedQuery[0], splittedQuery[1],
                            null, null, null, null));
                    persons.addAll(personRepository.findPeople(person, splittedQuery[1], splittedQuery[0],
                            null, null, null, null));
                    break;
                }
            }
    
            dialogStream = persons.stream()
                    .distinct()
                    .map(Person::getId)
                    .map(id -> dialogRepository.findByPersonIds(personId, id));
            
        } else {
            dialogStream = dialogRepository.findByPersonId(personId).stream();
        }
    
        List<DialogRs> dialogRs = dialogStream
                .sorted(Comparator.reverseOrder())
                .skip(offset)
                .limit(itemPerPage)
                .map(dialog -> {
                
                    Message message = messageRepository.findById(dialog.getId());
                
                    MessageRs messageRs = MessageRs.builder()
                            .id(message.getId())
                            .time(message.getTime())
                            .authorId(message.getAuthorId())
                            .recipientId(message.getRecipientId())
                            .messageText(message.getMessageText())
                            .readStatus(message.getReadStatus())
                            .build();
                
                    return DialogRs.builder()
                            .id(dialog.getId())
                            .unreadCount(messageRepository.getUnreadedCountByDialogId(dialog.getId()))
                            .lastMessage(messageRs)
                            .build();
                })
                .collect(Collectors.toList());
        
        return new ListResponseRs<>("", offset, itemPerPage, dialogRs);
    }
    
    public ResponseRs<ComplexRs> createDialog(String token, List<Integer> userIds) {
    
        Integer firstPersonId = getPerson(token).getId();
        Integer secondPersonId = userIds.get(0);
    
        if (dialogRepository.existsByPersonIds(firstPersonId, secondPersonId)) {
            throw new UnableCreateEntityException("dialog with person ids = " + firstPersonId +
                    " and " + secondPersonId + " already exists");
        }
        
        Dialog newDialog = Dialog.builder()
                .firstPersonId(getPerson(token).getId())
                .secondPersonId(userIds.get(0))
                .lastActiveTime(LocalDateTime.now())
                .build();
        
        dialogRepository.save(newDialog);
        Dialog dialog = dialogRepository.findByPersonIds(firstPersonId, secondPersonId);
        ComplexRs complexRs = ComplexRs.builder().id(dialog.getId()).build();
        
        return new ResponseRs<>(null, complexRs, null);
    }
    
    
}
