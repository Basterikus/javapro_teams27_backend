package org.javaproteam27.socialnetwork.mapper;

import org.javaproteam27.socialnetwork.model.entity.Notification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {
    
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
    
        Notification notification = new Notification();
    
        notification.setId(rs.getInt("id"));
        notification.setTypeId(rs.getInt("type_id"));
        notification.setSentTime(rs.getTimestamp("sent_time").toLocalDateTime());
        notification.setPersonId(rs.getInt("person_id"));
        notification.setEntityId(rs.getInt("entity_id"));
        notification.setContact(rs.getString("contact"));
    
        return notification;
    }
}
