package com.example.bhavana.gataway_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    Spinner spinner;
    EditText ed1;
    EditText ed2;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    Button b1;
    Button b2;
    Integer amount, no_tg;
    RadioGroup rg;
    RadioButton subRadioButton;
    RadioButton rb1;
    RadioButton rb2;
    String item;
    int tollnumber;
    Button emer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        spinner = (Spinner) findViewById(R.id.spinner);
        ed1 = (EditText) findViewById(R.id.edittext1);
        ed2 = (EditText) findViewById(R.id.edittext2);
        tv1 = (TextView) findViewById(R.id.textview2);
        tv2 = (TextView) findViewById(R.id.textview3);
        tv3 = (TextView) findViewById(R.id.textview4);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        rb1 = (RadioButton) findViewById(R.id.radiobutton1);
        rb2 = (RadioButton) findViewById(R.id.radiobutton2);
        emer = (Button) findViewById(R.id.button3);
        Bundle b = getIntent().getExtras();
        tollnumber = b.getInt("tollno");
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("car");
        categories.add("truck");
        categories.add("bus");
        categories.add("heavy vehicle");
        categories.add("two wheeler");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Payment.this, Proceed_to_payment.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String vnumber = ed1.getText().toString();
                final String v_type = item;
                int selectedId = rg.getCheckedRadioButtonId();
                subRadioButton = (RadioButton) findViewById(selectedId);
                final String v_trip = subRadioButton.getText().toString();
                no_tg = 4;
                msg_fill signup = new msg_fill(v_trip, v_type);

                myRef.child("vehicle_info").child(vnumber).setValue(signup).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w("done", "yes");
                        tv1.setText(vnumber);
                        tv2.setText(v_type);

                    }
                });
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Integer value = dataSnapshot.child("fareandtype").child(v_type).child(v_trip).getValue(Integer.class);
                        amount = tollnumber * value;
                        tv3.setText(amount.toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = "112";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + ph));

                // The below if condition will be auto suggessted if not given.
                if (ActivityCompat.checkSelfPermission(Payment.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                    Toast.makeText(Payment.this, "Require Call permission to make calls", Toast.LENGTH_SHORT).show();
                    // add the following line for runtime permission request
                    ActivityCompat.requestPermissions(Payment.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            123);
                    return;
                }
                startActivity(intent);

            }


        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {

    }
}
