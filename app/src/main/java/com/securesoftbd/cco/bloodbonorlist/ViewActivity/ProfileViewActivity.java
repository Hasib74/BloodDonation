package com.securesoftbd.cco.bloodbonorlist.ViewActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.securesoftbd.cco.bloodbonorlist.AddActivity.AddDonorActivity;
import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileViewActivity extends AppCompatActivity {

    private TextView nameTV, ageTV, bloodTV, contractTV, lastDateofDonationTV, addressTV;
    private Button editProfileBTN, callBTN;
    private List<Donor> donor;
    private Quary quary;
    private ImageView donorIV,donorProfileIV;
    private int CAMERA_REQUEAT_CODE = 10;
    private int GALLERY_REQUEST_CODE = 20;
    private DatePickerDialog datePickerDialog;
    private Bitmap donorImage = null;
    private String donorImageString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        inti();
        dataSet();
        onClick();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataSet();
    }

    private void dataSet() {
        String user = getIntent().getStringExtra("ID");

        donor = quary.getUser(user);
        nameTV.setText(donor.get(0).getName());
        ageTV.setText(donor.get(0).getAge());
        bloodTV.setText(donor.get(0).getBloodGroup());
        contractTV.setText(donor.get(0).getCountractNumber());
        lastDateofDonationTV.setText(donor.get(0).getLastDonationDate());
        addressTV.setText(donor.get(0).getAddress());
        donorProfileIV.setImageBitmap(StringToBitMap(donor.get(0).getImage()));

    }

    private void onClick()
    {
        editProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateProfile();

            }
        });

        callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + donor.get(0).getCountractNumber()));

                if (ActivityCompat.checkSelfPermission(ProfileViewActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });
    }

    private void UpdateProfile()
    {
        final BottomSheetDialog dialog = new BottomSheetDialog(ProfileViewActivity.this);
        View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.activity_add_donor, null, false);
        dialog.setContentView(imageCaptureDialogView);
        dialog.show();

        final EditText nameET, ageET, contractNumberET, addressET;
        final TextView bloodGroup, lastDonationDateTV;
        Spinner bloodGroupSPN;
        final Button doneBTN;


        final Donor d = donor.get(0);

        bloodGroupSPN = imageCaptureDialogView.findViewById(R.id.spinnerId);
        bloodGroupSPN.setVisibility(View.INVISIBLE);
        nameET = imageCaptureDialogView.findViewById(R.id.nameETId);
        nameET.setText(d.getName());
        ageET = imageCaptureDialogView.findViewById(R.id.ageETId);
        ageET.setText(d.getAge());
        contractNumberET = imageCaptureDialogView.findViewById(R.id.contractETId);
        contractNumberET.setText(d.getCountractNumber());
        addressET = imageCaptureDialogView.findViewById(R.id.addressETId);
        addressET.setText(d.getAddress());
        lastDonationDateTV = imageCaptureDialogView.findViewById(R.id.lastDonatedateTVId);
        lastDonationDateTV.setText(d.getLastDonationDate());


        doneBTN = imageCaptureDialogView.findViewById(R.id.doneBTNId);
        donorIV = imageCaptureDialogView.findViewById(R.id.donorImageId);
        donorIV.setImageBitmap(StringToBitMap(d.getImage()));
        bloodGroup = imageCaptureDialogView.findViewById(R.id.textView3);
        bloodGroup.setText(bloodGroup.getText() + "  " + d.getBloodGroup());
        doneBTN.setText("Confirm");


        lastDonationDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar calendar =Calendar.getInstance();
                int Year = calendar.get(Calendar.YEAR);
                int Month= calendar.get(Calendar.MONTH);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(ProfileViewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        lastDonationDateTV.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    }
                },Year,Month,Day);

                datePickerDialog.show();
            }
        });


        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, age, contractNumber, address, lastDonationDate, bloodGroupString;
                name = nameET.getText().toString();
                age = ageET.getText().toString();
                contractNumber = contractNumberET.getText().toString();
                address = addressET.getText().toString();
                lastDonationDate = lastDonationDateTV.getText().toString();
                bloodGroupString = d.getBloodGroup();


                if (name.isEmpty() || age.isEmpty() || contractNumber.isEmpty() || address.isEmpty() ||
                        lastDonationDate.isEmpty() || bloodGroupString.isEmpty()) {
                    Alertdialog("Error !! ", "Blank the fild..");

                } else {
                    //Donor donorUpdate = new Donor(name,age,donorImageString,bloodGroup,contractNumber,lastDonationDate,address);
                   if(donorImageString != null)
                   {
                       d.setImage(donorImageString);
                   }

                    Donor donorUpdate = new Donor(d.getId(), name, age, d.getImage(), bloodGroupString, contractNumber, lastDonationDate, address);
                    quary.UpdateData(donorUpdate);
                    Alertdialog("Update", "Update Data Successfully.");
                    dataSet();
                    dialog.dismiss();

                }
            }
        });

        donorIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CameraGalleryImageCapture();
            }
        });

    }

    private void inti() {
        quary = new Quary(this);
        donor = new ArrayList<>();
        editProfileBTN = findViewById(R.id.editProfileId);
        callBTN = findViewById(R.id.callDonorId);
        nameTV = findViewById(R.id.nameShowTVId);
        ageTV = findViewById(R.id.ageShowTVId);
        bloodTV = findViewById(R.id.bloodShowTVId);
        contractTV = findViewById(R.id.contractNumberShowTVId);
        lastDateofDonationTV = findViewById(R.id.lastDonatedateShowTVId);
        addressTV = findViewById(R.id.addressShowTVId);
        donorProfileIV = findViewById(R.id.donorImage);
    }

    public void Alertdialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileViewActivity.this);
        dialog.setTitle(title);
        dialog.setMessage(message);

        //dialog.show();

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 1500);
    }

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void CameraGalleryImageCapture()
    {
      /*  AlertDialog.Builder imageCaptureDialog = new AlertDialog.Builder(AddDonorActivity.this);
        final AlertDialog imageDialog = imageCaptureDialog.create();*/

        //  LinearLayout layoutBottomSheet;
        // final TextView btnBottomSheet = view.findViewById(R.id.btnBottomSheet);
        //BottomSheetBehavior sheetBehavior;

        //View view1 = getLayoutInflater().inflate(R.layout., null);
        View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.view_alertdialog_camera_gallary_design,null,false);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(imageCaptureDialogView);
        dialog.show();

        //imageCaptureDialog.setView(imageCaptureDialogView);




        //imageDialog.show();

        ImageView camera,gallery;

        camera = imageCaptureDialogView.findViewById(R.id.getCameraImageId);
        gallery = imageCaptureDialogView.findViewById(R.id.getGalleryImageId);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEAT_CODE);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {
             donorImage = (Bitmap) data.getExtras().get("data");
            donorIV.setImageBitmap(donorImage);
            donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
        }
        else if(requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {

            Uri imgaeUri = data.getData();

            try {
                InputStream imageStream = getContentResolver().openInputStream(imgaeUri);
                donorImage  = BitmapFactory.decodeStream(imageStream);
                donorIV.setImageBitmap(donorImage);
                donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
                //setImageVar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

    }
}
