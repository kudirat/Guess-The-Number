package org.app.model;

import org.app.entity.Game;
import org.app.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class RoundDaoImpl implements RoundDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Round addRound(Round round) {
        //Round round = new Round();
        LocalDateTime currTime = LocalDateTime.now();
        round.setRoundTime(currTime);
        final String INSERT_ROUND = "INSERT INTO rounds(roundid, gameid, guess, guessresult, finished, guesstime) VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ROUND,
                round.getId(),
                round.getGameid(),
                round.getGuess(),
                round.getGuessResult(),
                round.getFinished(),
                round.getRoundTime());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);
        return round;
    }

    /**
     * Ignore
     * @return
     */
    @Override
    public List<Round> getAllRounds() {
        final String GET_Rounds = "SELECT * FROM rounds;";
        //this.currGames = jdbc.query(GET_GAMES, new GameMapper());
        List<Round> currRounds = jdbc.query(GET_Rounds, new RoundMapper());
        return currRounds;
    }


    @Override
    public List<Round> getAllRoundForGame(int gameId) {
        final String GET_GAME_ROUNDS = "SELECT * FROM rounds WHERE gameid = ?;";
        List<Round> currRounds = jdbc.query(GET_GAME_ROUNDS, new RoundMapper(), gameId);
        Collections.sort(currRounds);
        return currRounds;
    }


    /**
     * Ignore. Rounds are deleted once a game is deleted
     * @param roundId
     */
    @Override
    public void deleteRoundById(int roundId) {

    }



    public static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();

            Timestamp timestamp = rs.getTimestamp("guesstime");

            round.setId(rs.getInt("roundid"));
            round.setGameid(rs.getInt("gameid"));
            round.setGuess(rs.getInt("guess"));
            round.setRoundTime(timestamp.toLocalDateTime());
            round.setGuessResult(rs.getString("guessresult"));
            round.setFinished(rs.getBoolean("finished"));
            return round;
        }
    }


}
