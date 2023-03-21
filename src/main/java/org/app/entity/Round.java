package org.app.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Round {
    private int id;
    private int gameid;
    private int guess;
    private boolean finished;

    private String roundTime;

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

    public String getRoundTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.roundTime = dtf.format(now);
        return this.roundTime;
    }


    public void setRoundTime(String roundTime) {
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
}
