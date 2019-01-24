package com.securesoftbd.cco.bloodbonorlist;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.securesoftbd.cco.bloodbonorlist.AddActivity.AddDonorActivity;
import com.securesoftbd.cco.bloodbonorlist.AvailableActivity.AvailableDonorActivity;
import com.securesoftbd.cco.bloodbonorlist.ViewActivity.ProfileViewActivity;
import com.securesoftbd.cco.bloodbonorlist.ViewActivity.ViewActivity;

public class MainActivity extends AppCompatActivity
{

    private Button viewBtn,addBtn,availableBtn,aboutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onClick();
    }

    private void onClick()
    {
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewActivity.class));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddDonorActivity.class));

            }
        });

        availableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,AvailableDonorActivity.class));
            }
        });
        aboutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.about_layout, null, false);
                dialog.setContentView(imageCaptureDialogView);
                dialog.show();*/
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
        });
    }

    private void init()
    {
        aboutBTN = findViewById(R.id.aboutBTNId);
        viewBtn = findViewById(R.id.viewBTNId);
        addBtn = findViewById(R.id.addBTNId);
        availableBtn = findViewById(R.id.avalibleBTNId);
    }
}
