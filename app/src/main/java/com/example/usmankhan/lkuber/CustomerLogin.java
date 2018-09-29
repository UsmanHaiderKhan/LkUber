package com.example.usmankhan.lkuber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerLogin extends AppCompatActivity {

    private EditText memail, mpassword;
    private Button mlogin, mregister;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mfireAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_customer_login );

        mAuth = FirebaseAuth.getInstance();
        mfireAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent( CustomerLogin.this, MapActivity.class );
                    startActivity( intent );
                    finish();
                    return;
                }
            }
        };

        memail = (EditText) findViewById( R.id.email );
        mpassword = (EditText) findViewById( R.id.password );

        mlogin = (Button) findViewById( R.id.login );
        mregister = (Button) findViewById( R.id.register );

        mregister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString();
                final String password = mpassword.getText().toString();
                mAuth.createUserWithEmailAndPassword( email, password )
                        .addOnCompleteListener( CustomerLogin.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText( CustomerLogin.this, "Sign up Error", Toast.LENGTH_SHORT ).show();

                                } else {
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference cUser = FirebaseDatabase.getInstance().getReference().child( "User" ).child( "Customers" ).child( user_id );
                                    cUser.setValue( true );
                                }
                            }
                        } );

            }
        } );
        mlogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString();
                final String password = mpassword.getText().toString();
                mAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( CustomerLogin.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                } );
            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener( mfireAuthListner );
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener( mfireAuthListner );
    }
}
