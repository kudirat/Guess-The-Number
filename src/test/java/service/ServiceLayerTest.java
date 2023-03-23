package service;

import org.app.TestApplicationConfiguration;
import org.app.entity.Game;
import org.app.entity.Round;
import org.app.model.GameDao;
import org.app.model.RoundDao;
import org.app.service.ServiceLayerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ServiceLayerTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    ServiceLayerImpl serviceLayer;

    @Test
    public void generateAnswerTest(){
        int answer = serviceLayer.generateAnswer();
        String answer_str = String.valueOf(answer);
        assertFalse(answer_str.length() > 4);
    }

    @Test
    public void beginGameTest(){
        serviceLayer.beginGame();
        int gameid = serviceLayer.beginGame();
        Game game = gameDao.getGameById(gameid);
        assertEquals(gameid, game.getId());
    }

    @Test
    public void makeGuessAndCheckTest(){
        Round round = new Round();
        round.setId(1);
        round.setGuess(1234);
        LocalDateTime timenow = LocalDateTime.now();
        round.setRoundTime(timenow);
        round.setGuessResult("e:4:p:0");
        round.setFinished(true);

        Round roundFromService = serviceLayer.makeGuess(round);
        assertTrue(round.getFinished() == true);

    }

    @Test
    public void GetGameByIdTest(){
        Game game = new Game();
        game.setAnswer(1234);
        game.setStatus("In Progress");
        game.setId(1);


        Round round = new Round();
        round.setId(1);
        round.setGuess(1234);
        round.setGameid(game.getId());
        LocalDateTime timenow = LocalDateTime.now();
        round.setRoundTime(timenow);
        round.setGuessResult("e:4:p:0");
        round.setFinished(true);

        Round roundFromService = serviceLayer.makeGuess(round);

        String[] correctRoundData = new String[] {String.valueOf(game.getId()), String.valueOf(game.getAnswer()), game.getStatus()};

        String[] roundData = serviceLayer.getGameById(roundFromService.getGameid());

        assertEquals(roundData, correctRoundData);

    }

    @Test
    public void testGetGames() {
        Game game = new Game();
        game.setId(1);
        game.setAnswer(1234);
        game.setStatus("In Progress");
        gameDao.addGame(game.getAnswer());

        Game game2 = new Game();
        game.setId(2);
        game.setAnswer(5678);
        game.setStatus("In Progress");
        gameDao.addGame(game2.getAnswer());

        List<Game> currGames = serviceLayer.getGames();

        assertEquals(2, currGames.size());
        assertTrue(currGames.contains(game));
        assertTrue(currGames.contains(game2));
    }

    @Test
    public void GetRoundsByGameIdTest(){
        Game game = new Game();
        game.setAnswer(1234);
        game.setStatus("In Progress");
        game.setId(1);


        Round round = new Round();
        round.setId(1);
        round.setGuess(1254);
        round.setGameid(game.getId());
        LocalDateTime timenow = LocalDateTime.now();
        round.setRoundTime(timenow);
        round.setGuessResult("e:3:p:1");
        round.setFinished(false);
        serviceLayer.makeGuess(round);

        Round round2 = new Round();
        round2.setId(2);
        round2.setGuess(1234);
        round2.setGameid(game.getId());
        LocalDateTime timenow2 = LocalDateTime.now();
        round2.setRoundTime(timenow2);
        round2.setGuessResult("e:4:p:0");
        round2.setFinished(true);
        serviceLayer.makeGuess(round2);

        round = serviceLayer.makeGuess(round);
        round2 = serviceLayer.makeGuess(round2);

        List<Round> currRoundsRight = new ArrayList<>();
        for(Round currRound: currRoundsRight){
            currRoundsRight.add(currRound);
        }

        List<Round> currRoundsFromService = serviceLayer.getGameRounds(game.getId());
        assertEquals(currRoundsRight, currRoundsFromService);

    }

    @Test
    public void testDeleteByGameId(){
        Game game = new Game();
        game.setAnswer(1234);
        game.setStatus("In Progress");
        game.setId(1);
        gameDao.addGame(game.getAnswer());

        serviceLayer.deleteByGameId(game.getId());
        Game deletedGame = gameDao.getGameById(game.getId());

        assertNull(deletedGame);

    }


}
