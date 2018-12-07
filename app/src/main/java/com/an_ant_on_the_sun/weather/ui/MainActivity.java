package com.an_ant_on_the_sun.weather.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.an_ant_on_the_sun.weather.R;
import com.an_ant_on_the_sun.weather.db.DatabaseChangedReceiver;
import com.an_ant_on_the_sun.weather.db.WeatherContract;
import com.an_ant_on_the_sun.weather.model.DataToDisplay;
import com.an_ant_on_the_sun.weather.model.QueryParameters;
import com.an_ant_on_the_sun.weather.network.QueryTask;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

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

    private EditText mEditTextCityName;
    private Button mButtonSearch;
    private TextView mTextViewDescription;
    private TextView mTextViewInfoAboutDisabling;

    private DatabaseChangedReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextCityName = findViewById(R.id.editTextCityName);
        mButtonSearch = findViewById(R.id.buttonSearch);
        mTextViewDescription = findViewById(R.id.textViewDescription);
        mTextViewInfoAboutDisabling = findViewById(R.id.textViewInfoAboutDisabling);

        mLoader = getLoaderManager().initLoader(CITIES_LOADER, null, this);
        mReceiver = new DatabaseChangedReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Создаем и регистрируем приемник широковещательных сообщений для получения сообщения
        //об обновлении базы данных
        IntentFilter intentFilter = new IntentFilter(DatabaseChangedReceiver
                .ACTION_DATABASE_CHANGED);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mReceiver);
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoader = null;
    }

    public void onButtonSearchClick(View view){
        //For checking working ability
        boolean dataExistInCursor;
        cityId = Integer.parseInt(mEditTextCityName.getText().toString());
        mTextViewDescription.setText("");
        if(mData != null && mData.getCount() > 0){
            dataExistInCursor = CursorHandler.getDataFromCursor(mData, cityId);
            if(dataExistInCursor){
                showResultOnScreen();
                return;
            }
        }
        queryParameters.setCityId(cityId);
        QueryTask queryTask = new QueryTask(queryParameters, this, mData);
        queryTask.execute();
        DisableButtonTask disableButtonTask = new DisableButtonTask(this, mButtonSearch,
                mTextViewInfoAboutDisabling);
        disableButtonTask.execute();
    }

    private void showResultOnScreen(){
        mTextViewDescription.setText(dataToDisplay.getDescription());
    }

    public void updateDataOnScreen(){
        mLoader.onContentChanged();
        CursorHandler.getDataFromCursor(mData, cityId);
        showResultOnScreen();
    }

}
