package com.example.bhavana.gataway_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginPage extends AppCompatActivity implements View.OnClickListener{
    EditText ed1;
    EditText ed2;
    SQLiteDatabase db;
    CheckBox c,p;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ed1=(EditText)findViewById(R.id.edittext1);
        ed2=(EditText)findViewById(R.id.edittext2);
        c=(CheckBox)findViewById(R.id.remember);
        b1=(Button)findViewById(R.id.login);
        b2=(Button)findViewById(R.id.signup);
        db = openOrCreateDatabase("Registration", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Registration(name VARCHAR,username VARCHAR,pin VARCHAR,phone VARCHAR,mail_id VARCHAR);");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dummyPassword;
                Cursor c = db.rawQuery("SELECT * FROM Registration WHERE username='" + ed1.getText() + "'", null);

                if (c.moveToFirst()){
                    String dum = c.getString(1);
                    dummyPassword = c.getString(2);
                    //Toast.makeText(getApplicationContext(), dummyPassword, Toast.LENGTH_SHORT).show();

                    if (ed2.getText().toString().equals(dummyPassword)) {
                        Toast.makeText(getApplicationContext(), "Welcome "+dum, Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(LoginPage.this,MapsActivity.class);
                        startActivity(i);
                        //Intent in = new Intent(this, intent.class);
                        //i.putExtra("username", usernameLogin.getText().toString());
                        //startActivity(in);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"invalid credentials",Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"User not registered",Toast.LENGTH_LONG).show();
                }
            }


        });
        b2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginPage.this,"redirecting....!!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(LoginPage.this,Registration.class);
                startActivity(i);
            }
        }));
    }



    @Override
    public void onClick(View v) {

    }

    public void savedata(View view) {
        SharedPreferences sp=getSharedPreferences("MainActivity",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("username",ed1.getText().toString());
        edit.putString("password",ed2.getText().toString());
        edit.commit();
        Toast.makeText(this,"data saved",Toast.LENGTH_LONG).show();

        getdata(view);

    }
    public void getdata(View view){
        Toast.makeText(this,"data retrieving",Toast.LENGTH_LONG).show();
        SharedPreferences sp=getSharedPreferences("MainActivity",Context.MODE_PRIVATE);
        ed1.setText(sp.getString("username","NA"));
        ed2.setText(sp.getString("password","NA"));

    }
}