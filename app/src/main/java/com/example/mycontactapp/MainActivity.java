package com.example.mycontactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhoneNum;
    EditText editAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhoneNum = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);
        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: instantiated DatabaseHelper");

    }

    public void addData(View view ){

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhoneNum.getText().toString(), editAddress.getText().toString());

        if(isInserted == true){
            Toast.makeText(MainActivity.this,"Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void viewData(View view){
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            //append res column 0, ... to the buffer - see StringBuffer and Cursor api's
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Phone Number: " + res.getString(2) + "\n");
            buffer.append("Address: " + res.getString(3) + "\n");
            buffer.append("\n\n");
        }

        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message){
        //put Log.d's in here
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}
