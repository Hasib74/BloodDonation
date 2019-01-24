package com.securesoftbd.cco.bloodbonorlist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BLOODDONORDATABASE";
    private static final int VERSION = 01;

    public final String TABLE_NAME = "DONORS";

    public final String ID = "ID";
    public final String AGE = "AGE";
    public final String NAME = "NAME";
    public final String ADDRESS ="ADDRESS";
    public final String PROFILE_IMAGE ="PROFILE";
    public final String BLOOD_GROUP = "BLOODGROUP";
    public final String COUNTRACT_NUMBERS = "COUNTRACTNUMBER";
    public final String LAST_DONATON_DATE = "LASTDONATIONDATE";

    private final String SQL = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER primary key , "+NAME + " TEXT, "+AGE +" INTEGER ,"
            +PROFILE_IMAGE+" TEXT NOT NULL,"+BLOOD_GROUP+" TEXT ,"+COUNTRACT_NUMBERS+" TEXT ,"+LAST_DONATON_DATE+" TEXT ,"+ADDRESS+" TEXT "+");";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
