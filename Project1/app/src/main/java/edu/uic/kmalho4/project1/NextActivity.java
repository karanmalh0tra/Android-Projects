package edu.uic.kmalho4.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NextActivity extends AppCompatActivity {
    protected EditText textField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        textField = (EditText) findViewById(R.id.editTextPersonName);
        textField.setOnEditorActionListener(textFieldActionListener);
    }

    private TextView.OnEditorActionListener textFieldActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event){

            //works with keyword enter and done/return key on android keyboard
            if(actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                Log.i("NextActivity","The text entered is: "+textField.getText());
                Intent i = new Intent();
                String name = String.valueOf(textField.getText());

                //if string is valid, bundle the name and flag and set result as RESULT_OK
                if (validateString(name)){
                    Log.i("NextActivity","Within the loop");
                    i.putExtra("contactName", name);
                    i.putExtra("Flag", "True");
                    setResult(RESULT_OK,i);
                }

                //checking if the name is invalid or was the back button pressed
                else {
                    if (name.length() > 0){
                        i.putExtra("contactName", name);
                    }
                    else {
                        i.putExtra("contactName", "");
                    }
                    i.putExtra("Flag", "False");
                    setResult(RESULT_CANCELED,i);

                }
                finish();
            }
            setResult(RESULT_CANCELED);
            return false;
        }
    };

    protected boolean validateString(String name){
        String trimmedName = name.trim();
        String nameWithProperSpacing = trimmedName.replaceAll("\\s+", " ");
        String[] nameArray = nameWithProperSpacing.split(" ");
        if (nameArray.length >= 2) {
            return name.matches("[a-z A-Z]+");
        }
        return false;
    }

    protected void onPause() {
        super.onPause();
        setResult(RESULT_OK);
    }
}