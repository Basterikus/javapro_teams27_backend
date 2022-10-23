package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PersonSettingsRq;
import org.javaproteam27.socialnetwork.model.dto.response.PersonSettingsRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.entity.PersonSettings;
import org.javaproteam27.socialnetwork.repository.PersonSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PersonSettingsService {

    private final PersonSettingsRepository personSettingsRepository;
    private final PersonService personService;

    public ResponseRs<PersonSettingsRs> getPersonSettings() {
        var personId = personService.getAuthorizedPerson().getId();
        var ps = personSettingsRepository.findByPersonId(personId);
        var data = convertToResponse(ps);
        return new ResponseRs<>("", data, null);
    }

    public ResponseRs<Object> editPersonSettings(PersonSettingsRq rq) {
        var personId = personService.getAuthorizedPerson().getId();
        var ps = personSettingsRepository.findByPersonId(personId);
        PersonSettings personSettings = PersonSettings.builder()
                .personId(personId)
                .postCommentNotification(rq.getPostCommentNotification() == null ?
                        ps.getPostCommentNotification() : rq.getPostCommentNotification())
                .commentCommentNotification(rq.getCommentCommentNotification() == null ?
                        ps.getCommentCommentNotification() : rq.getCommentCommentNotification())
                .friendRequestNotification(rq.getFriendRequestNotification() == null ?
                        ps.getFriendRequestNotification() : rq.getFriendRequestNotification())
                .messageNotification(rq.getMessageNotification() == null ?
                        ps.getMessageNotification() : rq.getMessageNotification())
                .friendBirthdayNotification(rq.getFriendBirthdayNotification() == null ?
                        ps.getFriendBirthdayNotification() : rq.getFriendBirthdayNotification())
                .likeNotification(rq.getLikeNotification() == null ?
                        ps.getLikeNotification() : rq.getLikeNotification())
                .postNotification(rq.getPostNotification() == null ?
                        ps.getPostNotification() : rq.getPostNotification())
                .build();
        personSettingsRepository.update(personSettings);
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "ok");
        return new ResponseRs<>("", data, null);
    }

    private PersonSettingsRs convertToResponse(PersonSettings ps) {
        return PersonSettingsRs.builder()
                .id(ps.getId())
                .personId(ps.getPersonId())
                .postCommentNotification(ps.getPostCommentNotification())
                .commentCommentNotification(ps.getCommentCommentNotification())
                .friendRequestNotification(ps.getFriendRequestNotification())
                .messageNotification(ps.getMessageNotification())
                .friendBirthdayNotification(ps.getFriendBirthdayNotification())
                .likeNotification(ps.getLikeNotification())
                .postNotification(ps.getPostNotification())
                .build();
    }
}
