package com.teamtreehouse.friendlyforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by rnguyen on 11/5/14.
 */
public class ForecastDataSource {

    private SQLiteDatabase mDatabase;
    private ForecastHelper mForecastHelper;
    private Context mContext;


    public ForecastDataSource(Context context) {
        mContext = context;
        mForecastHelper = new ForecastHelper(context);
    }

    //Open database
    public void open() throws android.database.SQLException {

            mDatabase = mForecastHelper.getWritableDatabase(); //Create database if null or opens database

    }

    //Insert

    //Select

    //Update

    //Delete

    //Close database
    public void close() {
        mDatabase.close();
    }

}
