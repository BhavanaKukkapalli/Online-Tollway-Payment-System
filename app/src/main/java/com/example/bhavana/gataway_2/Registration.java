package com.example.bhavana.gataway_2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;

import java.net.PasswordAuthentication;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText name,username;
    EditText email,phno;
    EditText pin,cpin;
    Button register,view;
    String emailid,phoneno;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);//maps xml file


        name=(EditText)findViewById(R.id.Name);
        username=(EditText)findViewById(R.id.Username);
        email=(EditText)findViewById(R.id.Email_Id);
        phno=(EditText)findViewById(R.id.Mobile);
        pin=(EditText)findViewById(R.id.Pin);
        cpin=(EditText)findViewById(R.id.ConfirmPin);
        register=(Button)findViewById(R.id.Register);
        register.setOnClickListener(this);
        view=findViewById(R.id.viewall);

        db = openOrCreateDatabase("Registration", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Registration(name VARCHAR,username VARCHAR,pin VARCHAR,phone VARCHAR,mail_id VARCHAR);");
        view.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==view.getId()) {
            Toast.makeText(this,"displaying",Toast.LENGTH_LONG).show();
            Cursor c = db.rawQuery("SELECT * FROM Registration", null);
            // Checking if no records found 
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            // Appending records to a string buffer 
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Name: " + c.getString(0) + "\n");
                buffer.append("Username: " + c.getString(1) + "\n");
                buffer.append("pin: " + c.getString(2) + "\n");
                buffer.append("Phone no: " + c.getString(3) + "\n");
                buffer.append("Email: " + c.getString(4) + "\n\n\n");


            }
            // Displaying all records 
            showMessage("Student Details", buffer.toString());
    }
    else{

        emailid = email.getText().toString().trim();
        phoneno = phno.getText().toString().trim();

        if (name.getText().toString().trim().length() == 0 || username.getText().toString().trim().length() == 0 ||
                pin.getText().toString().trim().length() == 0 || phno.getText().toString().trim().length() == 0 || email.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }

        if (emailid.matches(emailpattern) && (phoneno.length() == 10)) {
            email.setTextColor(Color.parseColor("#000000"));
            phno.setTextColor(Color.parseColor("#000000"));
            db.execSQL("INSERT INTO Registration VALUES('" + name.getText() + "','" + username.getText() +
                    "','" + pin.getText() + "','" + phno.getText() + "','" + email.getText() + "');");
            showMessage("Success", "Registration Successful");
            clearText();
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show();
        } else {
            if (!(emailid.matches(emailpattern))) {
                email.setTextColor(Color.parseColor("#ff0000"));
                Toast.makeText(this, "Email format is incorrect", Toast.LENGTH_LONG).show();

            } else if (phoneno.length() != 10) {
                phno.setTextColor(Color.parseColor("#ff0000"));
                Toast.makeText(this, "phone no is incorrect", Toast.LENGTH_LONG).show();
            }
        }
        }
    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText(){

        name.setText("");
        username.setText("");
        pin.setText("");
        phno.setText("");
        cpin.setText("");
        email.setText("");

    }

}
