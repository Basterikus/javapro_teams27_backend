package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.service.FriendsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {
    
    private final FriendsService friendsService;
    
    @GetMapping("/recommendations")
    private ResponseEntity<ListResponseDto> recommendations(
            @RequestParam("id") int id,
            @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
            @RequestParam(value = "perPage", required = false, defaultValue = "0") int itemPerPage) {
        ListResponseDto recommendations = friendsService.getRecommendations(id, offset, itemPerPage);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
}
