package org.app.view;

import org.app.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Component
public class GameView {

    @Autowired
    UserIO io;

    @Autowired
    GameController controller;

//    public void run(){
//        printWelcomeBanner();
//        //start game
//        controller.beginGame(); ///done
//        //get player guess
//
//
//
//    }


//    public void printWelcomeBanner() {
//        io.print("==== Bulls and Cows ====");
//    }
//
//    public void giveGuess(){
//        int numGuess = io.readInt("Give your guess.");
//    }

}
