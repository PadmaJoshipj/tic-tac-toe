package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int playerOnceScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPosition = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7}, {2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i<buttons.length; i++){
            String buttonId = "btn_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);

        }
        rountCount = 0;
        playerOnceScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        //Log.i("test","Button is Clicked");
        if (!((Button) view).getText().equals("")) {
            return;
        }
        String buttonId = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1, buttonId.length()));

        if (activePlayer) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#ECE492"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#88B876"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;
        if (checkwinner()) {
            if(activePlayer){
                playerOnceScoreCount ++;
                updatePlayerscore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playagain();

            }else {
                playerTwoScoreCount ++;
                updatePlayerscore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playagain();
            }

        } else if (rountCount == 9) {
            playagain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();


        }else {
            activePlayer=!activePlayer;
        }
        if (playerOnceScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is Winning");
        }else if (playerTwoScoreCount>playerOnceScoreCount){
            playerStatus.setText("Player Two is Winnning");
        }else  {
            playerStatus.setText("All The Best");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playagain();
                playerOnceScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerscore();
            }
        });
    }
    public boolean checkwinner (){
        boolean winnerresult = false;

        for (int [] win : winningPosition) {
            if (gameState[win[0]] == gameState[win[1]]
                    && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] !=2){
                winnerresult = true;

            }
        }
        return winnerresult;
    }
    public void updatePlayerscore(){
        playerOneScore.setText(Integer.toString(playerOnceScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playagain (){
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}