package model;

import org.app.TestApplicationConfiguration;
import org.app.entity.Game;
import org.app.entity.Round;
import org.app.model.GameDao;
import org.app.model.RoundDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoTest {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;

    @BeforeEach
    public void setUp() {
        //rounds will be deleted by association with the game id
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds) {
            roundDao.deleteRoundById(round.getGameid());
        }
    }


    @Test
    public void testAddGetRounds() {
        Round round = new Round();
        round.setId(1);
        round.setGuess(1234);
        LocalDateTime timenow = LocalDateTime.now();
        round.setRoundTime(timenow);
        round.setGuessResult("e:4:p:0");
        round.setFinished(true);
        roundDao.addRound(round);

        Round round2 = new Round();
        round.setId(3);
        round.setGuess(5678);
        LocalDateTime timenow2 = LocalDateTime.now();
        round.setRoundTime(timenow2);
        round.setGuessResult("e:4:p:0");
        round.setFinished(true);
        roundDao.addRound(round2);

        List<Round> currRounds = roundDao.getAllRounds();

        assertEquals(2, currRounds.size());
        assertTrue(currRounds.contains(round));
        assertTrue(currRounds.contains(round2));
    }

    @Test
    public void testGetRoundsByGameID() {
        Round round = new Round();
        round.setId(1);
        round.setGuess(1234);
        LocalDateTime timenow = LocalDateTime.now();
        round.setRoundTime(timenow);
        round.setGuessResult("e:4:p:0");
        round.setFinished(true);
        roundDao.addRound(round);

        Game game = gameDao.getGameById(round.getGameid());
        List<Round> currRounds = roundDao.getAllRoundForGame(round.getGameid());

        assertEquals(game.getId(), round.getGameid());

        for(Round currRound: currRounds){
            assertTrue(currRounds.contains(currRound));
        }
    }


}
