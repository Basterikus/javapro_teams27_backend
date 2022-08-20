package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.service.FriendsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {
    
    private final FriendsService friendsService;
    
    @GetMapping("/recommendations")
    private ResponseEntity<ListResponseDtoRs<PersonDto>> getRecommendations(
            @RequestHeader(value = "Authorization", required = false, defaultValue = "") String token,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "perPage", required = false, defaultValue = "10") int itemPerPage) {
        
        ListResponseDtoRs<PersonDto> recommendations = friendsService.getRecommendations(token, offset, itemPerPage);
        return ResponseEntity.ok(recommendations);
    }
}
