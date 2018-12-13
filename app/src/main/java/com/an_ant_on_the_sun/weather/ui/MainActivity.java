package com.an_ant_on_the_sun.weather.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.an_ant_on_the_sun.weather.R;
import com.an_ant_on_the_sun.weather.db.DatabaseChangedReceiver;
import com.an_ant_on_the_sun.weather.db.WeatherContract;
import com.an_ant_on_the_sun.weather.filepreferences.WriteDataToFileReceiver;
import com.an_ant_on_the_sun.weather.model.DataToDisplay;
import com.an_ant_on_the_sun.weather.filepreferences.FileNameForPreferences;
import com.an_ant_on_the_sun.weather.model.IconId;
import com.an_ant_on_the_sun.weather.model.QueryParameters;
import com.an_ant_on_the_sun.weather.network.RequestDataFromWebReceiver;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Идентификатор для загрузчика
     */
    private static final int CITIES_LOADER = 0;

    private QueryParameters queryParameters = QueryParameters.getInstance();
    private DataToDisplay dataToDisplay = DataToDisplay.getsInstance();
    private int cityId;
    private String cityName;
    private Cursor mData;
    private Loader<Cursor> mLoader;
    private String mInfoMessage;
    private List<ImageView> listOfImageViewIcons = new ArrayList<>();
    private boolean dataExistInCursor;

    private TextView mTextViewCityName;
    private EditText mEditTextCityName;
    private Button mButtonSearch;
    private TextView mTextViewDescription;
    private ImageView mImageViewIcon01d;
    private ImageView mImageViewIcon01n;
    private ImageView mImageViewIcon02d;
    private ImageView mImageViewIcon02n;
    private ImageView mImageViewIcon03d;
    private ImageView mImageViewIcon03n;
    private ImageView mImageViewIcon04d;
    private ImageView mImageViewIcon04n;
    private ImageView mImageViewIcon09d;
    private ImageView mImageViewIcon09n;
    private ImageView mImageViewIcon10d;
    private ImageView mImageViewIcon10n;
    private ImageView mImageViewIcon11d;
    private ImageView mImageViewIcon11n;
    private ImageView mImageViewIcon13d;
    private ImageView mImageViewIcon13n;
    private ImageView mImageViewIcon50d;
    private ImageView mImageViewIcon50n;
    private TextView mTextViewCurrentTemperature;
    private TextView mTextViewInfoAboutDisabling;

    private DatabaseChangedReceiver mReceiverDatabaseChanged;
    private RequestDataFromWebReceiver mReceiverDataFromWeb;
    private DisableButtonReceiver mReceiverDisableButton;
    private WriteDataToFileReceiver mReceiverWriteToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        mTextViewCityName = findViewById(R.id.textViewCityName);
        mEditTextCityName = findViewById(R.id.editTextCityName);
        mButtonSearch = findViewById(R.id.buttonSearch);
        mTextViewDescription = findViewById(R.id.textViewDescription);
        mImageViewIcon01d = findViewById(R.id.imageViewIcon01d);
        mImageViewIcon01n = findViewById(R.id.imageViewIcon01n);
        mImageViewIcon02d = findViewById(R.id.imageViewIcon02d);
        mImageViewIcon02n = findViewById(R.id.imageViewIcon02n);
        mImageViewIcon03d = findViewById(R.id.imageViewIcon03d);
        mImageViewIcon03n = findViewById(R.id.imageViewIcon03n);
        mImageViewIcon04d = findViewById(R.id.imageViewIcon04d);
        mImageViewIcon04n = findViewById(R.id.imageViewIcon04n);
        mImageViewIcon09d = findViewById(R.id.imageViewIcon09d);
        mImageViewIcon09n = findViewById(R.id.imageViewIcon09n);
        mImageViewIcon10d = findViewById(R.id.imageViewIcon10d);
        mImageViewIcon10n = findViewById(R.id.imageViewIcon10n);
        mImageViewIcon11d = findViewById(R.id.imageViewIcon11d);
        mImageViewIcon11n = findViewById(R.id.imageViewIcon11n);
        mImageViewIcon13d = findViewById(R.id.imageViewIcon13d);
        mImageViewIcon13n = findViewById(R.id.imageViewIcon13n);
        mImageViewIcon50d = findViewById(R.id.imageViewIcon50d);
        mImageViewIcon50n = findViewById(R.id.imageViewIcon50n);
        mTextViewCurrentTemperature = findViewById(R.id.textViewTemperature);
        mTextViewInfoAboutDisabling = findViewById(R.id.textViewInfoAboutDisabling);

        listOfImageViewIcons.addAll(Arrays.asList(
                mImageViewIcon01d,
                mImageViewIcon01n,
                mImageViewIcon02d,
                mImageViewIcon02n,
                mImageViewIcon03d,
                mImageViewIcon03n,
                mImageViewIcon04d,
                mImageViewIcon04n,
                mImageViewIcon09d,
                mImageViewIcon09n,
                mImageViewIcon10d,
                mImageViewIcon10n,
                mImageViewIcon11d,
                mImageViewIcon11n,
                mImageViewIcon13d,
                mImageViewIcon13n,
                mImageViewIcon50d,
                mImageViewIcon50n
                )
        );

        mLoader = getLoaderManager().initLoader(CITIES_LOADER, null, this);
        mReceiverDatabaseChanged = new DatabaseChangedReceiver(this);
        mReceiverDataFromWeb = new RequestDataFromWebReceiver(mButtonSearch);
        mReceiverDisableButton = new DisableButtonReceiver(this,
                mButtonSearch, mTextViewInfoAboutDisabling);
        mReceiverWriteToFile = new WriteDataToFileReceiver();
        mTextViewCityName.setText("");
        hideAllIcons();
        //Starting service for regular data update
        Intent intentService = new Intent(this, RegularDataUpdateService.class);

        readDataFromFile();
        startService(intentService);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Создаем и регистрируем приемники широковещательных сообщений для получения сообщений
        //об обновлении базы данных, необходимости загрузки данных из сети и записи данных в файл
        IntentFilter intentFilterDatabaseChanged = new IntentFilter(DatabaseChangedReceiver
                .ACTION_DATABASE_CHANGED);
        IntentFilter intentFilterNeedDataFromWeb = new IntentFilter(RequestDataFromWebReceiver
                .ACTION_NEED_DATA_FROM_WEB);
        IntentFilter intentFilterWriteDataToFile = new IntentFilter(WriteDataToFileReceiver
                .ACTION_WRITE_DATA_TO_FILE);
        registerReceiver(mReceiverDatabaseChanged, intentFilterDatabaseChanged);
        registerReceiver(mReceiverDataFromWeb, intentFilterNeedDataFromWeb);
        registerReceiver(mReceiverDisableButton, intentFilterNeedDataFromWeb);
        registerReceiver(mReceiverWriteToFile, intentFilterWriteDataToFile);
        //Грузим данные последнего запроса пользователя из предыдущего сеанса работы с приложением
        if(cityId != 0){
            mLoader.forceLoad();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mReceiverDatabaseChanged);
        unregisterReceiver(mReceiverDataFromWeb);
        unregisterReceiver(mReceiverDisableButton);
        unregisterReceiver(mReceiverWriteToFile);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Зададим нужные колонки
        String[] projection = {
                WeatherContract.CityEntry._ID,
                WeatherContract.CityEntry.COLUMN_CITY_ID,
                WeatherContract.CityEntry.COLUMN_CITY_NAME,
                WeatherContract.CityEntry.COLUMN_LON,
                WeatherContract.CityEntry.COLUMN_LAT,
                WeatherContract.CityEntry.COLUMN_COUNTRY,
                WeatherContract.CityEntry.COLUMN_SUNRISE,
                WeatherContract.CityEntry.COLUMN_SUNSET,
                WeatherContract.CityEntry.COLUMN_DESCRIPTION,
                WeatherContract.CityEntry.COLUMN_DESCRIPTION,
                WeatherContract.CityEntry.COLUMN_ICON,
                WeatherContract.CityEntry.COLUMN_TEMP,
                WeatherContract.CityEntry.COLUMN_HUMIDITY,
                WeatherContract.CityEntry.COLUMN_PRESSURE,
                WeatherContract.CityEntry.COLUMN_TEMP_MIN,
                WeatherContract.CityEntry.COLUMN_TEMP_MAX,
                WeatherContract.CityEntry.COLUMN_WIND_SPEED,
                WeatherContract.CityEntry.COLUMN_WIND_DEGREE,
                WeatherContract.CityEntry.COLUMN_CLOUDS,
                WeatherContract.CityEntry.COLUMN_RAIN,
                WeatherContract.CityEntry.COLUMN_SNOW};

        // Загрузчик запускает запрос ContentProvider в фоновом потоке
        return new CursorLoader(this,
                WeatherContract.CityEntry.CONTENT_URI,   // URI контент-провайдера для запроса
                projection,             // колонки, которые попадут в результирующий курсор
                null,                   // без условия WHERE
                null,                   // без аргументов
                null);                  // сортировка по умолчанию
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mData = data;
        checkDataInCursor();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoader = null;
    }

    public void onButtonSearchClick(View view){
        //For checking working ability
        try {
            cityId = Integer.parseInt(mEditTextCityName.getText().toString());
        } catch (NumberFormatException e) {
            Log.i(TAG, "Can't parse int from user input, Exception: ", e);
        }

        if(cityId == 0){
            return;
        }
        mTextViewDescription.setText("");
        mLoader.forceLoad();
    }

    private void showResultOnScreen(){
        mTextViewCityName.setText(getResources()
                .getString(R.string.city_name, dataToDisplay.getCityName()));
        mTextViewDescription.setText(dataToDisplay.getDescription());
        showIcon();
        mTextViewCurrentTemperature.setText(getResources()
                .getString(R.string.current_temperature,
                        String.valueOf(dataToDisplay.getTemperature())));

    }

    private void hideAllIcons(){
        for (int i = 0; i < listOfImageViewIcons.size(); i++){
            listOfImageViewIcons.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private void showIcon(){
        String iconId = dataToDisplay.getIcon();
        if(iconId == null){
            return;
        }
        hideAllIcons();
        switch (iconId){
            case IconId.CLEAR_DAY:
                mImageViewIcon01d.setVisibility(View.VISIBLE);
                break;
            case IconId.CLEAR_NIGHT:
                mImageViewIcon01n.setVisibility(View.VISIBLE);
                break;
            case IconId.PARTLY_CLOUD_DAY:
                mImageViewIcon02d.setVisibility(View.VISIBLE);
                break;
            case IconId.PARTLY_CLOUD_NIGHT:
                mImageViewIcon02n.setVisibility(View.VISIBLE);
                break;
            case IconId.CLOUD_DAY:
                mImageViewIcon03d.setVisibility(View.VISIBLE);
                break;
            case IconId.CLOUD_NIGHT:
                mImageViewIcon03n.setVisibility(View.VISIBLE);
                break;
            case IconId.RAIN_CLOUDS_DAY:
                mImageViewIcon04d.setVisibility(View.VISIBLE);
                break;
            case IconId.RAIN_CLOUDS_NIGHT:
                mImageViewIcon04n.setVisibility(View.VISIBLE);
                break;
            case IconId.RAIN_DAY:
                mImageViewIcon09d.setVisibility(View.VISIBLE);
                break;
            case IconId.RAIN_NIGHT:
                mImageViewIcon09n.setVisibility(View.VISIBLE);
                break;
            case IconId.LIGHT_RAIN_DAY:
                mImageViewIcon10d.setVisibility(View.VISIBLE);
                break;
            case IconId.LIGHT_RAIN_NIGHT:
                mImageViewIcon10n.setVisibility(View.VISIBLE);
                break;
            case IconId.THUNDERSTORM_DAY:
                mImageViewIcon11d.setVisibility(View.VISIBLE);
                break;
            case IconId.THUNDERSTORM_NIGHT:
                mImageViewIcon11n.setVisibility(View.VISIBLE);
                break;
            case IconId.SNOW_DAY:
                mImageViewIcon13d.setVisibility(View.VISIBLE);
                break;
            case IconId.SNOW_NIGHT:
                mImageViewIcon13n.setVisibility(View.VISIBLE);
                break;
            case IconId.FOG_DAY:
                mImageViewIcon50d.setVisibility(View.VISIBLE);
                break;
            case IconId.FOG_NIGHT:
                mImageViewIcon50n.setVisibility(View.VISIBLE);
                break;

            default:
                Log.w(TAG, "Icon ID is not match");

        }
    }

    public void updateDataOnScreen(){
        mLoader.forceLoad();
        writeDataToFile();
    }

    private void checkDataInCursor(){
        if(mData != null && mData.getCount() > 0 && cityId > 0){
            dataExistInCursor = CursorHandler.getDataFromCursor(mData, cityId);
            Log.i(TAG, "checkDataInCursor(), dataExistInCursor = " + dataExistInCursor
                    + ", cityId = " + cityId);
            if(dataExistInCursor){
                showResultOnScreen();
            } else {
                Log.i(TAG, "dataExistInCursor = false, asking for data from web");
                getDataFromWeb();
            }
        }
    }

    private void getDataFromWeb(){
        Intent intentNeedDataFromWeb = new Intent(RequestDataFromWebReceiver
                .ACTION_NEED_DATA_FROM_WEB);
        intentNeedDataFromWeb.putExtra("cityId", cityId);
        sendBroadcast(intentNeedDataFromWeb);
    }

    private void writeDataToFile(){
        Intent intentWriteDataToFile = new Intent(WriteDataToFileReceiver
                .ACTION_WRITE_DATA_TO_FILE);
        intentWriteDataToFile.putExtra("cityId", cityId);
        sendBroadcast(intentWriteDataToFile);
    }

    private void readDataFromFile(){
        SharedPreferences sharedPreferences = getSharedPreferences(FileNameForPreferences.FILE_NAME,
                Context.MODE_PRIVATE);
        if(sharedPreferences.contains(FileNameForPreferences.FILE_CITY_ID)){
            cityId = sharedPreferences.getInt(FileNameForPreferences.FILE_CITY_ID, 0);
            Log.i(TAG, "Read data from file, cityId = " + cityId);
        }
    }

}
