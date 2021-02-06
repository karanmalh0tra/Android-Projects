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
    protected int result_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding the elements to the fields
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(button1Listener) ;
        Log.i("MainActivity","Main Activity Created State");
        Intent i = getIntent();
        savedInstanceState = i.getExtras();
        if (savedInstanceState != null){
            name = (String) savedInstanceState.get("contactName");
            Log.i("MainActivity","Received String "+name+" from NextActivity");
            button2.setOnClickListener(button2Listener) ;
        }
    }

    private View.OnClickListener button1Listener = v -> switchToNextActivity();

    private View.OnClickListener button2Listener = v -> insertContact(name);

    public void insertContact(String name){
        if(result_code == -1){
            Intent addANewContact = new Intent(Intent.ACTION_INSERT);
            addANewContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
            addANewContact.putExtra(ContactsContract.Intents.Insert.NAME, name);
            startActivity(addANewContact);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Invalid name entered!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }


    private void switchToNextActivity(){
        Intent i = new Intent(MainActivity.this,NextActivity.class);
        startActivityForResult(i,99);
    }

    protected void onActivityResult(int code, int result_code, Intent i) {
        super.onActivityResult(code, result_code, i);
        this.result_code = result_code;
        //result code -1 if true and 0 if false
        if (i != null){
            Log.i("MainActivity: ", "Returned result is: " + result_code) ;
            Log.i("MainActivity: ", "My result code returned " + code);
            Bundle contactData = new Bundle();
            contactData = i.getExtras();
            name = (String) contactData.get("contactName");
            Log.i("MainActivity","Received String "+name+" from NextActivity");
            if (result_code == -1){
                Log.i("MainActivity","inside the result code == -1 loop");
            }
            activateButton2();
//            finish();
        }

    }
    private void activateButton2(){
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(button2Listener) ;
    }

}