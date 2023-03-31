# Guess-The-Number

## Overview
In this project I wrote a REST server to facilitate playing a number guessing game known as "Bulls and Cows". In each game, a 4-digit number is generated where every digit is different. For each round, the user guesses a number and is told the exact and partial digit matches.
An exact match occurs when the user guesses the correct digit in the correct position. A partial match occurs when the user guesses the correct digit but in the wrong position. Once the number is guessed (exact matches for all digits) the user wins the game.

These are the current REST endpoints I created:

"begin" - POST – Starts a game, generates an answer, and sets the correct status. Returns a 201 CREATED message as well as the created gameId.
"guess" – POST – Makes a guess by passing the guess and gameId in as JSON. The program calculates the results of the guess and marks the game finished if the guess is correct. It returns the Round object with the results filled in.
"game" – GET – Returns a list of all games. In-progress games do not display their answer.
"game/{gameId}" - GET – Returns a specific game based on ID. In-progress games do not display their answer.
"rounds/{gameId} – GET – Returns a list of rounds for the specified game sorted by time.

I included a Service layer to manage the game rules, such as generating initial answers for a game and calculating the results of a guess.

I have also written tests for my public DAO interface methods.
