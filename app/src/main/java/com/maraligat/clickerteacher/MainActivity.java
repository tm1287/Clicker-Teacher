package com.maraligat.clickerteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button b_agree;
    private Button b_disagree;
    private Button b_reset;
    private Button b_update;

    private TextView tv_agree;
    private TextView tv_disagree;
    private TextView tv_question;

    private EditText et_question;

    private DatabaseReference dr_firebase;
    private DatabaseReference dr_agreeChild;
    private DatabaseReference dr_disagreeChild;
    private DatabaseReference dr_questionChild;

    private static Random mRandom = new Random();
    private int mAgree;
    private int mDisagree;
    private String mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        mAgree = 0;
        mDisagree = 0;
        mQuestion = "";

        b_agree = (Button)findViewById(R.id.agree_b);
        b_disagree = (Button)findViewById(R.id.disagree_b);
        b_reset = (Button)findViewById(R.id.reset_b);
        b_update = (Button)findViewById(R.id.update_b);

        tv_agree = (TextView)findViewById(R.id.agree_tv);
        tv_disagree = (TextView)findViewById(R.id.disagree_tv);
        tv_question = (TextView)findViewById(R.id.question_tv);

        et_question = (EditText)findViewById(R.id.question_et);

        dr_firebase = FirebaseDatabase.getInstance().getReference();
        dr_questionChild = dr_firebase.child("question");
        dr_agreeChild = dr_firebase.child("agree");
        dr_disagreeChild = dr_firebase.child("disagree");

        tv_question.setText("");

        b_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_agreeChild.setValue(++mAgree);
            }
        });

        b_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_disagreeChild.setValue(++mDisagree);
            }
        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr_agreeChild.setValue(0);
                dr_disagreeChild.setValue(0);
                dr_questionChild.setValue("");
            }
        });

        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_questionChild.setValue(et_question.getText().toString());
                et_question.setText("");
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        });

        dr_agreeChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAgree = dataSnapshot.getValue(Integer.class);
                tv_agree.setText(mAgree+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dr_disagreeChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDisagree = dataSnapshot.getValue(Integer.class);
                tv_disagree.setText(mDisagree+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dr_questionChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestion = dataSnapshot.getValue(String.class);
                tv_question.setText(mQuestion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}