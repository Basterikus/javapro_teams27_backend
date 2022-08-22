package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.City;

@Data
@AllArgsConstructor
public class CityDto {

    private int id;
    private String title;

    public CityDto(City city) {
        id = city.getId();
        title = city.getTitle();
    }
}