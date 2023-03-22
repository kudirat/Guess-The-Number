package org.app.model;

import org.app.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public interface RoundDao {


   Round addRound(Round round);

   List<Round> getAllRounds();

   List<Round> getAllRoundForGame(int gameId);

   void deleteRoundById(int roundId);


}
