package org.app.service;

import org.app.entity.Game;
import org.app.entity.Round;
import org.app.model.GameDaoImpl;
import org.app.model.RoundDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ServiceLayerImpl {

    @Autowired
    GameDaoImpl gameDao;

    @Autowired
    RoundDaoImpl roundDao;

    /**
     * Retrieves Finished Games from list of games in database
     * @param currgames
     * @return
     */
    public List<Game> checkifFinished(List<Game> currgames){
        List<Game> finishedGames = new ArrayList<>();
        for(Game game: currgames){
            if(!game.getStatus().matches("In Progress")){
                finishedGames.add(game);
            }
            else{
                continue;
            }
        }
        return finishedGames;
    }


    /**
     * Generates an answer for the game and returns it as as int
     * @return
     */
    public int generateAnswer(){
        List<Integer> nums = new ArrayList<>();
        Set<Integer> dupsCheck = new HashSet<>();
        for(int i = 0; i < 10; i++){
            nums.add(i);
        }

        //check for duplicates
        for(Integer num: nums){
            if(!dupsCheck.contains(num)){
                dupsCheck.add(num);
            }
            else{
                break;
            }
        }
        //shuffle list
        Collections.shuffle(nums);

        //append the answer to a string
        String answer = "";
        for(int i = 0; i <= 3; i++){
            answer += nums.get(i).toString();
        }

        //convert to int and return
        return Integer.parseInt(answer);
    }

    /**
     * Start the game through game dao
     * @return
     */
    public String[] beginGame(){
        int answer = generateAnswer();
        Game newGame = gameDao.addGame(answer);
        String[] currGame = {String.valueOf(newGame.getId()), newGame.getStatus(), newGame.getTime()};
        return currGame;
    }

    /**
     * Gets the current games in the database
     */
    public List<Game> getGames(){
        List<Game> currGames = gameDao.getAllGames();
        for(Game game: currGames){
            if(!game.getStatus().matches("Finished")){
               //hide answer
                game.setAnswer(Integer.parseInt("0000"));
            }
        }
        return currGames;
    }

    public String[] getGameById(int gameid){
        Game game = gameDao.getGameById(gameid);
        String[] currGame = {String.valueOf(game.getId()), game.getStatus(), game.getTime()};
        return currGame;
    }

    public List<Round> getGameRounds(int gameid){
        List<Round> roundsForGame = roundDao.getAllRoundForGame(gameid);
        return roundsForGame;
    }

    //pass guess and gameid in as json,
   // calculate the results of the guess and
    //check if guess is correct
    // mark the game finished and return round object with results filled in
    public String[] makeGuess(int guess, int gameid){
        Game game = gameDao.getGameById(gameid);
        Boolean finished = false;

        int answer = game.getAnswer();
        //get the exaact and partial matches
        int[] result_arr = checkGuess(gameid, guess);
        String result = getResult(result_arr);
        //if the sum of the exact matches is 4, then mark the game as finished
        if(result_arr[0] == 4){
            game.setStatus("Finished");
            finished = true;
        }

       //Make a Round object and return it with the results filled in
        Round round = roundDao.addRound(gameid);
        round = roundDao.updateRound(round, gameid, guess, finished);

        String[] roundInfo = {String.valueOf(round.getId()), String.valueOf(round.getGameid()), String.valueOf(round.getGuess()), String.valueOf(round.getFinished())};
        return roundInfo;
    }


    /**
     * Get result
     */
    public String getResult(int[] arr){
        int partials = arr[0];
        int exact = arr[1];

        return "e:" + exact + ":p:" + partials;
    }

    /**
     * Checks if a guess is correct
     */
    public int[] checkGuess(int guess, int gameId){
        int partials = 0;
        int exact = 0;

        //get the current game
        Game game = gameDao.getGameById(gameId);
        int answer = game.getAnswer();

        //put them in arrays
        String answer_str = Integer.toString(guess);
        String guess_str = Integer.toString(answer);

        int[] answer_arr = new int[answer_str.length()];
        int[] guess_arr = new  int[guess_str.length()];

        //loop through to check for an exact match (when user guesses the correct digit in the correct pos)
        //loop through to check for a partial match (correct digit in the wrong position)
        for(int i = 0; i < answer_arr.length; i++){
            for(int j = 0; j < guess_arr.length; j++){
                if(guess_arr[i] == answer_arr[j]){
                    exact++;
                }
                else{
                    partials++;
                }
            }
        }


        int[] results_arr = {exact, partials};


        return results_arr;
    }


}
