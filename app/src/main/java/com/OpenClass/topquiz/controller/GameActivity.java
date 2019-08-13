package com.OpenClass.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.OpenClass.topquiz.R;
import com.OpenClass.topquiz.model.QuestionBank;
import com.OpenClass.topquiz.model.Question;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private TextView mQuestionText;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // link widget
        mQuestionText = findViewById(R.id.activity_game_question_text);
        mAnswer1Button = findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Button = findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Button = findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Button = findViewById(R.id.activity_game_answer4_btn);

        mQuestionBank = this.generateQuestions();
        mNumberOfQuestions = 3;
        mScore = 0;

        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);
        mAnswer1Button.setTag(0);
        mAnswer2Button.setTag(1);
        mAnswer3Button.setTag(2);
        mAnswer4Button.setTag(3);

        mQuestionBank = generateQuestions();
        mCurrentQuestion = mQuestionBank.getQuestion();
        displayQuestion(mCurrentQuestion);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        System.out.println(responseIndex+"     "+mCurrentQuestion.getAnswerIndex());
        if (responseIndex == mCurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        mNumberOfQuestions--;
        if(mNumberOfQuestions == 0){
            endGame();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }

    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question("Who shoot the sherif?", Arrays.asList("me","you","I","him"),2);
        Question question2 = new Question("Why?", Arrays.asList("Because","idk","42","69"),2);
        Question question3 = new Question("What Aypierre says ?", Arrays.asList("oups","yousk2","fuck","why not?"),0);
        Question question4 = new Question("How many emerauld Aypierre as farm?", Arrays.asList("12 469","1 000 000","694 242","666 666"),3);
        Question question5 = new Question("How Aypierre win?", Arrays.asList("With Villagers","With Watermelon","Pack Ultime","Glowstone"),2);
        Question question6 = new Question("What is the sound of the sub from Aypierre?", Arrays.asList("whip","oups!","ASMR","ass slap"),0);

        return new QuestionBank(Arrays.asList(question1,question2,question3,question4,question5,question6));
    }

    private void displayQuestion (final Question question) {
        List<String> answers = question.getChoiceList();
        mQuestionText.setText(question.getQuestion());
        mAnswer1Button.setText(answers.get(0));
        mAnswer2Button.setText(answers.get(1));
        mAnswer3Button.setText(answers.get(2));
        mAnswer4Button.setText(answers.get(3));
    }

}
