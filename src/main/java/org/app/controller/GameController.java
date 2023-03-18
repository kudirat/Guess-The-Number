package org.app.controller;

import org.app.entity.Round;
import org.app.service.ServiceLayerImpl;
import org.app.view.UserIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controller class handles request
 * Executes actions that result in data
 */

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    UserIO io;

    @Autowired
    ServiceLayerImpl serviceLayer;


    @Autowired
    JdbcTemplate jdbc;

    /**
     * Starts the game, returns a 201 created message and the created game
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/begin")
    public String[] beginGame(){
      String[] currGame = serviceLayer.beginGame();

      return currGame;
    }

    /**
     * Gets a game by its game id
     * @param gameid
     * @return
     */
    @GetMapping("/game/{gameid}")
    public String[] getGameById(@PathVariable("gameid") int gameid){
        String[] currGame = serviceLayer.getGameById(gameid);
        return currGame;
    }

    @GetMapping("/game")
    public List<Object> getAllGames(){
      List<Object> currGames = Collections.singletonList(serviceLayer.getGames());
      return currGames;
    }

    //"rounds/{gameId}
    // – GET – Returns a list of rounds for the
    // specified game sorted by time.
    @GetMapping("/rounds/{gameid}")
    public List<Object> getRoundsbyId(@PathVariable("gameid") int gameid){
       List<Object> gameRounds = Collections.singletonList(serviceLayer.getGameRounds(gameid));
       return gameRounds;
    }


    //"guess" – POST – Makes a guess by passing the guess
    // and gameId in as JSON. The program must calculate
    // the results of the guess and mark the game finished
    // if the guess is correct. It returns the Round object
    // with the results filled in.

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/guess")
    public String[] makeGuess(@RequestBody Round round){
        int gameid = io.readInt("Enter the game id");
        int guess = io.readInt("Please enter a 4 digit number.");
       String[] roundInfo = serviceLayer.makeGuess(guess, gameid);

        return roundInfo;
    }

}
