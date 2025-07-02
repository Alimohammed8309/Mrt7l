package com.mrt7l.ui.activity;
import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.ActivityRegisterBinding;
import com.mrt7l.databinding.RegisteraionCompletedDialogBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
 import com.mrt7l.helpers.PathUtil;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import com.onesignal.OneSignal;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;
    private String birthDay;
        private Calendar myCalendar;
        private Uri uri;
        private int genderId = 0,countryId = 0,cityId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         DialogsHelper.showProgressDialog(this,getString(R.string.loading_data));
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.init(this,this);
        try {
            if (Objects.requireNonNull(getIntent().getExtras()).getString("phone") != null) {
                binding.phoneNumber.setText(getIntent().getStringExtra("phone"));
                binding.phoneNumber.setEnabled(false);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.birthDay.setOnClickListener(view -> {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR,1990);
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.uploadImage.setOnClickListener(view -> checkPermission());
        binding.register.setOnClickListener(view -> {
            if(checkInputs()){
                binding.loginProgress.setVisibility(View.VISIBLE);
                binding.register.setVisibility(View.GONE);
                DialogsHelper.disable(binding.wholeView,false);
                String deviceToken =
                new PreferenceHelper(this).getDeviceToken();
                viewModel.register(createPartFromString(binding.firstName.getText().toString()),
                            createPartFromString(String.valueOf(countryId)),
                            createPartFromString(String.valueOf(genderId)),createPartFromString(birthDay),
                            createPartFromString(binding.passportNumber.getText().toString()),
                            createPartFromString(binding.phoneNumber.getText().toString()),
                            createPartFromString(binding.password.getText().toString()),
                            createPartFromString(binding.rePassword.getText().toString()),
                            createPartFromString(String.valueOf(cityId)),
                             createPartFromString(deviceToken!= null?
                                    deviceToken : ""));
//                }
            }
        });
        binding.login.setOnClickListener(view -> {
            Intent in = new Intent(this,SignInActivity.class);
            startActivity(in);
            finish();
        });
        binding.nationality.setOnClickListener(view -> binding.nationalitySpinner.performClick());
        binding.genderType.setOnClickListener(view -> binding.genderSpinner.performClick());
        binding.city.setOnClickListener(view -> binding.citySpinner.performClick());
        binding.firstName.requestFocus();
    }



        private RequestBody createPartFromString(String param) {
            return RequestBody.create(param,MediaType.parse("multipart/form-data"));
        }

    private boolean checkInputs(){
        if (TextUtils.isEmpty(binding.firstName.getText())){
            DialogsHelper.showTextError(binding.firstName,this);
            return false;
        } else if (genderId == 0){
            DialogsHelper.showTextError(binding.genderType,this);
            return false;
        } else if (birthDay == null){
            DialogsHelper.showTextError(binding.birthDay,this);
            return false;
        } else if (TextUtils.isEmpty(binding.password.getText())){
            DialogsHelper.showTextError(binding.password,this);
            return false;
        } else if (TextUtils.isEmpty(binding.rePassword.getText())){
            DialogsHelper.showTextError(binding.rePassword,this);
            return false;
        } else if (cityId == 0){
            DialogsHelper.showSpinnerError(binding.cityLayout,this);
            return false;
        }
        return true;
    }
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                try {
                    updateLabel();
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                } catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        };
         @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK && data != null) {
                uri = data.getData();
                        if (uri != null) {
                            binding.image.setImageURI(uri);
                            binding.image.setVisibility(View.VISIBLE);

                        }
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            }
            }


        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent in = new Intent(this,SignInActivity.class);
            startActivity(in);
            finish();
        }

        private Bitmap getBitmap(String filePath) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            //            bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
            return BitmapFactory.decodeFile(filePath, bmOptions);
        }

        String imagePath;
        private Uri getImageUri(Context inContext, Bitmap inImage) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(),  null);
            imagePath = path;
            return Uri.parse(path);
        }

    String passengerType;
    private void setUpAgeSpinner(Spinner spinners) {
        ArrayList<String> NameList = new ArrayList<>();
        NameList.add(getString(R.string.choose_age));
        NameList.add(getString(R.string.adults));
        NameList.add(getString(R.string.kids));
        NameList.add(getString(R.string.babys));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.passengerTypeLayout.setBackground(ContextCompat.getDrawable(
                            RegisterActivity.this
                            ,R.drawable.white_background));
                    binding.passengerType.setText(NameList.get(position));
                    if (position == 1){
                        passengerType = "adult";
                    } else if (position ==2){
                        passengerType = "child";
                    } else if (position ==3){
                        passengerType = "baby";
                    }
                } else {
                    binding.passengerType.setText(getString(R.string.choose_age));
                    passengerType = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void checkPermission() {
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("من فضلك قم بالسماح بتصريح الذاكرة لتتمكن من ارفاق صورة جواز السفر")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            ImagePicker.with(RegisterActivity.this).galleryOnly().start();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
//            Toast.makeText(requireActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


        private void updateLabel() {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            birthDay = sdf.format(myCalendar.getTime());
            binding.birthDay.setText(birthDay);
        }


        private void setUpGenderSpinner(Spinner spinners, final RegisterCollectionResponse initialData) {
            ArrayList<String> NameList = new ArrayList<>();
             NameList.add(getString(R.string.choose_gender));
            NameList.add(getString(R.string.male));
            NameList.add(getString(R.string.female));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, NameList);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            spinners.setAdapter(adapter);
            spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                             binding.genderLayout.setBackground(ContextCompat.getDrawable(
                                     RegisterActivity.this,R.drawable.white_background));
                             try {
                                 binding.genderType.setText(NameList.get(position));
                             }catch (IndexOutOfBoundsException e){
                                 e.printStackTrace();
                             }
                        genderId = position;
                     } else {
                             binding.genderType.setText(getString(R.string.choose_gender));
                        genderId = 0;
                     }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        private void setUpNationalitySpinner(Spinner spinners, final RegisterCollectionResponse initialData) {
            ArrayList<String> NameList = new ArrayList<>();
            NameList.add(getString(R.string.choose_nationality));
            NameList.addAll(HandleNationalityData(initialData));


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, NameList);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            spinners.setAdapter(adapter);
            spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        binding.nationalityLayout.setBackground(ContextCompat.getDrawable(
                                RegisterActivity.this,R.drawable.white_background));
                        try {
                            binding.nationality.setText(NameList.get(position));
                        } catch (IndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                        countryId = initialData.getMrt7al().getData().getNationalities().get(position-1).getId();
                    } else {
                        binding.nationality.setText(getString(R.string.choose_nationality));
                        countryId = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        private void setUpCitySpinner(Spinner spinners, final RegisterCollectionResponse initialData) {
            ArrayList<String> NameList = new ArrayList<>();
            NameList.add(getString(R.string.choose_city));
            NameList.addAll(HandleCityData(initialData));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, NameList);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            spinners.setAdapter(adapter);
            spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        binding.cityLayout.setBackground(ContextCompat.getDrawable(
                                RegisterActivity.this,R.drawable.white_background));
                        try {
                            binding.city.setText(NameList.get(position));
                        } catch (IndexOutOfBoundsException e){
                            e.printStackTrace();
                        }

                        cityId = initialData.getMrt7al().getData().getCities().get(position-1).getId();
                    } else {
                        binding.city.setText(getString(R.string.choose_city));
                        cityId = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        private ArrayList<String> HandleNationalityData(RegisterCollectionResponse response){
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i=0; i<response.getMrt7al().getData().getNationalities().size();i++){
                arrayList.add(response.getMrt7al().getData().getNationalities().get(i).getName());
            }
            return arrayList;
        }
        private ArrayList<String> HandleCityData(RegisterCollectionResponse response){
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i=0; i<response.getMrt7al().getData().getCities().size();i++){
                arrayList.add(response.getMrt7al().getData().getCities().get(i).getName());
            }
            return arrayList;
        }
        @Override
        public void onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse) {
            DialogsHelper.removeProgressDialog();
            if (isSuccess){
                Gson gson = new Gson();
                String model = gson.toJson(registerCollectionResponse,RegisterCollectionResponse.class);
                new PreferenceHelper(RegisterActivity.this).setGROUPID(model);
                setUpNationalitySpinner(binding.nationalitySpinner,registerCollectionResponse);
                setUpGenderSpinner(binding.genderSpinner,registerCollectionResponse);
                setUpCitySpinner(binding.citySpinner,registerCollectionResponse);
                setUpAgeSpinner(binding.passengerTypeSpinner);
            }
        }

        @Override
        public void onResponse(boolean isSuccess, RegisterResponse loginResponse) {

             if (isSuccess){
                 try {
                     imagePath= PathUtil.getPath(this,uri);
                 } catch (URISyntaxException | NumberFormatException | NullPointerException e) {
                     e.printStackTrace();
                 }

                try {
                    Gson gson = new Gson();
                    LoginResponse loginResponse1 = LoginResponse.getInstance();
                    LoginResponse.Mrt7alBean bean = new LoginResponse.Mrt7alBean();
                    LoginResponse.Mrt7alBean.DataBean dataBean = new LoginResponse.Mrt7alBean.DataBean();
                    dataBean.setCity_id(loginResponse.getMrt7al().getData().getCity_id());
                    dataBean.setCreated(loginResponse.getMrt7al().getData().getCreated());
                    dataBean.setDate_of_birth(loginResponse.getMrt7al().getData().getDate_of_birth());
                    dataBean.setUsername(loginResponse.getMrt7al().getData().getUsername());
                    dataBean.setGender(loginResponse.getMrt7al().getData().getGender());
                    dataBean.setId(loginResponse.getMrt7al().getData().getId());
                    dataBean.setMobile(loginResponse.getMrt7al().getData().getMobile());
                    dataBean.setNationality_id(loginResponse.getMrt7al().getData().getNationality_id());
                    dataBean.setPassport_file(loginResponse.getMrt7al().getData().getPassport_file());
                    dataBean.setPassport_id(loginResponse.getMrt7al().getData().getPassport_id());
                    dataBean.setPassangerType(loginResponse.getMrt7al().getData().getPassangerType());
                    dataBean.setToken(loginResponse.getMrt7al().getData().getToken());
                    dataBean.setPassword(binding.password.getText().toString());
                    dataBean.setUser_group_id(loginResponse.getMrt7al().getData().getUser_group_id());
                    bean.setData(dataBean);
                    loginResponse1.setMrt7al(bean);
                    String json = gson.toJson(loginResponse1);
                    new PreferenceHelper(this).setUSERNAME(json);
                    new PreferenceHelper(this).setUSERID(loginResponse.getMrt7al().getData().getId());
                    new PreferenceHelper(this).setTOKEN(loginResponse.getMrt7al().getData().getToken());
                    showSuccessDialog();
                    if(imagePath != null) {
                        File target = new File(imagePath);
                         if (target.exists() && target.isFile() && target.canWrite()) {
                            boolean isDeleted = target.delete();
                            if (isDeleted) {
//                                    Toast.makeText(this, "imageDeleted", Toast.LENGTH_SHORT).show();
                            } else {
//                                    Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
                            }
                            Log.d("d_filed_file", "" + target.getName());
                        }
                    }
                } catch (NullPointerException | IllegalStateException e){
                    e.printStackTrace();
                 }
             } else {
                     DialogsHelper.showErrorDialog(getString(R.string.phone_exists),RegisterActivity.this);
             }
            try{
            binding.loginProgress.setVisibility(View.VISIBLE);
            binding.register.setVisibility(View.GONE);
            DialogsHelper.disable(binding.wholeView, true);
            binding.phoneNumber.setEnabled(false);
            } catch (NullPointerException | IllegalStateException e){
                e.printStackTrace();
            }
        }

        public void showSuccessDialog() {
            final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            RegisteraionCompletedDialogBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.registeraion_completed_dialog, null, false);
            registerErrorDialogBinding.okButton.setOnClickListener(view -> {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
                dialog.cancel();
            });
            dialog.setContentView(registerErrorDialogBinding.getRoot());
            if(!RegisterActivity.this.isFinishing()){
                dialog.show();
            }
        }
        private ErrorResponse errorResponse;
        @Override
        public void handleError(Throwable t) {
            try{
            DialogsHelper.removeProgressDialog();
            if (ConnectivityReceiver.isConnected()){
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch ( IOException | JsonSyntaxException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        Toast.makeText(this, errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "صورة الجواز غير صالحة من فضلك اختر صورة اخري", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();


            } catch (NullPointerException | IllegalStateException e){
                e.printStackTrace();
            }
            binding.loginProgress.setVisibility(View.GONE);
            binding.register.setVisibility(View.VISIBLE);
            DialogsHelper.disable(binding.wholeView,true);
           // binding.phoneNumber.setEnabled(false);

        }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}