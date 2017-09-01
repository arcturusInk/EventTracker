package com.thought_elevator.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Swati on 4/1/2017.
 */

public class AddEvent extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText eventName;
    EditText eventLoc;
    EditText eventDate;
    EditText eventTime;
    EditText eventDes;

    Button cancelEventBtn;
    Button createEventBtn;

    // url to create new event
    private static String url_create_event = "http://eventtracker-com.stackstaging.com/create_event.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        // Edit Text
        eventName = (EditText) findViewById(R.id.eventName);
        eventLoc = (EditText) findViewById(R.id.eventLoc);
        eventDate = (EditText) findViewById(R.id.eventDate);
        eventTime = (EditText) findViewById(R.id.eventTime);
        eventDes = (EditText) findViewById(R.id.eventDes);

        //Buttons
        cancelEventBtn = (Button) findViewById(R.id.cancelEventBtn);
        createEventBtn = (Button) findViewById(R.id.createEventBtn);

        cancelEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToFeed = new Intent(AddEvent.this, MainActivity.class);
                startActivity(goBackToFeed);
            }
        });

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eventName.getText().toString();
                String loc = eventLoc.getText().toString();
                String date = eventDate.getText().toString();
                String time = eventTime.getText().toString();
                String des = eventDes.getText().toString();
                // creating new event in background thread
                new CreateNewEvent().execute(name, loc, date, time, des);
            }
        });

    }
    /**
     * Background Async Task to Create new event
     * */
    class CreateNewEvent extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddEvent.this);
            pDialog.setMessage("Creating Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating event
         * */
        protected String doInBackground(String... args) {

            String name = args[0],
                    loc = args[1],
                    date = args[2],
                    time = args[3],
                    des = args[4];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("loc", loc));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("time", time));
            params.add(new BasicNameValuePair("des", des));

            // getting JSON Object
            // Note that create event url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_event,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created event
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create event
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
