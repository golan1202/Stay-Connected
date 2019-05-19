package com.cliff.StayConnected;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import android.Manifest;


public class Dashboard extends AppCompatActivity   {

    String lat, lon;
    final String TAG = "GPS";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";
    final String ARICLE_FRAGMENT_TAG = "article_fragment";
    FragmentManager manager;
    FragmentTransaction trans;

    LocationManager locationManager;
    public static final Integer MY_PERMISSIONS_REQUEST_LOCATION = 0x5;
    fragment_weather fragment_weather;
    ListItem listItem;
    NavigationView navigationView;

    DrawerLayout drawerLayout;

    MenuItem selectedSubject, selectedDuration;
    int notificationTime;

    AlarmManager alarmManager;
    private Context context;

    private NotificationManagerCompat notificationManager;


    LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        alarmManager= (AlarmManager)getSystemService(ALARM_SERVICE);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        manager=getSupportFragmentManager();

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        } else {
            setupListeners();


        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getGroupId() == R.id.subject_item) {
                    if (selectedSubject != null) {
                        selectedSubject.setChecked(false);
                    }
                    selectedSubject = item;
                    selectedSubject.setChecked(true);
                } else if (item.getGroupId() == R.id.duration_item) {
                    if (selectedDuration != null) {
                        selectedDuration.setChecked(false);
                    }
                    selectedDuration = item;
                    selectedDuration.setChecked(true);
                }




                if((item==navigationView.getMenu().findItem(R.id.duration1) & selectedSubject==navigationView.getMenu().findItem(R.id.item_articles))||(item==navigationView.getMenu().findItem(R.id.item_articles) & selectedDuration==navigationView.getMenu().findItem(R.id.duration1))) {
                    notificationTime = 1;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);

                    intent.putExtra("time",notificationTime);
                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent2);
                        startForegroundService(intent);
                    } else {
                        stopService(intent2);
                        startService(intent);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Articles \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    return false;
                }
                if((item==navigationView.getMenu().findItem(R.id.duration1) & selectedSubject==navigationView.getMenu().findItem(R.id.item_sport))||(item==navigationView.getMenu().findItem(R.id.item_sport) & selectedDuration==navigationView.getMenu().findItem(R.id.duration1))) {
                    notificationTime = 1;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);
                    intent2.putExtra("time", notificationTime);

                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent);
                        startForegroundService(intent2);
                    } else {
                        stopService(intent);
                        startService(intent2);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Sport \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();
                     return false;
                }
                if((item==navigationView.getMenu().findItem(R.id.duration2) & selectedSubject==navigationView.getMenu().findItem(R.id.item_articles))||(item==navigationView.getMenu().findItem(R.id.item_articles) & selectedDuration==navigationView.getMenu().findItem(R.id.duration2))) {
                    notificationTime = 2;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);
                    intent.putExtra("time",notificationTime);
                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent2);
                        startForegroundService(intent);
                    } else {
                        stopService(intent2);
                        startService(intent);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Articles \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();

                    return false;
                }
                if((item==navigationView.getMenu().findItem(R.id.duration2) & selectedSubject==navigationView.getMenu().findItem(R.id.item_sport))||(item==navigationView.getMenu().findItem(R.id.item_sport) & selectedDuration==navigationView.getMenu().findItem(R.id.duration2))) {
                    notificationTime = 2;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);

                    intent2.putExtra("time", notificationTime);

                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent);
                        startForegroundService(intent2);
                    } else {
                        stopService(intent);
                        startService(intent2);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Sport \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();
                    return false;
                }
                if((item==navigationView.getMenu().findItem(R.id.duration5) & selectedSubject==navigationView.getMenu().findItem(R.id.item_articles))||(item==navigationView.getMenu().findItem(R.id.item_articles) & selectedDuration==navigationView.getMenu().findItem(R.id.duration5))) {
                    notificationTime = 5;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);
                    intent.putExtra("time",notificationTime);
                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent2);
                        startForegroundService(intent);
                    } else {
                        stopService(intent2);
                        startService(intent);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Articles \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();
                    return false;
                }
                if((item==navigationView.getMenu().findItem(R.id.duration5) & selectedSubject==navigationView.getMenu().findItem(R.id.item_sport))||(item==navigationView.getMenu().findItem(R.id.item_sport) & selectedDuration==navigationView.getMenu().findItem(R.id.duration5))) {
                    notificationTime = 5;
                    Intent intent = new Intent(Dashboard.this, ArticlesService.class);
                    Intent intent2 = new Intent(Dashboard.this, SportService.class);

                    intent2.putExtra("time", notificationTime);

                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent);
                        startForegroundService(intent2);
                    } else {
                        stopService(intent);
                        startService(intent2);
                    }
                    Toast.makeText(Dashboard.this, "Subject : Sport \n Duration : "+notificationTime+ " minutes", Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();
                     return false;
                }

                 if(item==navigationView.getMenu().findItem(R.id.stop_service)){
                     Intent intent2 = new Intent(Dashboard.this, SportService.class);
                     Intent intent = new Intent(Dashboard.this, ArticlesService.class);

                    if (Build.VERSION.SDK_INT >= 26) {
                        stopService(intent);
                        stopService(intent2);
                    } else {
                        stopService(intent);
                        stopService(intent2);
                    }
                     Toast.makeText(Dashboard.this, "You closed notification service !", Toast.LENGTH_SHORT).show();

                     drawerLayout.closeDrawers();
                     return false;
                }


                return false;
            }
        });
    }



    public void setupListeners() {
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                trans=manager.beginTransaction();
                fetchLocationData(location);
                fetchArticleData();
                trans.commit();

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);

        if (!netAndGpsEnabled()) {
            showSettingsAlert();
        } else {
            Log.d(TAG, "GPS on");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0x5: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupListeners();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private boolean netAndGpsEnabled(){

        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGPS && !isNetwork){
            return false;
        }
        else{
            return true;
        }
    }

    public void fetchArticleData(){
        listItem = new ListItem();
        trans.replace(R.id.LayoutArticle,listItem,ARICLE_FRAGMENT_TAG);


    }

    public void fetchLocationData(Location myLocation){

        Log.d(TAG, "got new location update");

        lat = Double.toString(myLocation.getLatitude());
        lon = Double.toString(myLocation.getLongitude());
        Log.d(TAG, "DAta: " + myLocation);

        Bundle bundle = new Bundle();
        bundle.putString("lon", lon);
        bundle.putString("lat", lat);
        // set Fragmentclass Arguments
        fragment_weather  = new fragment_weather();
        fragment_weather.setArguments(bundle);
        trans.replace(R.id.weather_container,fragment_weather,WEATHER_FRAGMENT_TAG);

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            drawerLayout.openDrawer(Gravity.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null)
            locationManager.removeUpdates(locationListener);
    }
}
