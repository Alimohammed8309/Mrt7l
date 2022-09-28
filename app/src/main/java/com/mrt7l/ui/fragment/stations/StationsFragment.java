package com.mrt7l.ui.fragment.stations;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.MyApplication;
import com.mrt7l.R;
import com.mrt7l.databinding.LocationerrorBinding;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.StationsFragmentBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.LocationHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.activity.SignInActivity;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class StationsFragment extends Fragment implements StationsInterface, LocationHelper.OnLocationReceived  {

    private StationsViewModel mViewModel;
    private static StationsFragment instance;
    public static StationsFragment newInstance() {
        if (instance == null){
            instance = new StationsFragment();
        }
        return instance;
    }
    private StationsFragmentBinding binding;
    private PreferenceHelper preferenceHelper;
    private LoginResponse loginResponse;
    private double lat,lng;
    private MapView mMapView;
    private Marker markerDriver;
    private Bundle mBundle;
    ArrayList<RegisterCollectionResponse.Mrt7alBean.DataBean.CitiesBean> cities = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.stations_fragment, container, false);
        mMapView =  binding.mapz;
        mMapView.onCreate(new Bundle());
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                askFormPermissions();
            } else {
                runLocationService();
            }
        }

        manager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        setUpMap();
        mViewModel = new ViewModelProvider(this).get(StationsViewModel.class);
        preferenceHelper = new PreferenceHelper(requireActivity());
        loginResponse = LoginResponse.getInstance();
        Gson gson = new Gson();
        loginResponse = gson.fromJson(preferenceHelper.getUSERNAME(),LoginResponse.class);
        mViewModel.init(this);
        return binding.getRoot();
    }

    public void  runLocationService(){
        LocationHelper locHelper = new LocationHelper(requireActivity());
        locHelper.setLocationReceivedLister(StationsFragment.this);
        locHelper.onStart();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
    private void setUpCitySpinner(Spinner spinners) {
        ArrayList<String> NameList = new ArrayList<>();
        NameList.add("اختر المدينة");
        for (int i=0;i<cities.size();i++){
            NameList.add(cities.get(i).getName());
         }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.cityText.setText(NameList.get(position));
                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));
                    mViewModel.getStations(String.valueOf(cities.get(position - 1).getId()),String.valueOf(myLatLng.latitude)
                            ,String.valueOf(myLatLng.longitude));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    private GoogleMap map;
    private void setUpMap() {
        if (map == null) {
             mMapView.getMapAsync(googleMap -> {
                 map = googleMap;
                 if (myLocation != null) {
                     LatLng latLang = new LatLng(myLocation.getLatitude(),
                             myLocation.getLongitude());
                     CameraUpdate factory = CameraUpdateFactory.newLatLngZoom(latLang,15);
                     map.moveCamera(factory);
                 }
                 map.setOnMarkerClickListener(marker -> {
                     marker.showInfoWindow();
                     return true;
                 });
             });


        }
    }

    void goToGoogleMapApp(LatLng latLng){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLng.latitude+"," +latLng.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

//    private void setMarkers(LatLng latLang) {
//        LatLng latLngDriver = new LatLng(lat,
//                lng);
//        setDriverMarker(latLngDriver);
//        //boundLatLang();
//    }
//    private void setDriverMarker(LatLng latLng ) {
//        if (latLng != null) {
//            if (map != null) {
//                if (markerDriver == null) {
//                    MarkerOptions opt = new MarkerOptions();
//                    opt.flat(true);
//                    opt.anchor(0.5f, 0.5f);
//                    opt.position(latLng);
////                    opt.icon(BitmapDescriptorFactory
////                            .fromResource(android.R.drawable.map));
//                    opt.title(getString(R.string.text_drive_location));
//                    markerDriver = map.addMarker(opt);
//                } else {
//                    Location driverLocation = new Location("");
//                    driverLocation.setLatitude(latLng.latitude);
//                    driverLocation.setLongitude(latLng.longitude);
//                     animateMarker(markerDriver, latLng, driverLocation, false);
//                    // if (isCameraZoom) {
//                    // animateCameraToMarker(latLng);
//                    // }
//                }
//                //if (myMarker != null && myMarker.getPosition() != null)
//                //getDirectionsUrl(latLng, myMarker.getPosition());
//            }
//        }
//
//    }

    private void animateMarker(final Marker marker, final LatLng toPosition,
                               final Location toLocation, final boolean hideMarker) {
        if (map == null) {
            return;
        }
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                float rotation = (float) (t * toLocation.getBearing() + (1 - t)
                        * startRotation);
                if (rotation != 0) {
                    marker.setRotation(rotation);
                }
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }




    private void askFormPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (ContextCompat.checkSelfPermission(requireActivity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(requireActivity(),
//                            Manifest.permission.ACCESS_COARSE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                            8);
//                }
//            }
//        }
    }


    private boolean isGpsDialogShow = false;

    @Override
    public void onResume() {
        super.onResume();
        if (DashboardActivity.tvTitle != null){
            DashboardActivity.tvTitle.setText(getString(R.string.staions));
        }
        mMapView.onResume();
        if (receiver == null) {
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            if (getActivity() != null)
                getActivity().registerReceiver(receiver, filter);
            isRecieverRegistered = true;
        }
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ShowGpsDialog();
            isGpsDialogShow = true;

        } else {
            if (isGpsDialogShow) {
                isGpsDialogShow = false;

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        map = null;
        if (mMapView != null)
            mMapView.onDestroy();
        try {
            if (isRecieverRegistered) {
                if ( getActivity() != null)
                    getActivity().unregisterReceiver(receiver);
                requireActivity().unregisterReceiver(GpsChangeReceiver);

            }
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(boolean isSuccess, StationsResponse contactUsModel) {
        if (isSuccess) {
            if (contactUsModel.getMrt7al().getData().size() > 0) {
                if (map != null){
                    boolean isFirstRow = true;
                for (int i = 0;i<contactUsModel.getMrt7al().getData().size();i++){
                    map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(
                            contactUsModel.getMrt7al().getData().get(i).getLat_at()),
                            Double.parseDouble(contactUsModel.getMrt7al().getData().get(i).getLong_at()))).
                            title(contactUsModel.getMrt7al().getData().get(i).getName()));
                    if (isFirstRow){
                        isFirstRow = false;
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(
                                contactUsModel.getMrt7al().getData().get(i).getLat_at()),
                                Double.parseDouble(contactUsModel.getMrt7al().getData().get(i).getLong_at())), 16);
                        map.animateCamera(cu);

                    }
                }
                    map.setOnInfoWindowClickListener(marker -> {
                        LatLng latLon = marker.getPosition();
                        try {
                            goToGoogleMapApp(latLon);
                        } catch (ActivityNotFoundException e){
                            e.printStackTrace();
                        }
                        //Cycle through places array
//                for(Place place : places){
//                    if (latLon.equals(place.latlng)){
//                        //match found!  Do something....
//                    }
//
//                }
                    });
                }
                Gson gson = new Gson();
                String obj = gson.toJson(contactUsModel);
                Bundle bundle = new Bundle();
                bundle.putString("model", obj);
                Navigation.findNavController(requireActivity(), R.id.main_fragment)
                        .navigate(R.id.action_stationsFragment_to_stationsBottomSheet, bundle);
            } else {
                Toast.makeText(requireActivity(), getString(R.string.no_stations), Toast.LENGTH_SHORT).show();
            }
        }
        DialogsHelper.removeProgressDialog();
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        DialogsHelper.removeProgressDialog();
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else if (code == 404) {
                    Log.v("404", "my booking fragment page not found");
                } else {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSearchResult() {

    }

    @Override
    public void onLocationReceived(LatLng latlong) {
        myLatLng = latlong;
        myLocation.setLatitude(latlong.latitude);
        myLocation.setLongitude(latlong.longitude);
    }

    @Override
    public void onLocationReceived(Location location) {
        myLocation = location;
        myLatLng = new LatLng(location.getLatitude(),
                location.getLongitude());
    }

    @Override
    public void onConncted(Bundle bundle) {

    }

    LatLng myLatLng;
    Location myLocation;
    @Override
    public void onConncted(Location location) {
        if (location != null) {
            myLocation = location;
             myLatLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
             if (map != null){
                 try {
                      String collectionModel = new PreferenceHelper(MyApplication.getInstance()
                              .getApplicationContext()).getGROUPID();
                     if (collectionModel != null){
                         Gson gson = new Gson();
                         RegisterCollectionResponse collectionResponse;
                         collectionResponse = gson.fromJson(collectionModel,
                                 RegisterCollectionResponse.class);
                         cities.addAll(collectionResponse.getMrt7al().getData().getCities());
                         setUpCitySpinner(binding.citySpinner);
                     }

                     CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(myLatLng, 16);
                     map.addMarker(new MarkerOptions().position(myLatLng));
                     map.animateCamera(cu);
                     Gson gson = new Gson();
                     LoginResponse response = LoginResponse.getInstance();
                     response = gson.fromJson(new PreferenceHelper(requireActivity()).getUSERNAME(),LoginResponse.class);
                     for (int i=0;i<cities.size();i++){
                         if (response.getMrt7al().getData().getCity_id() == cities.get(i).getId()){
                             binding.citySpinner.setSelection(i+1);
                             break;
                         }
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
        }
    }
    private LocationManager manager;

    int gpsMode = 99;
    boolean aaa = false;
    public BroadcastReceiver GpsChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final LocationManager manager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            if (manager != null) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // do something
                    gpsMode = getLocationMode(requireActivity());
                    if (gpsMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                        removeGpsDialog();
                        removeHigjPrirityDialog();
                        BroadcastHelper.sendInform(requireActivity(), "gpsConnected");



                    } else {
                        removeHigjPrirityDialog();
                        ShowGpsHighPriorityDialog();
                    }
                } else {
                    aaa = true;
                    if (isGpsDialogShowing) {
                        return;
                    }
                    ShowGpsDialog();
                }
            } else {
                ShowGpsDialog();
            }
        }
    };

    public static int getLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;
            }
            else if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                locationMode = Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;
            }
            else if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                locationMode = Settings.Secure.LOCATION_MODE_SENSORS_ONLY;
            }
            else if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                locationMode = Settings.Secure.LOCATION_MODE_BATTERY_SAVING;
            }

        }

        return locationMode;
    }
    private void ShowGpsDialog() {
       DialogsHelper.removeProgressDialog();
        isGpsDialogShowing = true;
        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                requireActivity());
        gpsBuilder.setCancelable(false);
        gpsBuilder
                .setTitle(getString(R.string.dialog_no_gps))
                .setMessage(getString(R.string.dialog_no_gps_messgae))
                .setPositiveButton(getString(R.string.dialog_enable_gps),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                requireActivity().startActivity(intent);
                                removeGpsDialog();
                            }
                        })

                .setNegativeButton(getString(R.string.dialog_exit),
                        (dialog, which) -> {
                            try{
                                removeGpsDialog();
                                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
                            }catch (IllegalStateException e){
                                e.printStackTrace();
                            }
                        });
        gpsAlertDialog = gpsBuilder.create();
        if(!requireActivity().isFinishing())
        {
            gpsAlertDialog.show();
        }
    }


    private boolean isGpsDialogShowing = false;
    private void ShowGpsHighPriorityDialog() {
        DialogsHelper.removeProgressDialog();
        isGpsDialogShowing = true;
        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                requireActivity());
        gpsBuilder.setCancelable(false);
        gpsBuilder
                .setTitle(getString(R.string.gps_isnot_highpriority))
                .setMessage(getString(R.string.please_make_high_priority))
                .setPositiveButton(getString(R.string.dialog_enable_gps),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                                removeGpsDialog();
                            }
                        })

                .setNegativeButton(getString(R.string.dialog_exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeGpsDialog();
                                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
                            }
                        });
        gpsHighPriorityDialog = gpsBuilder.create();
        if(!requireActivity().isFinishing())
        {
            gpsHighPriorityDialog.show();
        }
    }
    private AlertDialog  gpsAlertDialog, gpsHighPriorityDialog;

    private void removeHigjPrirityDialog() {
        if (gpsHighPriorityDialog != null && gpsHighPriorityDialog.isShowing()) {
            gpsHighPriorityDialog.dismiss();
            gpsHighPriorityDialog = null;

        }
    }

    private void removeGpsDialog() {
        if (gpsAlertDialog != null && gpsAlertDialog.isShowing()) {
            gpsAlertDialog.dismiss();
            isGpsDialogShowing = false;
            gpsAlertDialog = null;

        }
    }


    private Receiver receiver;
    private boolean isRecieverRegistered = false;

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                Log.v("receive", methodName);
                switch (methodName) {
                    case "changeLocation":
                        lat = Double.parseDouble(Objects.requireNonNull(arg1.getStringExtra("lat")));
                        lng = Double.parseDouble(Objects.requireNonNull(arg1.getStringExtra("lng")));
                        if (map != null){
                            try {
                                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 16);
                                map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
                                map.animateCamera(cu);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "locationGranted":
                        runLocationService();

                        break;


                    case "LocationRefused":
                        showLocationErrorDialog(getString(R.string.must_request_location),requireActivity());
                        break;

                    default:
                        break;

                }
            }
        }
    }
    public void showLocationErrorDialog(String message, Activity activity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        LocationerrorBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.locationerror, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(v -> {
            askFormPermissions();
            dialog.cancel();
        });
        registerErrorDialogBinding.cancel.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }

}