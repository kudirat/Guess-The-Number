package org.app.model;

import org.app.entity.Game;
import org.app.service.ServiceLayerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameDaoImpl implements GameDao{
    @Autowired
    JdbcTemplate jdbc;

    List<Game> currGames = new ArrayList<>();

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("gameid"));
            game.setAnswer(rs.getInt("answer"));
            game.setStatus(rs.getString("gamestatus"));
            return game;
        }
    }

    @Override
    @Transactional
    public Game addGame(int answer){
        Game game = new Game();
        //generates an answer
        System.out.println(answer);
        game.setAnswer(answer);
        game.setStatus("In Progress");
        // game.getTime();
        final String INSERT_GAME = "INSERT INTO games(gameid, answer, gamestatus) VALUES(?,?,?)";
        jdbc.update(INSERT_GAME,
                game.getId(),
                game.getAnswer(),
                game.getStatus());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(newId);
        this.currGames.add(game);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String GET_GAMES = "SELECT gameid, answer, gamestatus, gametime FROM games;";
        this.currGames = jdbc.query(GET_GAMES, new GameMapper());
        return this.currGames;
    }

    @Override
    public Game getGameById(int gameid) {
        final String GET_GAME = "SELECT * FROM games WHERE gameid = ?;";
        Game currGame = jdbc.queryForObject(GET_GAME, new GameMapper(), gameid);
        return currGame;
    }

    @Override
    public void deleteGameById(int gameid) {
        final String DELETE_GAME = "DELETE FROM games WHERE gameid = ?;";
        Game currGame = jdbc.queryForObject(DELETE_GAME, new GameMapper(), gameid);
    }




}
