package com.teamtreehouse.friendlyforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teamtreehouse.friendlyforecast.services.Forecast;

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
    public void insertForecast(Forecast forecast) {

        //begin transaction
        mDatabase.beginTransaction();

        try {
            //Insert 48 updates in 1 transaction
            for (Forecast.HourData hour : forecast.hourly.data) {
                ContentValues values = new ContentValues();
                //key value
                values.put(ForecastHelper.COLUMN_TEMPERATURE, hour.temperature);
                mDatabase.insert(ForecastHelper.TABLE_TEMPERATURES, null, values);
            }
            mDatabase.setTransactionSuccessful();
        }
        finally {
            //End transaction
            mDatabase.endTransaction();
        }


    }

    //Select
    public Cursor selectAllTemperatures() {
        Cursor cursor = mDatabase.query(

                ForecastHelper.TABLE_TEMPERATURES, //Table
                new String[] {ForecastHelper.COLUMN_TEMPERATURE}, //Columns
                null, //WHERE clause
                null, //WHERE PARAMS
                null, //GROUP BY
                null, //HAVING
                null //ORDER BY
        );

        return cursor;

    }

    public Cursor selectTempsGreaterThan(String minTemp) {
        String whereClause = ForecastHelper.COLUMN_TEMPERATURE + " > ? ";


        Cursor cursor = mDatabase.query(

                ForecastHelper.TABLE_TEMPERATURES, //Table
                new String[] {ForecastHelper.COLUMN_TEMPERATURE}, //Columns
                whereClause, //WHERE clause
                new String[] {minTemp}, //WHERE PARAMS
                null, //GROUP BY
                null, //HAVING
                null //ORDER BY
        );

        return cursor;
    }

    //Update
    //returns number of rows
    public int updateTemperature(double newTemp) {
        ContentValues values = new ContentValues();
        values.put(ForecastHelper.COLUMN_TEMPERATURE, newTemp);
        int rowsUpdated = mDatabase.update(
                ForecastHelper.TABLE_TEMPERATURES, //Table
                values, //Values
                null, //WHERE clause
                null
        );

        return rowsUpdated;
    }

    //Delete
    public void deleteAll() {
        mDatabase.delete(
                ForecastHelper.TABLE_TEMPERATURES, //Tables
                null, //WHERE clause
                null //WHERE Parameters
        );
    }

    //Close database
    public void close() {
        mDatabase.close();
    }

}
