package com.tristenallen.watersource.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tristenallen.watersource.model.SourceReport;

/**
 * Created by David on 3/28/17.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WaterSource.db";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        UserDB.onCreate(database);
        SourceReportDB.onCreate(database);
        PurityReportDB.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        UserDB.onUpgrade(database, oldVersion, newVersion);
        SourceReportDB.onUpgrade(database, oldVersion, newVersion);
        PurityReportDB.onUpgrade(database, oldVersion, newVersion);
    }
}