package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.Country;

@Data
@AllArgsConstructor
public class CountryDto {

    private int id;
    private String title;

    public CountryDto(Country country) {
        this.id = country.getId();
        this.title = country.getTitle();
    }
}