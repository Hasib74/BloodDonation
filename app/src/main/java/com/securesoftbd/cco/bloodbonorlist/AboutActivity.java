package com.securesoftbd.cco.bloodbonorlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView activity_1,activity_1_details,activity_2,activity_2_details,activity_3,activity_3_details
            ,activity_4,activity_4_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();


        activity_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_1_details.setVisibility(View.VISIBLE);
                activity_2_details.setVisibility(View.GONE);
                activity_3_details.setVisibility(View.GONE);
                activity_4_details.setVisibility(View.GONE);
            }
        });

        activity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_1_details.setVisibility(View.GONE);
                activity_2_details.setVisibility(View.VISIBLE);
                activity_3_details.setVisibility(View.GONE);
                activity_4_details.setVisibility(View.GONE);

            }
        });

        activity_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_1_details.setVisibility(View.GONE);
                activity_2_details.setVisibility(View.GONE);
                activity_3_details.setVisibility(View.VISIBLE);
                activity_4_details.setVisibility(View.GONE);
            }
        });

        activity_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_1_details.setVisibility(View.GONE);
                activity_2_details.setVisibility(View.GONE);
                activity_3_details.setVisibility(View.GONE);
                activity_4_details.setVisibility(View.VISIBLE);
            }
        });

        }

    private void init()
    {
        activity_1 = findViewById(R.id.activity_1);
        activity_1_details = findViewById(R.id.activity_1_details);
        activity_2 = findViewById(R.id.activity_2);
        activity_2_details = findViewById(R.id.activity_2_details);
        activity_3 = findViewById(R.id.activity_3);
        activity_3_details = findViewById(R.id.activity_3_details);
        activity_4 = findViewById(R.id.activity_4);
        activity_4_details = findViewById(R.id.activity_4_details);
    }
}
