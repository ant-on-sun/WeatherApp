package com.an_ant_on_the_sun.weather.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class WeatherContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private WeatherContract(){}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.an_ant_on_the_sun.weather";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.guests/guests/ is a valid path for
     * looking at guest data. content://com.example.android.guests/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     * So usually it is a table name.
     */
    public static final String PATH_WEATHER_IN_CITIES = "weather_in_cities";

    public static final class CityEntry implements BaseColumns {
        /** The content URI to access the data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_WEATHER_IN_CITIES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of cities.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WEATHER_IN_CITIES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single city.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_WEATHER_IN_CITIES;

        public final static String TABLE_NAME = "weather_in_cities";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_LON = "longitude";
        public static final String COLUMN_LAT = "latitude";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_SUNRISE = "sunrise";
        public static final String COLUMN_SUNSET = "sunset";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_TEMP = "temperature";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_TEMP_MIN = "temp_min";
        public static final String COLUMN_TEMP_MAX = "temp_max";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_WIND_DEGREE = "wind_degree";
        public static final String COLUMN_CLOUDS = "clouds";
        public static final String COLUMN_RAIN = "rain";
        public static final String COLUMN_SNOW = "snow";
    }
}
