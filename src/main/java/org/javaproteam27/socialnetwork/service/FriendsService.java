package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.model.dto.response.CityDto;
import org.javaproteam27.socialnetwork.model.dto.response.CountryDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.javaproteam27.socialnetwork.model.entity.Country;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.FriendshipStatusCode;
import org.javaproteam27.socialnetwork.repository.CityRepository;
import org.javaproteam27.socialnetwork.repository.CountryRepository;
import org.javaproteam27.socialnetwork.repository.FriendshipRepository;
import org.javaproteam27.socialnetwork.repository.FriendshipStatusRepository;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendsService {
    
    private final PersonRepository personRepository;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    
    
    public ListResponseDto<PersonDto> getRecommendations(String email, int offset, int itemPerPage) {
        
        Person person = personRepository.findByEmail(email);
        Integer myId = person.getId();
        
        List<Integer> myFriendsIds = getMyFriendsIds(myId);
        List<Integer> friendsIdsForFriends = getFriendsIdsForFriends(myFriendsIds, myId);
        Map<Integer, Integer> recommendationsCounts = getRecommendationsCounts(friendsIdsForFriends);
        List<Integer> sortedRecommendationsByCount = getSortedRecommendationsByCount(recommendationsCounts);
        List<Integer> limitedRecommendations = limitRecommendations(sortedRecommendationsByCount, offset, itemPerPage);
        List<Person> persons = getPersons(limitedRecommendations);
        
        return getResultJson(persons, offset, itemPerPage);
    }
    
    private List<Integer> getMyFriendsIds(Integer myId) {
        
        return friendshipRepository
                .findByPersonIdAndStatus(myId, FriendshipStatusCode.FRIEND).stream()
                .flatMap(friendship -> Stream.of(
                        friendship.getSrcPersonId(), friendship.getDstPersonId()
                ))
                .filter(id -> !id.equals(myId))
                .collect(Collectors.toList());
    }
    
    private List<Integer> getFriendsIdsForFriends(List<Integer> friendsIds, Integer myId) {

        return friendsIds.stream()
                .flatMap(id -> friendshipRepository
                        .findByPersonIdAndStatus(id, FriendshipStatusCode.FRIEND).stream()
                        .flatMap(fs -> Stream.of(fs.getSrcPersonId(), fs.getDstPersonId()))
                        .filter(personId -> !friendsIds.contains(personId))
                        .filter(personId -> !personId.equals(myId)))
                .collect(Collectors.toList());
    }
    
    private Map<Integer, Integer> getRecommendationsCounts(List<Integer> friendsIdsForFriends) {
        
        Map<Integer, Integer> friendshipsCount = new HashMap<>();
        
        friendsIdsForFriends.forEach(id -> {
            Integer count = friendshipsCount.getOrDefault(id, 0);
            friendshipsCount.put(id, count + 1);
        });
        
        return friendshipsCount;
    }
    
    private List<Integer> getSortedRecommendationsByCount(Map<Integer, Integer> recommendationsCounts) {

        return recommendationsCounts.keySet().stream()
                .sorted((ffc1, ffc2) ->
                        recommendationsCounts.get(ffc2).compareTo(recommendationsCounts.get(ffc1)))
                .collect(Collectors.toList());
    }
    
    private List<Integer> limitRecommendations(List<Integer> sortedRecommendationsByCount,
                                               int offset, int itemPerPage) {
        return sortedRecommendationsByCount.stream()
                .skip(offset)
                .limit(itemPerPage)
                .collect(Collectors.toList());
    }
    
    private List<Person> getPersons(List<Integer> limitedRecommendations) {
        
        return limitedRecommendations.stream()
                .map(id -> {
                    try {
                        return Optional.of(personRepository.findById(id));
                    } catch (EntityNotFoundException e) {
                        return Optional.empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(optional -> (Person) optional)
                .collect(Collectors.toList());
    }
    
    private ListResponseDto<PersonDto> getResultJson(List<Person> persons, int offset, int itemPerPage) {
        
        List<PersonDto> data = persons.stream()
                .map(person -> {
                    
                    City city = cityRepository.findById(person.getCityId());
                    Country country = countryRepository.findById(city.getCountryId());
                    
                    return new PersonDto(person);
                })
                .collect(Collectors.toList());
        
        return new ListResponseDto<>("", offset, itemPerPage, data);
    }
    
}
