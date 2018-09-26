package com.example.usmankhan.lkuber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
     private Button mdriver,mcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mdriver=(Button) findViewById( R.id.driver );
        mcustomer=(Button) findViewById( R.id.customer );

        mdriver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this,DriverLogin.class );
                startActivity( intent );
                finish();
                return;
            }
        } );

    }
}
