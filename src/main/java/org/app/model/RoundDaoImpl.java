package org.app.model;

import org.app.entity.Round;
import org.app.service.ServiceLayerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component
public class RoundDaoImpl implements RoundDao{
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    ServiceLayerImpl serviceLayer;

    @Override
    public Round addRound(int gameId) {
        Round round = new Round();
        final String INSERT_ROUND = "INSERT INTO rounds(roundid, guessid, guess, finished) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ROUND,
                round.getId(),
                round.getGameid(),
                round.getGuess(),
                round.getFinished());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);
        return round;
    }

    @Override
    public List<Round> getAllRounds() {
        return null;
    }

    @Override
    public List<Round> getAllRoundForGame(int gameId) {
        final String GET_GAME_ROUNDS = "SELECT * FROM rounds WHERE gameid = ?";
        List<Round> currRounds = jdbc.query(GET_GAME_ROUNDS, new RoundMapper());
        return currRounds;
    }

    @Override
    public void deleteRoundById(int roundId) {

    }

    @Override
    public Round updateRound(Round round, int gameid, int guess, Boolean finished) {
        round.setGuess(guess);
        round.setGameid(gameid);
        round.setFinished(finished);
        final String UPDATE_ROUND = "UPDATE rounds SET gameid = ?, guess = ?, finished = ? WHERE roundid = ?";
        jdbc.update(UPDATE_ROUND,
                round.getGameid(),
                round.getGuess(),
                round.getFinished(),
                round.getId());

        return round;
    }


    public static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("roundid"));
            round.setGameid(rs.getInt("gameid"));
            round.setGuess(rs.getInt("guess"));
            round.setFinished(rs.getBoolean("finished"));
            return round;
        }
    }


}
