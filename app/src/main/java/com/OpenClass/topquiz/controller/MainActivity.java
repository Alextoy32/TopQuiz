package com.OpenClass.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.OpenClass.topquiz.R;
import com.OpenClass.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            assert data != null;
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putInt("lastScore",score).apply();
            checkLastGame();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingText = findViewById(R.id.activity_main_greeting_text);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mUser = new User();
        checkLastGame();

        //activate button if a name is entered
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //button listener
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setFirstName(mNameInput.getText().toString());
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putString("firstname",mUser.getFirstName()).apply();
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });




    }

    public void checkLastGame(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String firstName = preferences.getString("firstname",null);
        int lastScore = preferences.getInt("lastScore", -1);
        if (firstName != null && lastScore >= 0){
            mNameInput.setText(firstName);
            mNameInput.setSelection(firstName.length());
            firstName = "Welcome back "+firstName+" !\nYour last score was "+ lastScore +", will you do better this time?";
            mPlayButton.setEnabled(true);
        } else {
            firstName = "Welcome in TopQuiz! What\\'s your name ?";
            mPlayButton.setEnabled(false);
        }
        mGreetingText.setText(firstName);
    }

}
