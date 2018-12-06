package com.an_ant_on_the_sun.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = WeatherDbHelper.class.getSimpleName();
    private static WeatherDbHelper sInstance;
    /**
     * Database name
     */
    private static final String DATABASE_NAME = "weather.db";

    /**
     * Database version. When changing the scheme, increase it by one
     */
    private static final int DATABASE_VERSION = 1;

    public static synchronized WeatherDbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new WeatherDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor {@link WeatherDbHelper}
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     * @param context - the application context
     */
    private WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    /**
     * When creating database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //String for creating DB
        String SQL_CREATE_WEATHER_IN_CITIES_TABLE = "CREATE TABLE "
                + WeatherContract.CityEntry.TABLE_NAME + " ("
                + WeatherContract.CityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WeatherContract.CityEntry.COLUMN_CITY_ID + " INTEGER NOT NULL, "
                + WeatherContract.CityEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, "
                + WeatherContract.CityEntry.COLUMN_LON + " REAL, "
                + WeatherContract.CityEntry.COLUMN_LAT + " REAL, "
                + WeatherContract.CityEntry.COLUMN_COUNTRY + " TEXT, "
                + WeatherContract.CityEntry.COLUMN_SUNRISE + " INTEGER, "
                + WeatherContract.CityEntry.COLUMN_SUNSET + " INTEGER, "
                + WeatherContract.CityEntry.COLUMN_DESCRIPTION + " TEXT, "
                + WeatherContract.CityEntry.COLUMN_ICON + " TEXT, "
                + WeatherContract.CityEntry.COLUMN_TEMP + " REAL, "
                + WeatherContract.CityEntry.COLUMN_HUMIDITY + " INTEGER, "
                + WeatherContract.CityEntry.COLUMN_PRESSURE + " REAL, "
                + WeatherContract.CityEntry.COLUMN_TEMP_MIN + " REAL, "
                + WeatherContract.CityEntry.COLUMN_TEMP_MAX + " REAL, "
                + WeatherContract.CityEntry.COLUMN_WIND_SPEED + " REAL, "
                + WeatherContract.CityEntry.COLUMN_WIND_DEGREE + " REAL, "
                + WeatherContract.CityEntry.COLUMN_CLOUDS + " INTEGER, "
                + WeatherContract.CityEntry.COLUMN_RAIN + " INTEGER, "
                + WeatherContract.CityEntry.COLUMN_SNOW + " INTEGER"
                + ");";
        //Creating the table
        db.execSQL(SQL_CREATE_WEATHER_IN_CITIES_TABLE);
    }

    /**
     * When db schema is updated
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Updating from version " + oldVersion
                + " to version " + newVersion);

        // Deleting old table
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.CityEntry.TABLE_NAME);
        // Creating new table
        onCreate(db);
    }
}
