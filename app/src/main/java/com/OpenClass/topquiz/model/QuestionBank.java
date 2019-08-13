package com.OpenClass.topquiz.model;
/*
Created by Alexis Boutan on 11/08/2019 from the project : AndroidProjects
*/

import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList){
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mNextQuestionIndex = 0;
    }

    public Question getQuestion(){
        if (mNextQuestionIndex == mQuestionList.size()){
            mNextQuestionIndex = 0;
            return mQuestionList.get(mNextQuestionIndex);
        } else {
            return mQuestionList.get(mNextQuestionIndex++);
        }
    }

}
