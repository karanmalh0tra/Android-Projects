package edu.uic.kmalho4.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button button1;
    protected Button button2;
    protected String name;
    protected String flag = "False"; //flag is used to maintain states between the two activities
    protected int result_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding the elements to the fields
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(button1Listener);
        button2.setOnClickListener(button2Listener);
        Log.i("MainActivity","Main Activity Created State");
    }

    //Listener for Button 1
    private final View.OnClickListener button1Listener = v -> switchToNextActivity();

    //Listener for Button 2
    private final View.OnClickListener button2Listener = v -> insertContact(name);

    public void insertContact(String name){
        //result code -1 comes on RESULT_OK
        if(result_code == -1){
            Intent addANewContact = new Intent(Intent.ACTION_INSERT);
            addANewContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
            addANewContact.putExtra(ContactsContract.Intents.Insert.NAME, name);
            startActivity(addANewContact);
        }
        //RESULT_CODE is 0 during an invalid name or when the user presses the back button

        //Here, the condition is checking for incorrect name
        else if (result_code == 0 && flag.equals("False") && name != null && name.length() >0){
            Context context = getApplicationContext();
            CharSequence text = "Invalid name entered! "+name;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        //here the condition is checking when the user presses the back button directly
        else if (result_code == 0){
            Context context = getApplicationContext();
            CharSequence text = "No name was entered!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        Log.i("MainActivity","onSaveInstanceState called");
        savedInstanceState.putString("contactName",name);
        savedInstanceState.putString("Flag",flag);
        savedInstanceState.putInt("result_code",result_code);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            Log.i("MainActivity","onRestoreInstanceState called");
            name = savedInstanceState.getString("contactName");
            flag = savedInstanceState.getString("Flag");
            result_code = savedInstanceState.getInt("result_code");
            Log.i("MainActivity", "onRestoreInstanceState called with name as " + name);
        }
    }

    private void switchToNextActivity(){
        Intent i = new Intent(MainActivity.this,NextActivity.class);
        startActivityForResult(i,200);
    }

    protected void onActivityResult(int code, int result_code, Intent i) {
        super.onActivityResult(code, result_code, i);
        this.result_code = result_code;
        //result code -1 if RESULT_OK and 0 if RESULT_CANCELLED
        if (code == 200){
            Log.i("MainActivity: ", "Returned result is: " + result_code) ;

            if (i != null){
                Bundle contactData;
                contactData = i.getExtras();
                name = (String) contactData.get("contactName");
                flag = (String) contactData.get("Flag");
                Log.i("MainActivity","Received String "+name+" from NextActivity and visited= "+flag);
            }
            else{
                name = "";
                flag = "False";
                Log.i("MainActivity","Instance received was null");
            }
        }
    }


}