import org.app.TestApplicationConfiguration;
import org.app.entity.Game;
import org.app.model.GameDao;
import org.app.model.RoundDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @BeforeEach
    public void setUp() {
        //rounds will be deleted by association with the game id
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGameById(game.getId());
        }
    }


    @Test
    public void testAddGetGames() {
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

        List<Game> currGames = gameDao.getAllGames();

        assertEquals(2, currGames.size());
        assertTrue(currGames.contains(game));
        assertTrue(currGames.contains(game2));
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setId(1);
        game.setAnswer(1234);
        game.setStatus("In Progress");
        gameDao.addGame(game.getAnswer());

        Game gameFromDao = gameDao.getGameById(game.getId());

        assertEquals(game, gameFromDao);

        game.setStatus("Finished");

        gameDao.updateGameById(1);


        assertNotEquals(game, gameFromDao);

        gameFromDao = gameDao.getGameById(game.getId());

        assertEquals(game, gameFromDao);
    }

    @Test
    public void testDeleteGameByID() {
        Game game = new Game();
        game.setId(1);
        game.setAnswer(1234);
        game.setStatus("In Progress");
        gameDao.addGame(game.getAnswer());

        gameDao.deleteGameById(game.getId());

        Game gameFromDao = gameDao.getGameById(game.getId());
        assertNull(gameFromDao);
    }
}
