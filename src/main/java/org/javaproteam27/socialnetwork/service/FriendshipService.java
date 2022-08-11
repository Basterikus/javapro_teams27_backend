package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Friendship;
import org.javaproteam27.socialnetwork.model.enums.FriendshipStatusCode;
import org.javaproteam27.socialnetwork.repository.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    
    private final FriendshipRepository friendshipRepository;
    
    
    public void save(Friendship friendship) {
        friendshipRepository.save(friendship);
    }
    
    public List<Friendship> findByPersonId(int id) {
        return friendshipRepository.findByPersonId(id);
    }
    
    public List<Friendship> findByPersonIdAndStatus(Integer id, FriendshipStatusCode statusCode) {
        return friendshipRepository.findByPersonIdAndStatus(id, statusCode);
    }
    
}
