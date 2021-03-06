package com.labcnt.sttq.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.labcnt.sttq.R;
import com.labcnt.sttq.Model.StructureModel;
import com.labcnt.sttq.Toggle.StructureToggleButton;

import java.util.ArrayList;

public class StructureAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<StructureModel> mQuestionList;


    public StructureAdapter(Context context, ArrayList<StructureModel> questionList) {
        this.mContext = context;
        this.mQuestionList = questionList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View questionView = LayoutInflater.from(mContext)
                .inflate(R.layout.structure_card_layout, parent, false);
        return new QuestionViewHolder(questionView);
    }

    private RadioButton lastCheckedRB = null;

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final StructureModel question = mQuestionList.get(position);
        final QuestionViewHolder questionViewHolder = (QuestionViewHolder) holder;
        questionViewHolder.mQuestion.setText(question.getQuestion());
        questionViewHolder.mRb1.setText(question.getOption1());
        questionViewHolder.mRb2.setText(question.getOption2());
        questionViewHolder.mRb3.setText(question.getOption3());
        questionViewHolder.mRb4.setText(question.getOption4());


        ArrayList<RadioButton> radioButtons = questionViewHolder.mTableLayout.getChildren();
        for (final RadioButton rb : radioButtons) {
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionViewHolder.mTableLayout.checkAnswer(rb, question.getCorrectAnsNo(), mContext);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    //tambahan coba-coba
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView mQuestion;
        RadioButton mRb1;
        RadioButton mRb2;
        RadioButton mRb3;
        RadioButton mRb4;

        StructureToggleButton mTableLayout;

        QuestionViewHolder(View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.question);

            mRb1 = itemView.findViewById(R.id.rb1);
            mRb2 = itemView.findViewById(R.id.rb2);
            mRb3 = itemView.findViewById(R.id.rb3);
            mRb4 = itemView.findViewById(R.id.rb4);

            mTableLayout = itemView.findViewById(R.id.table_layout);
        }
    }

}
