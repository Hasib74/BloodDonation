package com.securesoftbd.cco.bloodbonorlist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Quary extends DBHelper {

    private SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    private SQLiteDatabase readSqLiteDatabase = this.getReadableDatabase();

    public Quary(Context context) {
        super(context);
    }

    public void InsertData(Donor donor) {
        //SQLiteOpenHelper sqLiteOpenHelper = Quary.this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID,donor.getId());
        contentValues.put(NAME, donor.getName());
        contentValues.put(AGE, donor.getAge());
        contentValues.put(ADDRESS, donor.getAddress());
        contentValues.put(BLOOD_GROUP, donor.getBloodGroup());
        contentValues.put(LAST_DONATON_DATE, donor.getLastDonationDate());
        contentValues.put(PROFILE_IMAGE, donor.getImage());
        contentValues.put(COUNTRACT_NUMBERS, donor.getCountractNumber());

        long l = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (l > -1) {
            System.out.println("databse insert ");
        } else {
            System.out.println(" inseted Error ");
        }
    }

    public void UpdateData(Donor donor) {
        //SQLiteOpenHelper sqLiteOpenHelper = Quary.this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID,donor.getId());
        contentValues.put(NAME, donor.getName());
        contentValues.put(AGE, donor.getAge());
        contentValues.put(ADDRESS, donor.getAddress());
        contentValues.put(BLOOD_GROUP, donor.getBloodGroup());
        contentValues.put(LAST_DONATON_DATE, donor.getLastDonationDate());
        contentValues.put(PROFILE_IMAGE, donor.getImage());
        contentValues.put(COUNTRACT_NUMBERS, donor.getCountractNumber());

        long l = sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{donor.getId()});

        if (l > -1) {
            System.out.println("database Update ");
        } else {
            System.out.println(" Update Error ");
        }
    }

    public List<Donor> getDonorDBdata() {

        List<Donor> donorList = null;// = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;


        //SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        //System.out.println(doctorList);


        // System.out.println(" cursor have data " );

        if (cursor.moveToFirst()) {
            donorList = new ArrayList<>();

            do {

                String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorName = cursor.getString(cursor.getColumnIndex(NAME));
                String donorAge = cursor.getString(cursor.getColumnIndex(AGE));
                String donorContractNumber = cursor.getString(cursor.getColumnIndex(COUNTRACT_NUMBERS));
                String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                String donorLastDonateDate = cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String donorProfile = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE));
                String donorAddress = cursor.getString(cursor.getColumnIndex(ADDRESS));

                Donor donor = new Donor(donorId, donorName, donorAge, donorProfile, donorBloodGroup, donorContractNumber, donorLastDonateDate, donorAddress);
                donorList.add(donor);


                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }

        return donorList;
    }

    public List<Donor> getAvalableBloodGroup() {

        List<Donor> donorList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;


/*

        String selectQuery = "SELECT " + BLOOD_GROUP + "," + ID + " FROM " + TABLE_NAME + " WHERE (SELECT IF "+month+">"+Integer.valueOf("SUBSTRING("+LAST_DONATON_DATE+",4,2)")+", "
                +month+"-"+Integer.valueOf("SUBSTRING("+LAST_DONATON_DATE+",4,2)")+") > 3 GROUP BY " + BLOOD_GROUP;
*/


        String selectQuery = "SELECT " + BLOOD_GROUP + "," + ID + ", "+LAST_DONATON_DATE+"  FROM " + TABLE_NAME + "  GROUP BY " + BLOOD_GROUP;
      //  String selectQuery = "SELECT SUBSTR("+LAST_DONATON_DATE+",1,2) FROM " + TABLE_NAME ;

        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // donorList = new ArrayList<>();

            do {

                System.out.println(cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE)).toString());


                String donationDate= cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String sp[] = donationDate.split("-",3);
         /*       System.out.println("splide 1 "+sp[0]);
                System.out.println("splide 2 "+sp[1]);
                System.out.println("month "+month);*/

                if(( month > Integer.valueOf(sp[1]) && month-Integer.valueOf(sp[1]) >= 4) ||
                        (month< Integer.valueOf(sp[1]) && (month+12)-Integer.valueOf(sp[1])>=4)
                        )
                {
                    String donorId = cursor.getString(cursor.getColumnIndex(ID));
                    String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                    Donor donor = new Donor(donorId, donorBloodGroup);
                    donorList.add(donor);
                }


/*
                String donation = donationDate.substring(3,5);
                System.out.println("month "+donation);
                String a[] = donation.split("-");
                System.out.println("splide "+a[0]);*/

                //Log.d(TAG, "getAvalableBloodGroup: "+donorId2);

                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }


        return donorList;
    }

    public String  getUserId() {

        String selectQuery = "SELECT " + ID + "  FROM " + TABLE_NAME ;

        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToLast();

        return cursor.getString(cursor.getColumnIndex(ID));
    }

    public List<Donor> searchByBlood(String Blood) {

        List<Donor> donorList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + BLOOD_GROUP + " = '" + Blood + "'";

        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // donorList = new ArrayList<>();

            do {

                String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorName = cursor.getString(cursor.getColumnIndex(NAME));
                String donorAge = cursor.getString(cursor.getColumnIndex(AGE));
                String donorContractNumber = cursor.getString(cursor.getColumnIndex(COUNTRACT_NUMBERS));
                String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                String donorLastDonateDate = cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String donorProfile = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE));
                String donorAddress = cursor.getString(cursor.getColumnIndex(ADDRESS));

                Donor donor = new Donor(donorId, donorName, donorAge, donorProfile, donorBloodGroup, donorContractNumber, donorLastDonateDate, donorAddress);
                donorList.add(donor);
                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }


        return donorList;
    }

    public List<Donor> searchByName(String Name) {

        List<Donor> donorList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '" + Name + "%'";

        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // donorList = new ArrayList<>();

            do {


                String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorName = cursor.getString(cursor.getColumnIndex(NAME));
                String donorAge = cursor.getString(cursor.getColumnIndex(AGE));
                String donorContractNumber = cursor.getString(cursor.getColumnIndex(COUNTRACT_NUMBERS));
                String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                String donorLastDonateDate = cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String donorProfile = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE));
                String donorAddress = cursor.getString(cursor.getColumnIndex(ADDRESS));

                Donor donor = new Donor(donorId, donorName, donorAge, donorProfile, donorBloodGroup, donorContractNumber, donorLastDonateDate, donorAddress);
                donorList.add(donor);

                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }


        return donorList;
    }

    public List<Donor> getAvalableBloodGroupDetails(String blood) {
        List<Donor> donorList = new ArrayList<>();
        //System.out.println(blood);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + BLOOD_GROUP + " = '" + blood + "'";
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // donorList = new ArrayList<>();

            do {

                String donorLastDonateDate = cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String sp[] = donorLastDonateDate.split("-",3);

                if(( month > Integer.valueOf(sp[1]) && month-Integer.valueOf(sp[1]) >= 4) ||
                        (month< Integer.valueOf(sp[1]) && (month+12)-Integer.valueOf(sp[1])>=4)
                        ) {

                    String donorId = cursor.getString(cursor.getColumnIndex(ID));
                    String donorName = cursor.getString(cursor.getColumnIndex(NAME));
                    String donorAge = cursor.getString(cursor.getColumnIndex(AGE));
                    String donorContractNumber = cursor.getString(cursor.getColumnIndex(COUNTRACT_NUMBERS));
                    String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                    String donorProfile = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE));
                    String donorAddress = cursor.getString(cursor.getColumnIndex(ADDRESS));

                    Donor donor = new Donor(donorId, donorName, donorAge, donorProfile, donorBloodGroup, donorContractNumber, donorLastDonateDate, donorAddress);
                    donorList.add(donor);

                }
              /*  String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorBloodGroup = blood;// cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                Donor donor = new Donor(donorId,donorBloodGroup);
                donorList.add(donor);*/
                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }


        return donorList;
    }

    public List<Donor> getUser(String id) {
        List<Donor> donorList = new ArrayList<>();
        //System.out.println(blood);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "'";

        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // donorList = new ArrayList<>();

            do {

                String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorName = cursor.getString(cursor.getColumnIndex(NAME));
                String donorAge = cursor.getString(cursor.getColumnIndex(AGE));
                String donorContractNumber = cursor.getString(cursor.getColumnIndex(COUNTRACT_NUMBERS));
                String donorBloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                String donorLastDonateDate = cursor.getString(cursor.getColumnIndex(LAST_DONATON_DATE));
                String donorProfile = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE));
                String donorAddress = cursor.getString(cursor.getColumnIndex(ADDRESS));

                Donor donor = new Donor(donorId, donorName, donorAge, donorProfile, donorBloodGroup, donorContractNumber, donorLastDonateDate, donorAddress);
                donorList.add(donor);


              /*  String donorId = cursor.getString(cursor.getColumnIndex(ID));
                String donorBloodGroup = blood;// cursor.getString(cursor.getColumnIndex(BLOOD_GROUP));
                Donor donor = new Donor(donorId,donorBloodGroup);
                donorList.add(donor);*/
                //Doctor doctor = new Doctor(doctorId, doctorName, doctorDetails, doctorAppoinmentDate, doctorPhone, doctorEmail, doctorImage);
                //doctorList.add(doctor);

            } while (cursor.moveToNext());

        }


        return donorList;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public List<String> getUserSize(){

        String selectQuery = "SELECT " + ID + "  FROM " + TABLE_NAME ;

        List<String> size = new ArrayList<>();
        Cursor cursor = readSqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String donorId = cursor.getString(cursor.getColumnIndex(ID));
                size.add(donorId);

            } while (cursor.moveToNext());

        }


        return size;
    }
}