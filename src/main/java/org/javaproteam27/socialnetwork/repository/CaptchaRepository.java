package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Captcha;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CaptchaRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Captcha> rowMapper = (rs, rowNum) -> {

        Captcha captcha = new Captcha();

        captcha.setId(rs.getInt("id"));
        captcha.setTime(rs.getTimestamp("time").toLocalDateTime());
        captcha.setCode(rs.getString("code"));
        captcha.setSecretCode(rs.getString("secret_code"));

        return captcha;
    };


    public Captcha findByCode(String code) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM captcha WHERE code = ?", rowMapper, Captcha.class);
    }

    public List<Captcha> findAll() {
        return jdbcTemplate.queryForList(
                "SELECT * FROM captcha", Captcha.class);
    }

    public void deleteProfileById(int id) {
        jdbcTemplate.update("DELETE FROM captcha WHERE id = ?");
    }

    public void save(Captcha captcha) {

        String sql = "insert into captcha(time, code, secret_code) values (?,?,?)";
        jdbcTemplate.update(sql, captcha.getTime(), captcha.getCode(), captcha.getSecretCode());
    }
}
