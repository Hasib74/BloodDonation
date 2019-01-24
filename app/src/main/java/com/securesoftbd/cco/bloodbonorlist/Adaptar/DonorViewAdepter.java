package com.securesoftbd.cco.bloodbonorlist.Adaptar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.MainActivity;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;
import com.securesoftbd.cco.bloodbonorlist.ViewActivity.ProfileViewActivity;

import java.util.ArrayList;
import java.util.List;

public class DonorViewAdepter extends RecyclerView.Adapter<DonorViewAdepter.MyViewHolder>
{

    private List<Donor> donorList = new ArrayList<>();
    private Context context;

    public DonorViewAdepter(List<Donor> donorList, Context context) {
        this.donorList = donorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.model_view_design,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i)
    {
        final Donor donor = donorList.get(i);

        myViewHolder.nameTV.setText(donor.getName());
        myViewHolder.bloodGroupTV.setText(donor.getBloodGroup());
        myViewHolder.profileIV.setImageBitmap(StringToBitMap(donor.getImage()));
        myViewHolder.viewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ProfileViewActivity.class).putExtra("ID",donor.getId()));
            }
        });
        myViewHolder.deleteDonorIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Delete Message !!");
                dialog.setMessage("Are you sure to delete it ?");


                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Quary quary = new Quary(context);
                        quary.deleteData(donor.getId());
                        donorList.remove(i);
                        notifyDataSetChanged();
                    dialog.dismiss();
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                dialog.show();

                //donorList = quary.ge
            }
        });

    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


    @Override
    public int getItemCount()
    {
        try {

        return donorList.size();
    }catch (Exception e)
    {

    }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView nameTV,bloodGroupTV;
        private Button viewBTN;
        private ImageView profileIV;
        private ImageButton deleteDonorIB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            nameTV = itemView.findViewById(R.id.modelNameShowTVId);
            bloodGroupTV = itemView.findViewById(R.id.modelBloodShowTVId);
            profileIV = itemView.findViewById(R.id.modelProfileShowIvId);
            viewBTN = itemView.findViewById(R.id.modelViewProfileBTNId);
            deleteDonorIB = itemView.findViewById(R.id.donorDeleteIBId);
        }
    }

}
