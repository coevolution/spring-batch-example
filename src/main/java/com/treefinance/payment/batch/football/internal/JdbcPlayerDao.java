package com.treefinance.payment.batch.football.internal;

import com.treefinance.payment.batch.football.Player;
import com.treefinance.payment.batch.football.PlayerDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author lxp
 * @date 2019/10/25 下午4:39
 * @Version 1.0
 */
public class JdbcPlayerDao implements PlayerDao {

    public static final String INSERT_PLAYER =
        "INSERT into PLAYERS (player_id, last_name, first_name, pos, year_of_birth, year_drafted)" +
            " values (:id, :lastName, :firstName, :position, :birthYear, :debutYear)";

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Override
    public void savePlayer(Player player) {
        namedParameterJdbcTemplate.update(INSERT_PLAYER, new BeanPropertySqlParameterSource(player));
    }

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
