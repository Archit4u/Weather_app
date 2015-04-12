package com.mycompany.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
public static final String TAG=MainActivity.class.getSimpleName();
    private Current_weather mCurrent_weather;
    @InjectView(R.id.humidity_num) TextView mHumidity;
    @InjectView(R.id.iconid) ImageView mIcon;
    @InjectView(R.id.summaryid)TextView mSummary;
    @InjectView(R.id.percetvalueid)TextView mPercept;
    @InjectView(R.id.timeid)TextView mtime;
    @InjectView(R.id.tempid)TextView mTemper;
    @InjectView(R.id.refreshid)ImageView mRefreshid;
    @InjectView(R.id.progressBarid)ProgressBar mProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgressbar.setVisibility(View.INVISIBLE);
        final double Latitude = 37.8267;
        final double Longitude = -122.423;
        mRefreshid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getforecast(Latitude,Longitude);
            }
        });
        getforecast(Latitude,Longitude);

    }

    private void getforecast(double Latitude,double Longitude) {
        String key = "9d9f3e4e3625a36986ee3df24bd4be8a";

        String Forecasturl = "https://api.forecast.io/forecast/" + key + "/" + Latitude + "," + Longitude;
        if(isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(Forecasturl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String JsonData=response.body().string();
                        Log.v(TAG, JsonData);
                        if (response.isSuccessful()) {
                            mCurrent_weather = getCurrentDetails(JsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updatedisplay();
                                    System.out.print("HIIIIIIIIIIIIIIIIIIIII");
                                }
                            });

                        }
                        else {
                            AlertDialogBox();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }
                    catch (JSONException JS)
                    {
                        Log.e(TAG, "jSON Exception Caught", JS);
                    }
                }


            });
        }
    else
        {
            Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressbar.getVisibility()==View.INVISIBLE) {
            mProgressbar.setVisibility(View.VISIBLE);
            mRefreshid.setVisibility(View.INVISIBLE);
        }
        else
        {
            mProgressbar.setVisibility(View.INVISIBLE);
            mRefreshid.setVisibility(View.VISIBLE);
        }
    }

    private void updatedisplay() {
        mTemper.setText(mCurrent_weather.getTemp()+"");
        mtime.setText("At "+mCurrent_weather.getFormattedTime()+" It will be");
        mHumidity.setText(mCurrent_weather.getHumidity()+"");
        mPercept.setText(mCurrent_weather.getPercipchance()+" %");
        mSummary.setText(mCurrent_weather.getSummary()+"");
        mIcon.setImageResource(mCurrent_weather.getIconid());



    }

    private Current_weather getCurrentDetails(String jsonData)throws JSONException{
        JSONObject forecast=new JSONObject(jsonData);
        String timezone=forecast.getString("timezone");

       JSONObject current=forecast.getJSONObject("currently");
        Current_weather current_weather=new Current_weather();
        current_weather.setHumidity(current.getDouble("humidity"));
        current_weather.setTime(current.getLong("time"));
        current_weather.setIcon(current.getString("icon"));
        current_weather.setPercipchance(current.getDouble("precipProbability"));
        current_weather.setTemp(current.getDouble("temperature"));
        current_weather.setSummary(current.getString("summary"));

        current_weather.setTimezone(timezone);

        Log.i(TAG, "From json" + current_weather.getFormattedTime());
        return current_weather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        boolean isAvailable=false;
        if(info!=null&&info.isConnected())
        {
            isAvailable=true;
        }
        return isAvailable;
    }

    private void AlertDialogBox() {
AlertDialogFragment dialog=new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_message");
    }
}
