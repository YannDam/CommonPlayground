package com.wordpress.commonplayground.commonplayground;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class AddSessionActivity extends AppCompatActivity implements View.OnClickListener {

    Button publish, btnDatePicker, btnTimePicker;
    TextInputLayout title, game, place, date, time, numberOfPlayers, description;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        publish = (Button) findViewById(R.id.ButtonPublish);
        publish.setOnClickListener(this);

        btnDatePicker = (Button)findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);

        title = (TextInputLayout) findViewById(R.id.TitleInput);
        game = (TextInputLayout) findViewById(R.id.GameInput);
        place = (TextInputLayout) findViewById(R.id.PlaceInput);
        date = (TextInputLayout) findViewById(R.id.DateInput);
        numberOfPlayers = (TextInputLayout) findViewById(R.id.PlayersInput);
        description = (TextInputLayout) findViewById(R.id.DescriptionInput);

    }

    @Override
    public void onClick(View v) {
        if (v == publish) {

            try {
                String body =
                        "name=" + URLEncoder.encode( title.getEditText().getText().toString(), "UTF-8" ) + "&" +
                                "description=" + URLEncoder.encode( description.getEditText().getText().toString(), "UTF-8" ) + "&" +
                                "game=" + URLEncoder.encode( game.getEditText().getText().toString(), "UTF-8" ) + "&" +
                                "place=" + URLEncoder.encode( place.getEditText().getText().toString(), "UTF-8" ) + "&" +
                                "date=" + URLEncoder.encode( date.getEditText().getText().toString(), "UTF-8" ) + "&" +
                                "numberOfPlayers=" + URLEncoder.encode( numberOfPlayers.getEditText().getText().toString(), "UTF-8" );

                Log.v("AddSessionActivity", body);

                URL url = new URL( "192.168.178.33:8080/postNewSession" );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod( "POST" );
                connection.setDoInput( true );
                connection.setDoOutput( true );
                connection.setUseCaches( false );
                connection.setRequestProperty( "Content-Type",
                        "application/x-www-form-urlencoded" );
                connection.setRequestProperty( "Content-Length", String.valueOf(body.length()) );

                OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
                writer.write( body );
                writer.flush();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()) );

                for ( String line; (line = reader.readLine()) != null; )
                {
                    System.out.println( line );
                }

                writer.close();
                reader.close();
            } catch (Exception e){
                assert false;
            }
        }
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.getEditText().setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
