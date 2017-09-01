package com.thought_elevator.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Swati on 4/1/2017.
 */

public class ViewEvent extends AppCompatActivity {
    TextView date, ename, time, loc, description;
    Button btn, del;

    String eid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single event url
    private static final String url_event_details = "http://eventtracker-com.stackstaging.com/get_event_details.php";

    // url to delete event
    private static final String url_delete_event = "http://eventtracker-com.stackstaging.com/delete_event.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EVENT = "event";
    private static final String TAG_EID = "eid";
    private static final String TAG_NAME = "ename";
    private static final String TAG_TIME = "time";
    private static final String TAG_LOC = "loc";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_Date = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        // getting event details from intent
        Intent i = getIntent();

        // getting event id (eid) from intent
        eid = i.getStringExtra(TAG_EID);

        // Getting complete event details in background thread
        new GetEventDetails().execute();
    }
    /**
     * Background Async Task to Get complete event details
     */
    class GetEventDetails extends AsyncTask<String, String, JSONObject> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewEvent.this);
            pDialog.setMessage("Loading Event Details. Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting event details in background thread
         * */
        protected JSONObject doInBackground(String... args) {

            JSONObject event = null;
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("eid", eid));

                // getting event details by making HTTP request
                // Note that event details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_event_details, "GET", params);

                // check your log for json response
                Log.d("Single Event Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received event details
                    JSONArray eventObj = json
                            .getJSONArray(TAG_EVENT); // JSON Array

                    // get first event object from JSON Array
                    event = eventObj.getJSONObject(0);
                }else{
                    // event with eid not found
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return event;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(JSONObject event) {
            if (event != null) {
                setContentView(R.layout.view_event);

                // event with this eid found
                // Text View
                date = (TextView) findViewById(R.id.date);
                ename = (TextView) findViewById(R.id.ename);
                time = (TextView) findViewById(R.id.time);
                loc = (TextView) findViewById(R.id.loc);
                description = (TextView) findViewById(R.id.description);

                try{
                    // display event data in TextView
                    date.setText(event.getString(TAG_Date));
                    ename.setText(event.getString(TAG_NAME));
                    time.setText(event.getString(TAG_TIME));
                    loc.setText(event.getString(TAG_LOC));
                    description.setText(event.getString(TAG_DESCRIPTION));
                }catch (JSONException e){
                    //failed
                }
            }
            // dismiss the dialog once got all details
            pDialog.dismiss();

            //create some toast when RSVP button is clicked
            btn = (Button) findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ViewEvent.this, "You Have RSVPed", Toast.LENGTH_SHORT).show();
                    TextView changeRSVPtext = (TextView)findViewById(R.id.btn);
                    changeRSVPtext.setText("RSVPed");
                }
            });

            del = (Button) findViewById(R.id.del);
            // Delete button click event
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // deleting event in background thread
                    new DeleteEvent().execute();
                }
            });
        }
    }

    class DeleteEvent extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewEvent.this);
            pDialog.setMessage("Deleting Event...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting event
         * */
        protected String doInBackground(String... args) {
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("eid", eid));

                // getting event details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_event, "POST", params);

                // check your log for json response
                Log.d("Delete Event", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // event successfully deleted
                    //go back to feed
                    Intent goBackToFeed = new Intent(ViewEvent.this, MainActivity.class);
                    startActivity(goBackToFeed);
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
            // dismiss the dialog once event deleted
            pDialog.dismiss();
        }

    }
}
