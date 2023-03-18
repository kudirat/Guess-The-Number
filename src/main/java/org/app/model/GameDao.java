package org.app.model;

import org.app.entity.Game;

import java.util.List;

public interface GameDao {

    Game addGame(int answer);

    List<Game> getAllGames();

    Game getGameById(int gameid);

    void deleteGameById(int id);
}
