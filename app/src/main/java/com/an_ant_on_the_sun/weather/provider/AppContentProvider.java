package com.an_ant_on_the_sun.weather.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.an_ant_on_the_sun.weather.db.WeatherContract;
import com.an_ant_on_the_sun.weather.db.WeatherDbHelper;

public class AppContentProvider extends ContentProvider{

    public static final String TAG = AppContentProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for entire table
     */
    private static final int CITIES = 100;

    /**
     * URI matcher code for the content URI for a single line in the table
     */
    private static final int CITY_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.guests/guests" will map to the
        // integer code {@link #CITIES}. This URI is used to provide access to MULTIPLE rows
        // of the guests table.
        sUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY,
                WeatherContract.PATH_WEATHER_IN_CITIES, CITIES);

        // The content URI of the form "content://com.example.android.guests/guests/#" will map to the
        // integer code {@link #CITY_ID}. This URI is used to provide access to ONE single row
        // of the guests table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.guests/guests/3" matches, but
        // "content://com.example.android.guests/guests" (without a number at the end) doesn't match.
        sUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY,
                WeatherContract.PATH_WEATHER_IN_CITIES + "/#", CITY_ID);
    }

    /**
     * Database helper object
     */
    private WeatherDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = WeatherDbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        //Let's get access to the database for reading
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        //Cursor containing the result of the query
        Cursor cursor;
        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match){
            case CITIES:
                // For the CITIES code, query the weather_in_cities table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the table.
                cursor = database.query(WeatherContract.CityEntry.TABLE_NAME, projection, selection,
                        selectionArgs,null, null, sortOrder);
                break;
            case CITY_ID:
                // For the CITY_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.guests/guests/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = WeatherContract.CityEntry.COLUMN_CITY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the guests table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(WeatherContract.CityEntry.TABLE_NAME, projection, selection,
                        selectionArgs,null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        try {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CITIES:
                return WeatherContract.CityEntry.CONTENT_LIST_TYPE;
            case CITY_ID:
                return WeatherContract.CityEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CITIES:
                return insertCityWeather(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CITIES:
                // Delete all rows that match the selection and selection args
                return database.delete(WeatherContract.CityEntry.TABLE_NAME,
                        selection, selectionArgs);
            case CITY_ID:
                // Delete a single row given by the ID in the URI
                selection = WeatherContract.CityEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(WeatherContract.CityEntry.TABLE_NAME,
                        selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if(values.size() == 0){
            return 0;
        }
        final int match = sUriMatcher.match(uri);
        int numberOfUpdatedRows;
        switch (match) {
            case CITIES:
                return updateCityWeather(uri, values, selection, selectionArgs);
            case CITY_ID:
                // For the CITY_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "city_id=?" and selection
                // arguments will be a String array containing the actual CITY_ID.
                selection = WeatherContract.CityEntry.COLUMN_CITY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                numberOfUpdatedRows = updateCityWeather(uri, values, selection, selectionArgs);
                if(numberOfUpdatedRows == 1){
                    return numberOfUpdatedRows;
                } else {
                    Uri insertedRowUri = insertCityWeather(uri,values);
                    return (int) ContentUris.parseId(insertedRowUri); // -1 if null
                }
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Insert weather in a city into the database with the given content values.
     * Return the new content URI for that specific row in the database.
     */
    private Uri insertCityWeather(Uri uri, ContentValues values){
        //Checking values
        Integer cityId = values.getAsInteger(WeatherContract.CityEntry.COLUMN_CITY_ID);
        if(cityId == null){
            throw new IllegalArgumentException("City requires an ID");
        }
        String cityName = values.getAsString(WeatherContract.CityEntry.COLUMN_CITY_NAME);
        if(cityName == null){
            throw new IllegalArgumentException("City requires a name");
        }
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new row with the given values
        long id = database.insert(WeatherContract.CityEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Update data in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more rows).
     * Return the number of rows that were successfully updated.
     */
    private int updateCityWeather(Uri uri, ContentValues values,
                                  String selection, String[] selectionArgs){
        // If the {@link CityEntry#COLUMN_CITY_ID} key is present,
        // check that the value is not null.
        if (values.containsKey(WeatherContract.CityEntry.COLUMN_CITY_ID)) {
            Integer id = values.getAsInteger(WeatherContract.CityEntry.COLUMN_CITY_ID);
            if (id == null) {
                throw new IllegalArgumentException("City requires an ID");
            }
        }
        // If the {@link CityEntry#COLUMN_CITY_NAME} key is present,
        // check that the value is not null.
        if (values.containsKey(WeatherContract.CityEntry.COLUMN_CITY_NAME)) {
            String cityName = values.getAsString(WeatherContract.CityEntry.COLUMN_CITY_NAME);
            if (cityName == null) {
                throw new IllegalArgumentException("City requires a name");
            }
        }
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        // Otherwise, get writable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(WeatherContract.CityEntry.TABLE_NAME, values,
                selection, selectionArgs);
    }
}
