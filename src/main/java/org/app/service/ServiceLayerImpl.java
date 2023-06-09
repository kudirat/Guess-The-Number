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
     * Ignore: Method not needed
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
        for(int i = 0; i < 4; i++){
            answer += nums.get(i).toString();
        }

        //if length is greater than 4, call the method again
        if(answer.length() > 4){
            generateAnswer();
        }

        //convert to int and return
        return Integer.parseInt(answer);
    }

    /**
     * Start the game through game dao
     * @return
     */
    public int beginGame(){
        int answer = generateAnswer();
        Game newGame = gameDao.addGame(answer);
        //String[] currGame = {String.valueOf(newGame.getId()), newGame.getStatus()};
        return newGame.getId();
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

        List<Round> currRounds = roundDao.getAllRoundForGame(gameid);

        for(Round round: currRounds){
            if(round.getFinished() == true){
                game.setStatus("Finished");
                gameDao.updateGameById(gameid);
            }
        }


        if(game.getStatus().matches("Finished")){
           // game.setAnswer(0000);
            return new String[]{String.valueOf(game.getId()), String.valueOf(game.getAnswer()), game.getStatus()};
        }
        else{
            return new String[]{String.valueOf(game.getId()), game.getStatus()};

        }
    }

    public List<Round> getGameRounds(int gameid){
        List<Round> roundsForGame = roundDao.getAllRoundForGame(gameid);
        return roundsForGame;
    }

    /**
     * pass guess and gameid in as json,
     * calculate the results of the guess and
     *  mark the game finished and return round object with results filled in
     */
    public Round makeGuess(Round round){
        Game game = gameDao.getGameById(round.getGameid());
        int guess = round.getGuess();
        Boolean finished = false;
        int answer = game.getAnswer();

        //get the exact and partial matches
        int[] result_arr = checkGuess(answer, guess);
        String result = getResult(result_arr);
        round.setGuessResult(result);
        //if the sum of the exact matches is 4, then mark the game as finished
        if(result.charAt(2) == '4'){
            game.setStatus("Finished");
            finished = true;
            round.setFinished(finished);
        }

       //Make a Round object and return it with the results filled in
        round = roundDao.addRound(round);

        //String[] roundInfo = {String.valueOf(round.getId()), String.valueOf(round.getGameid()), String.valueOf(round.getGuess()), String.valueOf(round.getFinished())};
        return round;
    }


    /**
     * Get result
     */
    public String getResult(int[] arr){
        int partials = arr[1];
        int exact = arr[0];

        return "e:" + exact + ":p:" + partials;
    }

    /**
     * Checks if a guess is correct
     */
    public int[] checkGuess(int answer, int guess){
        int partials = 0;
        int exact = 0;

        Set<Character> dupsCheck = new HashSet<>();
        //convert the ints into Strings and then into arrays
        String answer_str = Integer.toString(answer);
        String guess_str = Integer.toString(guess);


        for(int i= 0;i < 4;i++){
            if(guess_str.charAt(i) == answer_str.charAt(i)){
                exact++;
            }else if(answer_str.contains(guess_str.charAt(i)+"")){
                partials++;
            }
        }

        int[] results_arr = {exact, partials};


        return results_arr;
    }


    public void deleteByGameId(int gameid){
         gameDao.deleteGameById(gameid);
    }
}
