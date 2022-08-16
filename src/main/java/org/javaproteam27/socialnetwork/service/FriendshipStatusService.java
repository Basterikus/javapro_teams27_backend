package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.FriendshipStatus;
import org.javaproteam27.socialnetwork.repository.FriendshipStatusRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipStatusService {
    
    private final FriendshipStatusRepository friendshipStatusRepository;
    
    
    public FriendshipStatus findById(int id) {
        return friendshipStatusRepository.findById(id);
    }
    
}
