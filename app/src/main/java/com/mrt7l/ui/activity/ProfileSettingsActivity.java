package com.mrt7l.ui.activity;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.ActivityProfileBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.FilePath;
 import com.mrt7l.helpers.PathUtil;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class ProfileSettingsActivity extends BaseActivity  implements ProfileInterface {
    ProfileViewModel viewModel;
    private ActivityProfileBinding binding;
    private String birthDay,token;
    private Calendar myCalendar;
    private Uri uri;
    private Bitmap imageBitmap;
    private LoginResponse response;
    private int genderId = 0, countryId = 0, cityId = 0,userId;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        SetNotificationImage(binding.notificationLayout.ivNotification);
        binding.mainProgress.setVisibility(View.VISIBLE);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        token = new PreferenceHelper(this).getTOKEN();
        userId = new PreferenceHelper(this).getUSERID();
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        this.uri = uri;
                        binding.passportImage.setImageURI(uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        Gson gson = new Gson();
        response = LoginResponse.getInstance();
        response = gson.fromJson(new PreferenceHelper(this).getUSERNAME(),LoginResponse.class);
        viewModel.token = token;
        viewModel.userId = String.valueOf(userId);
        viewModel.init(this, this);
        binding.birthDay.setOnClickListener(view -> {
            myCalendar = Calendar.getInstance();
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.ivBack.setOnClickListener(view -> finish());
        binding.uploadImage.setOnClickListener(view ->
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build()));
        binding.btnSave.setOnClickListener(view -> {
            binding.confirmProgress.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.GONE);
            if (birthDay == null && !binding.birthDay.getText().toString().isEmpty()){
                birthDay = binding.birthDay.getText().toString();
            }
            if (checkInputs()) {
                DialogsHelper.disable(binding.wholeView,false);
//                try {
//                    uri = getImageUri(ProfileSettingsActivity.this,imageBitmap);
//                }catch (NullPointerException e){
//                    //e.printStackTrace();
//                }
                if (imageBitmap != null && uri == null) {
                    uri = getImageUri(ProfileSettingsActivity.this, imageBitmap);
                }

                if (uri !=null) {
                    viewModel.edit("Bearer " + token, userId,
                            createPartFromString(binding.firstName.getText().toString())
                            , createPartFromString(String.valueOf(countryId)),
                            createPartFromString(String.valueOf(genderId)),
                            createPartFromString(birthDay),
                            createPartFromString(binding.idNumber.getText().toString()),
                            createPartFromString(binding.passwordEt.getText().toString()),
                            createPartFromString(String.valueOf(cityId)),
                            prepareFilePart(uri.toString())
                    );

                } else {
                    viewModel.edit("Bearer " + token, userId,
                            createPartFromString(binding.firstName.getText().toString())
                            , createPartFromString(String.valueOf(countryId)),
                            createPartFromString(String.valueOf(genderId)),
                            createPartFromString(birthDay),
                            createPartFromString(binding.idNumber.getText().toString()),
                            createPartFromString(binding.passwordEt.getText().toString()),
                            createPartFromString(String.valueOf(cityId))
                    );
                }
            }
        });

        binding.nationality.setOnClickListener(view -> binding.nationalitySpinner.performClick());
        binding.genderType.setOnClickListener(view -> binding.genderSpinner.performClick());
        binding.city.setOnClickListener(view -> binding.citySpinner.performClick());



    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
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
                            ProfileSettingsActivity.
                                    this, R.drawable.bg_square));
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
        switch (response.getMrt7al().getData().getPassangerType()) {
            case "adult":
                binding.passengerTypeSpinner.setSelection(1);
                break;
            case "child":
                binding.passengerTypeSpinner.setSelection(2);
                break;
            case "baby":
                binding.passengerTypeSpinner.setSelection(3);
                break;
        }
    }

    private RequestBody createPartFromString(String param) {
        return RequestBody.create(param,MediaType.parse("multipart/form-data"));
    }

    private MultipartBody.Part prepareFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(this, Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,MediaType.parse(Objects.requireNonNull
                    (getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("profilePhoto", file.getName(), requestFile);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    private boolean checkInputs() {
        if (TextUtils.isEmpty(binding.firstName.getText())) {
            DialogsHelper.showTextError(binding.firstName, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        } else if (countryId == 0) {
            DialogsHelper.showSpinnerError(binding.nationalityLayout, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        } else if (genderId == 0) {
            DialogsHelper.showSpinnerError(binding.genderType, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        } else if (birthDay == null) {
            DialogsHelper.showTextError(binding.birthDay, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
//        } else if (passengerType == null) {
//            DialogsHelper.showTextError(binding.passengerType, this);
//            binding.confirmProgress.setVisibility(View.GONE);
//            binding.btnSave.setVisibility(View.VISIBLE);
//            return false;
        } else if (TextUtils.isEmpty(binding.idNumber.getText())) {
            DialogsHelper.showTextError(binding.idNumber, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        } else if (TextUtils.isEmpty(binding.passwordEt.getText())) {
            DialogsHelper.showTextError(binding.passwordEt, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        } else if (cityId == 0) {
            DialogsHelper.showSpinnerError(binding.cityLayout, this);
            binding.confirmProgress.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
            return false;
        }
//        else if (uri == null) {
//            DialogsHelper.showErrorDialog(getString(R.string.upload_passport), this);
//            binding.confirmProgress.setVisibility(View.GONE);
//            binding.btnSave.setVisibility(View.VISIBLE);
//            return false;
//        }
        return true;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };



    private Bitmap getBitmap(String filePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        //        bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
        return BitmapFactory.decodeFile(filePath, bmOptions);
    }


    String imagePath;
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        //        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(),  null);
        imagePath = path;
        return Uri.parse(path);
    }
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
                    binding.genderType.setBackground(ContextCompat.getDrawable(ProfileSettingsActivity.this, R.drawable.bg_square));
                    binding.genderText.setText(NameList.get(position));
                    genderId = position;
                } else {
                    binding.genderText.setText(getString(R.string.choose_gender));
                    genderId = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (response.getMrt7al().getData().getGender().equals("ذكر")){
            binding.genderSpinner.setSelection(1);
        } else {
            binding.genderSpinner.setSelection(2);
        }
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
                    binding.nationalityLayout.setBackground(ContextCompat.getDrawable(ProfileSettingsActivity.this, R.drawable.bg_square));
                    binding.nationality.setText(NameList.get(position));
                    countryId = initialData.getMrt7al().getData().getNationalities().get(position - 1).getId();
                } else {
                    binding.nationality.setText(getString(R.string.choose_nationality));
                    countryId = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i=0;i<initialData.getMrt7al().getData().getNationalities().size();i++){
            if (response.getMrt7al().getData().getNationality_id() == initialData.getMrt7al().getData().getNationalities().get(i).getId()){
                binding.nationalitySpinner.setSelection(i+1);
                break;
            }
        }
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
                    binding.cityLayout.setBackground(ContextCompat.getDrawable(ProfileSettingsActivity.this, R.drawable.bg_square));
                    binding.city.setText(NameList.get(position));
                    cityId = initialData.getMrt7al().getData().getCities().get(position - 1).getId();
                } else {
                    binding.city.setText(getString(R.string.choose_city));
                    cityId = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i=0;i<initialData.getMrt7al().getData().getCities().size();i++){
            if (response.getMrt7al().getData().getCity_id() == initialData.getMrt7al().getData().getCities().get(i).getId()){
                binding.citySpinner.setSelection(i+1);
                break;
            }
        }
    }

    private ArrayList<String> HandleNationalityData(RegisterCollectionResponse response) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (response.getMrt7al() != null)
            if (response.getMrt7al().getData() != null)
                for (int i = 0; i < response.getMrt7al().getData().getNationalities().size(); i++) {
                    arrayList.add(response.getMrt7al().getData().getNationalities().get(i).getName());
                }
        return arrayList;
    }

    private ArrayList<String> HandleCityData(RegisterCollectionResponse response) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (response.getMrt7al() != null)
            if (response.getMrt7al().getData() != null)
                for (int i = 0; i < response.getMrt7al().getData().getCities().size(); i++) {
                    arrayList.add(response.getMrt7al().getData().getCities().get(i).getName());
                }
        return arrayList;
    }

    @Override
    public void onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse) {
        if (isSuccess) {
            setUpNationalitySpinner(binding.nationalitySpinner, registerCollectionResponse);
            setUpGenderSpinner(binding.genderSpinner, registerCollectionResponse);
            setUpCitySpinner(binding.citySpinner, registerCollectionResponse);
            setUpAgeSpinner(binding.passengerTypeSpinner);
        }
    }

    @Override
    public void onResponse(boolean isSuccess, EditProfileResponse loginResponse) {
        if (isSuccess) {
            try {
                imagePath = PathUtil.getPath(ProfileSettingsActivity.this, uri);
            } catch (URISyntaxException e) {
                //e.printStackTrace();
            }
            assert imagePath != null;
            File target = new File(imagePath);
            try {
                if (target.exists() && target.isFile() && target.canWrite()) {
                    boolean isDeleted = target.delete();
                    if (isDeleted) {
                        //                                    Toast.makeText(this, "imageDeleted", Toast.LENGTH_SHORT).show();
                    } else {
//                                    Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
                    }
                }
//                if (uri != null)
//                    getContentResolver().delete(uri, null, null);

            } catch (UnsupportedOperationException ignored){
            }

            Gson gson = new Gson();
            LoginResponse loginResponse1 = LoginResponse.getInstance();
            LoginResponse.Mrt7alBean bean = new LoginResponse.Mrt7alBean();
            LoginResponse.Mrt7alBean.DataBean dataBean = new LoginResponse.Mrt7alBean.DataBean();
            dataBean.setCity_id(loginResponse.getMrt7al().getData().getCity_id());
            dataBean.setUsername(loginResponse.getMrt7al().getData().getUsername());
            dataBean.setPassword(loginResponse.getMrt7al().getData().getConfirm_password());
            dataBean.setCreated(loginResponse.getMrt7al().getData().getCreated());
            dataBean.setDate_of_birth(loginResponse.getMrt7al().getData().getDate_of_birth());
            dataBean.setGender(loginResponse.getMrt7al().getData().getGender());
            dataBean.setId(Integer.parseInt(loginResponse.getMrt7al().getData().getId()));
            dataBean.setMobile(loginResponse.getMrt7al().getData().getMobile());
            dataBean.setPassport_file(loginResponse.getMrt7al().getData().getPassport_file());
            dataBean.setPassport_id(loginResponse.getMrt7al().getData().getPassport_id());
            dataBean.setToken(token);
            dataBean.setPassangerType(loginResponse.getMrt7al().getData().getPassangerType());
            dataBean.setUser_group_id(loginResponse.getMrt7al().getData().getUser_group_id());
            dataBean.setNationality_id(loginResponse.getMrt7al().getData().getNationality_id());
            bean.setData(dataBean);
            loginResponse1.setMrt7al(bean);
            String json = gson.toJson(loginResponse1);
            new PreferenceHelper(this).setUSERNAME(json);
            //            new PreferenceHelper(this).setUSERID(loginResponse.getMrt7al().getData().getId());
//            new PreferenceHelper(this).setTOKEN(loginResponse.getMrt7al().getData().getToken());
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
        } else {
            binding.btnSave.setVisibility(View.VISIBLE);
            binding.confirmProgress.setVisibility(View.GONE);
            DialogsHelper.disable(binding.wholeView,true);
//                 if (loginResponse.getMrt7al().getMsgs().getMobile()!= null){
//                     DialogsHelper.showErrorDialog("رقم الجوال مستخدم من قبل",RegisterActivity.this);
//                 } else  if (loginResponse.getMrt7al().getMsgs().getPassportId()!= null){
//                     DialogsHelper.showErrorDialog("رقم جواز السفر مستخدم من قبل",RegisterActivity.this);
//                 } else {
            DialogsHelper.showErrorDialog("خطأ بالبيانات", ProfileSettingsActivity.this);
//                 }
        }

    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        try {
            imagePath = PathUtil.getPath(ProfileSettingsActivity.this, uri);
        } catch (URISyntaxException e) {
            //e.printStackTrace();
        }
        assert imagePath != null;
        File target = new File(imagePath);
        try {
            if (target.exists() && target.isFile() && target.canWrite()) {
                boolean isDeleted = target.delete();
                if (isDeleted) {
                    //                                    Toast.makeText(this, "imageDeleted", Toast.LENGTH_SHORT).show();
                } else {
//                                    Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
                }
            }
//            if (uri != null)
//                getContentResolver().delete(uri, null, null);
        } catch (UnsupportedOperationException ignored){
        }

        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), this);
                } else if (code == 404) {
                    try {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();

                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException | IllegalStateException | NullPointerException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        Toast.makeText(this, errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
        binding.btnSave.setVisibility(View.VISIBLE);
        binding.confirmProgress.setVisibility(View.GONE);
        for (int i = 0; i < binding.wholeView.getChildCount(); i++) {
            View child = binding.wholeView.getChildAt(i);
            child.setEnabled(true);
        }


    }

    @Override
    public void onGetProfileData(boolean isSuccess, ProfileResponse profileResponse) {
        binding.firstName.setText(profileResponse.getMrt7al().getData().getUsername());
        binding.birthDay.setText(profileResponse.getMrt7al().getData().getDate_of_birth());
        binding.idNumber.setText(profileResponse.getMrt7al().getData().getPassport_id());
//        binding.passwordEt.setText(profileResponse.getMrt7al().getData().getPassword());


        Glide.with(this).asBitmap().load("https://administrator.mrt7al.com/" +
                profileResponse.getMrt7al().getData().getPassport_file()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                binding.passportImage.setImageBitmap(resource);
                imageBitmap = resource;

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        binding.mainProgress.setVisibility(View.GONE);
        binding.allData.setVisibility(View.VISIBLE);
    }
}

