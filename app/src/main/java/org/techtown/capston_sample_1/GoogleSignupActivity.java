package org.techtown.capston_sample_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleSignupActivity extends AppCompatActivity {

    private static final String TAG = "<<GoogleSignupActivity>>";

    String id;
    String age;
    String sex;
    String artist;

    EditText editTextSignupId;
    EditText editTextSignupAge;
    Spinner spinnerSex;
    Spinner spinnerArtist;

    Button buttonCancel;
    Button buttonSign;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Text2Drawing");

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSign = findViewById(R.id.buttonSignG);

        editTextSignupId = findViewById(R.id.editTextSignupIdG);
        editTextSignupAge = findViewById(R.id.editTextSignupAgeG);

        spinnerSex = findViewById(R.id.spinnerSexG);
        ArrayAdapter<CharSequence> spinnerSex_adapter = ArrayAdapter.createFromResource(this, R.array.resourcesSex, android.R.layout.simple_spinner_item);
        spinnerSex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(spinnerSex_adapter);

        spinnerArtist = findViewById(R.id.spinnerArtistG);
        ArrayAdapter<CharSequence> spinnerArtist_adapter = ArrayAdapter.createFromResource(this, R.array.resourcesArtist, android.R.layout.simple_spinner_item);
        spinnerArtist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArtist.setAdapter(spinnerArtist_adapter);

        // ???????????? ????????? ????????????
        Intent getIdIntent = getIntent();
        editTextSignupId.setText(getIdIntent.getStringExtra("emailId"));
        editTextSignupId.setEnabled(false);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        spinnerSex.setSelection(0);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerArtist.setSelection(0);
        spinnerArtist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                artist = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = editTextSignupId.getText().toString();
                age = editTextSignupAge.getText().toString();

                if(age.length() == 0){
                    Toast.makeText(getApplicationContext(),"????????? ??????????????????",Toast.LENGTH_SHORT).show();
                } else{
                    // ???????????? ?????????????????? ??????
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    User user = new User();
                    user.setUid(firebaseUser.getUid());
                    user.setEmailId(firebaseUser.getEmail());
                    user.setAge(age);
                    user.setSex(sex);
                    user.setArtist(artist);
                    databaseReference.child("User").child(firebaseUser.getUid()).setValue(user);

                    Toast.makeText(getApplicationContext(),"??????????????? ??????????????????",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }
}