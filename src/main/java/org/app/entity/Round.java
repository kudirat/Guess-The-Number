package org.app.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Round implements Comparable<Round>{
    private int id;
    private int gameid;
    private int guess;
    private boolean finished;

    private LocalDateTime roundTime;

    private String guessResult;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getGameid() {
        return this.gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }


    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDateTime getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(LocalDateTime roundTime) {
        this.roundTime = roundTime;
    }

    public String getGuessResult() {
        return guessResult;
    }

    public void setGuessResult(String guessResult) {
        this.guessResult = guessResult;
    }

    @Override
    public boolean equals(Object o) {

        Round round = (Round) o;

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (id != round.id) return false;
        return Objects.equals(guess, round.guess);
    }

    @Override
    public int hashCode() {
        int result = id * 31;
        result = 31 * result + (gameid != 0 ? Objects.hashCode(gameid) : 0);
        result = 31 * result + (guess != 0 ? Objects.hashCode(guess) : 0);
        result = 31 * result + (Objects.hashCode(finished));

        return result;
    }

    @Override
    public int compareTo(Round round) {
        return getRoundTime().compareTo(round.getRoundTime());
    }
}
