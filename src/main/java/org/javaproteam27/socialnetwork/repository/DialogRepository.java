package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.DialogMapper;
import org.javaproteam27.socialnetwork.model.entity.Dialog;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DialogRepository {
    
    private final DialogMapper rowMapper = new DialogMapper();
    private final JdbcTemplate jdbcTemplate;
    
    
    private static List<Integer> sortIds(Integer firstPersonId, Integer secondPersonId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(firstPersonId);
        ids.add(secondPersonId);
        ids.sort(Comparator.naturalOrder());
        return ids;
    }
    
    
    public void save(Dialog dialog) {
        
        Integer firstPersonId = dialog.getFirstPersonId();
        Integer secondPersonId = dialog.getSecondPersonId();
    
        List<Integer> sortedIds = sortIds(firstPersonId, secondPersonId);
        String sql = "insert into dialog (first_person_id, second_person_id, last_message_id, last_active_time)" +
                " values (?,?,?,?)";
        jdbcTemplate.update(sql, sortedIds.get(0), sortedIds.get(1),
                dialog.getLastMessageId(), dialog.getLastActiveTime());
    }
    
    public Dialog findById(Integer id) {
    
        try {
            String sql ="select * from dialog where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("dialog id = " + id);
        }
    }
    
    public List<Dialog> findByPersonId(Integer id) {
    
        try {
            String sql ="select * from dialog where first_person_id = ? or second_person_id = ?";
            return jdbcTemplate.query(sql, rowMapper, id, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("dialogs with person id = " + id);
        }
    }
    
    public Dialog findByPersonIds(Integer firstPersonId, Integer secondPersonId) {
    
        List<Integer> sortedIds = sortIds(firstPersonId, secondPersonId);
    
        String sql;
        if (firstPersonId.equals(secondPersonId)) {
            sql = "select * from dialog where first_person_id = ? and second_person_id = ?";
        } else {
            sql = "select * from dialog where first_person_id = ? or second_person_id = ?";
        }
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, sortedIds.get(0), sortedIds.get(1));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("dialog with person ids = " + firstPersonId + " and " + secondPersonId);
        }
    }
    
    public Boolean existsByPersonIds(Integer firstPersonId, Integer secondPersonId) {
    
        try {
            return findByPersonIds(firstPersonId, secondPersonId) != null;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
    
    

}
