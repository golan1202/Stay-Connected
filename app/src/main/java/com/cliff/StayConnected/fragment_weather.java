package com.cliff.StayConnected;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class fragment_weather extends Fragment  {

    String lat, lon;
    final String TAG = "GPS";
    TextView tvCity, tvWeather, tvDate, tvTemp_c, tvTemp_f;
    RelativeLayout vProgressLayer;
    RequestQueue requestQueue;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather,container,false);

        tvCity =  rootView.findViewById(R.id.tvCity);
        tvWeather =  rootView.findViewById(R.id.tvWeather);
        tvDate =  rootView.findViewById(R.id.tvDate);
        tvTemp_c =  rootView.findViewById(R.id.tvTemp_c);
        tvTemp_f =  rootView.findViewById(R.id.tvTemp_f);

        requestQueue = Volley.newRequestQueue(getActivity());



        lat = getArguments().getString("lat");
        lon = getArguments().getString("lon");

        String url = "http://api.openweathermap.org/data/2.5/weather?id=2172797&APPID=1c8611027b88a5229c4b789fdaed740f&units=metric&lat="+lat+"&lon="+lon;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"DAta: "+response);
                        try {
                            String locationTemp = response.getJSONObject("main").getString("temp");
                            String country = response.getJSONObject("sys").getString("country");
                            String place = response.getString("name") + ", "+ country;
                            String locationWeather = response.getJSONArray("weather").getJSONObject(0).getString("main");
                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                            tvWeather.setText(locationWeather);
                            tvTemp_c.setText(locationTemp + " \u2103");
                            String fahrenheit =String.format( "%.2f",Double.valueOf(locationTemp)*1.8 + 32);
                            tvTemp_f.setText(fahrenheit + " \u2109");
                            tvDate.setText(currentDateTimeString);
                            tvCity.setText(place);


                        } catch (JSONException e) {
                            //some exception handler code.
                            Log.d(TAG, "error occured: "+e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Something went wrong: "+error);
                        Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });

        //add request to queue
        requestQueue.add(jsonObjectRequest);
        return rootView;
    }


//
//    public void startAlarm() {
//
//        //called when the activity is first created
//
//        myIntent = new Intent(getActivity(), WeatherSendRecevier.class);
//        boolean alarmRunning = (PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_NO_CREATE) != null);
//        if (alarmRunning == false) {
//            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
//
//            if (Build.VERSION.SDK_INT >= 26) {
//                getActivity().startForegroundService(myIntent);
//            } else {
//                getActivity().startService(myIntent);
//            }
//
//        }
//    }
}

