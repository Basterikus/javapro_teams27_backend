package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.CityMapper;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CityRepository {
    
    private final RowMapper<City> rowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    
    public City findById(int id) {
        try {
            String sql = "select * from city where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("city id = " + id);
        }
    }
}
