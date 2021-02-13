package com.example.app;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.entered);



        final Intent intent = new Intent(this, MainActivity2.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int first = Integer.parseInt("3");
                    int second = Integer.parseInt("3");
                        intent.putExtra("firstnumber",String.valueOf(first));
                        intent.putExtra("secondnumber",String.valueOf(second));
                        startActivity(intent);

                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "There is something wrong"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}