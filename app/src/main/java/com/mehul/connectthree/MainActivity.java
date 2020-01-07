package com.mehul.connectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //variable to track if the game is active
    boolean gameIsActive = true;

    // 0 = yellow, 1 = red
    int activePlayer = 0;

    //memory to manage the state of the game. There are nine 2's in the array for each space which are unplayed
    //array values doesn't have to be 2 can be any number but for this exercise I used the number 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    //array inside an array to store all the winning positions/possibilities/combinations corresponding with the tags
    int [][] winningPositions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}};

    //method runs when user taps on an empty space
    public void dropIn(View v){
        //get the image/empty space that was tapped on
        ImageView counter = (ImageView) v;

        //gets the tag of the space that was clicked as a string and then converts to integer and assigns it to an int variable
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        //check whether space has been tapped on and comparing it with the gameState and game is active
        //gameState[tappedCounter] == 2 is checking if array value of tappedCounter index is 2 and if it is 2 then the space is unplayed and playable
        if(gameState[tappedCounter] == 2 && gameIsActive) {
            //array value is changed to the current activePlayer value (0 or 1) to show which player has played for that space
            gameState[tappedCounter] = activePlayer;
            //move that image/empty space 1000 pixels up off the screen
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                //set the image/empty space to yellow.png
                counter.setImageResource(r.drawable.yellow);
                activePlayer = 1;
            } else if (activePlayer == 1) {
                //set the image/empty space to red.png
                counter.setImageResource(r.drawable.red);
                activePlayer = 0;
            }

            //for each loop runs for how ever many array values there are for winningPositions
            //winningPosition is a single dimension array but winningPositions is a two dimensional array
            //on every run of the loop will check the index of the two dimensional winningPositions array
            for(int[] winningPosition : winningPositions){
                //conditions set to check if the gameState index of the winningPosition index value is occupied by the same player
                //if the gameState index of the winningPosition index value is 2 then condition is not met
                //basically taking [currentrun][0] and [currentrun][1] and [currentrun][2] and
                //putting them into the gameState index to see if that space is held by the same player
                //in other words, checking if gameState[winningPosition[currentrun][0]] is the same as
                //gameState[winningPosition[currentrun][1]] and the same as gameState[winningPosition[currentrun][2]]
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    //stops the game since game has won
                    gameIsActive = false;

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    if(gameState[winningPosition[0]] == 0){
                        winnerMessage.setText("Yellow has won!");
                    } else {
                        winnerMessage.setText("Red has won!");
                    }

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;
                    for(int counterState : gameState) {
                        if (counterState == 2){
                            gameIsOver = false;
                        }else if(gameIsOver){
                            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                            winnerMessage.setText("It's a draw.");
                            LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }

        //move or animate down the image 1000pixels in 300 milliseconds
        counter.animate().translationYBy(1000f).setDuration(300);
    }

    public void playAgain(){
        //setting status of the game back to true as default to allow game restart fresh
        gameIsActive =  true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

        //removes the post game message
        layout.setVisibility(View.INVISIBLE);

        //resetting the active player variable back to 0 as default
        activePlayer = 0;

        //resetting gameState to default back to 2
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //resets the image for the tiles played by removing the image
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
