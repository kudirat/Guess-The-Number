package org.app.model;

import org.app.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GameDaoImpl implements GameDao{
    @Autowired
    JdbcTemplate jdbc;

   // List<Game> currGames = new ArrayList<>();

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
        //this.currGames.add(game);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String GET_GAMES = "SELECT gameid, answer, gamestatus FROM games;";
        //this.currGames = jdbc.query(GET_GAMES, new GameMapper());
        List<Game> currGames = jdbc.query(GET_GAMES, new GameMapper());
        return currGames;
    }

    @Override
    public Game getGameById(int gameid) {
        final String GET_GAME = "SELECT * FROM games WHERE gameid = ?;";
        Game currGame = jdbc.queryForObject(GET_GAME, new GameMapper(), gameid);
//        if(currGame.getStatus().matches("In Progress")){
//            currGame.setAnswer(0000);
//        }
        return currGame;
    }

    @Override
    public void deleteGameById(int gameid) {
        final String DELETE_GAME = "DELETE FROM games WHERE gameid = ?;";
        //Game currGame = jdbc.queryForObject(DELETE_GAME, new GameMapper(), gameid);
        jdbc.update(DELETE_GAME, gameid);
    }

    @Override
    public void updateGameById(int gameid) {
        final String UPDATE_GAME = "UPDATE games SET gamestatus = ? WHERE gameid = ?;";
        //Game currGame = jdbc.queryForObject(UPDATE_GAME, new GameMapper(), "Finished", gameid);
        String finished = "Finished";
        jdbc.update(UPDATE_GAME, finished, gameid);

        //return currGame;
    }



}
