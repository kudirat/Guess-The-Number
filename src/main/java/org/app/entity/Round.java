package org.app.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Round {
    private int id;
    private int gameid;
    private int guess;
    private boolean finished;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Round round = (Round) o;

        if (id != round.id) return false;
        return Objects.equals(guess, round.guess);
    }

    @Override
    public int hashCode() {
        int result = id * 31;
        //result = 31 * result + (answer != 0 ? answer.hashCode() : 0);
        return result;
    }
}
