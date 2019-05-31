package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Intent;
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
import com.example.mycontactapp.DatabaseHelper;



public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editAddress;
    EditText editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editNumber = findViewById(R.id.editNumber);
        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: instantiated DatabaseHelper");

    }

    public void addData(View view){

        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editNumber.getText().toString());

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
            buffer.append("Address: " + res.getString(2) + "\n");
            buffer.append("Number: " + res.getString(3) + "\n");
            buffer.append("\n\n");
        }
        Log.d("MyContactApp", "MainActivity: viewData: assembled string buffer");
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

    public static final String EXTRA_NAME = "com.example.mycontactapp.MESSAGE";

    public void SearchRecord(View view) {

        Log.d("MyContactApp", "MainActivity:launching searchActivity");
        Cursor res = myDb.getAllData();
        Intent intent = new Intent(this, SearchActivity.class);

        StringBuffer buffer = new StringBuffer();
        int ITT = res.getCount();
        int x  = 0;
        while (res.moveToNext()) {
            if (res.getString(1).equals(editName.getText().toString())) {
                buffer.append("ID: " + res.getString(0));
                buffer.append("\n");
                buffer.append("Name: " + res.getString(1));
                buffer.append("\n");
                buffer.append("Address: " + res.getString(2));
                buffer.append("\n");
                buffer.append("Number: " + res.getString(3));
                buffer.append("\n");
            }
            else{
                x++;
            }

        }
        if(x == ITT) {
            buffer.append("No contact found");
        }
        intent.putExtra(EXTRA_MESSAGE, buffer.toString());
        startActivity(intent);
    }

}

