package org.javaproteam27.socialnetwork.mapper;

import org.javaproteam27.socialnetwork.model.entity.NotificationType;
import org.javaproteam27.socialnetwork.model.enums.NotificationTypeCode;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationTypeMapper implements RowMapper<NotificationType> {
    
    @Override
    public NotificationType mapRow(ResultSet rs, int rowNum) throws SQLException {
    
        NotificationType notificationType = new NotificationType();
    
        notificationType.setId(rs.getInt("id"));
        notificationType.setCode(NotificationTypeCode.valueOf(rs.getString("code")));
        notificationType.setName(rs.getString("name"));
    
        return notificationType;
    }
}
