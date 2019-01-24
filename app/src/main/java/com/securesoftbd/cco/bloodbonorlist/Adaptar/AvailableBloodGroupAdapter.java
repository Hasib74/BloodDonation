package com.securesoftbd.cco.bloodbonorlist.Adaptar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;
import com.securesoftbd.cco.bloodbonorlist.ViewActivity.ViewActivity;

import java.util.ArrayList;
import java.util.List;

public class AvailableBloodGroupAdapter extends  RecyclerView.Adapter<AvailableBloodGroupAdapter.MyViewHolder>{

    private List<Donor> donorList = new ArrayList<>();
    private Context context;

    public AvailableBloodGroupAdapter(List<Donor> donorList, Context context)
    {
        this.donorList = donorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_available_blood_group_design,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Donor donor = donorList.get(i);
        myViewHolder.bloodGroupTV.setText(donor.getBloodGroup());

        myViewHolder.viewBloodBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context.startActivity(new Intent(context,ViewActivity.class).putExtra("BLOODGROUP",donor.getBloodGroup()).putExtra("ID",donor.getId()));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return donorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView bloodGroupTV;
        private Button viewBloodBTN;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            bloodGroupTV = itemView.findViewById(R.id.modelAvailableBloodGroupTVId);
            viewBloodBTN = itemView.findViewById(R.id.modelViewAvailableBTNId);

        }
    }
}