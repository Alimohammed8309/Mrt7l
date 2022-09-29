package com.mrt7l.ui.fragment.passengers;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.AddPassengerFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.FilePath;
import com.mrt7l.helpers.PathUtil;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class AddPassengerFragment extends Fragment implements AddPassengersInterface {

    private AddPassengerViewModel viewModel;
    AddPassengerFragmentBinding binding;

    private String birthDay;
    private Calendar myCalendar;
    private Uri uri;
    private String token;
    private int genderId = 0,countryId = 0;
    private LoginResponse loginResponse;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        uri = result.getData().getData();
                        binding.image.setImageURI(uri);
                        binding.image.setVisibility(View.VISIBLE);
                    }

                });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityResultLauncher = registerForActivityResult(new
                        ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Log.e("activityResultLauncher", ""+result.toString());
                    boolean areAllGranted = true;
                    for(Boolean b : result.values()) {
                        areAllGranted = areAllGranted && b;
                    }

                    if(areAllGranted) {
                        triggerChooser();
                    } else {
                        Toast.makeText(requireActivity(), "من فضلك قم بقبول التصريح حتي تستطيع من اضافة صورة الجواز", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_passenger_fragment,container,false);
        viewModel = new ViewModelProvider(this).get(AddPassengerViewModel.class);
        viewModel.init(getActivity(),this);
        token = new PreferenceHelper(requireActivity()).getTOKEN();
        Gson gson = new Gson();
        loginResponse = LoginResponse.getInstance();
        loginResponse = gson.fromJson(new PreferenceHelper(requireActivity()).getUSERNAME(),LoginResponse.class);
        binding.birthDay.setOnClickListener(view -> {
            myCalendar = Calendar.getInstance();
            new DatePickerDialog(requireActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.uploadImage.setOnClickListener(view -> checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,33));
        binding.nationality.setOnClickListener(view -> binding.nationalitySpinner.performClick());
        binding.genderType.setOnClickListener(view -> binding.genderSpinner.performClick());
        binding.fullName.requestFocus();
        setUpAgeSpinner(binding.ageSpinner);
        binding.savePassenger.setOnClickListener(view -> {
            if (checkInputs()) {
                if (loginResponse.getMrt7al() != null) {
//                     uri = getImageUri(requireContext(),imageBitmap);

                    if (uri != null) {
                        binding.savePassenger.setVisibility(View.GONE);
                        binding.confirmProgress.setVisibility(View.VISIBLE);
                        DialogsHelper.disable(binding.wholeView, false);
                        String phone = binding.phoneNumber.getText().toString();
                        viewModel.AddPassenger(token, createPartFromString(binding.fullName.getText().toString()),
                                createPartFromString(String.valueOf(countryId)),
                                createPartFromString(String.valueOf(genderId)),
                                createPartFromString(birthDay), createPartFromString(
                                        binding.passportNumber.getText().toString()),
                                createPartFromString(phone)
                                , prepareFilePart(uri.toString()));
                    } else {
                        Toast.makeText(requireActivity(), getString(R.string.upload_passport), Toast.LENGTH_SHORT).show();
                        binding.savePassenger.setVisibility(View.VISIBLE);
                        binding.confirmProgress.setVisibility(View.GONE);
                        //                        viewModel.AddPassenger(token, createPartFromString(binding.fullName.getText().toString()),
//                                createPartFromString(String.valueOf(countryId)),
//                                createPartFromString(String.valueOf(genderId)),
//                                createPartFromString(birthDay), createPartFromString(
//                                        binding.passportNumber.getText().toString()),
//                                createPartFromString(phone)
//                        );
                    }
                }
            }
        });


        return binding.getRoot();
    }
    private RequestBody createPartFromString(String param) {
        return RequestBody.create(param,MediaType.parse("multipart/form-data"));
    }
    private MultipartBody.Part prepareFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(requireActivity(), Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,
                    MediaType.parse(Objects.requireNonNull(requireActivity().getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("passport_img", file.getName(), requestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    ActivityResultLauncher<Intent> mLauncher;

    private boolean checkInputs(){
        if (TextUtils.isEmpty(binding.fullName.getText())){
            DialogsHelper.showTextError(binding.fullName,requireActivity());
            Toast.makeText(requireActivity(), getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            return false;
        }  else if (countryId == 0){
            DialogsHelper.showSpinnerError(binding.nationalityLayout,requireActivity());
            Toast.makeText(requireActivity(), getString(R.string.enter_nationality), Toast.LENGTH_SHORT).show();
            return false;
        } else if (genderId == 0){
            DialogsHelper.showSpinnerError(binding.genderLayout,requireActivity());
            Toast.makeText(requireActivity(), getString(R.string.enter_gender), Toast.LENGTH_SHORT).show();
            return false;
        } else if (birthDay == null){
            DialogsHelper.showTextError(binding.birthDay,requireActivity());
            Toast.makeText(requireActivity(), getString(R.string.enter_birthday), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.passportNumber.getText())){
            DialogsHelper.showTextError(binding.passportNumber,requireActivity());
            Toast.makeText(requireActivity(), getString(R.string.enter_passport), Toast.LENGTH_SHORT).show();
            return false;
        }
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
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
//        bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
        return bitmap;
    }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(),  null);
        return Uri.parse(path);
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(requireActivity(), permission) ==
                PackageManager.PERMISSION_DENIED) {
            String[] appPerms;
            appPerms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            this.activityResultLauncher.launch(appPerms);
        } else {
            triggerChooser();
        }
        // Checking if permission is not granted
//        if (ContextCompat.checkSelfPermission(requireActivity(), permission) ==
//                PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(requireActivity(), new String[] {
//                    permission }, requestCode);
//
//            return  false;
//        }
//        else {
//            Toast.makeText(requireActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
//        return  true;
//        }
    }

    private ActivityResultLauncher<String[]> activityResultLauncher;



    private void triggerChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mLauncher.launch(intent);
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthDay = sdf.format(myCalendar.getTime());
        binding.birthDay.setText(birthDay);
    }


    private void setUpGenderSpinner(Spinner spinners ) {
        ArrayList<String> NameList = new ArrayList<>();
        NameList.add(getString(R.string.choose_gender));
        NameList.add(getString(R.string.male));
        NameList.add(getString(R.string.female));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.genderLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.genderType.setText(NameList.get(position));
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


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.nationalityLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.nationality.setText(NameList.get(position));
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
    String passengerType;
    private void setUpAgeSpinner(Spinner spinners) {
        ArrayList<String> NameList = new ArrayList<>();
        NameList.add(getString(R.string.choose_age));
        NameList.add(getString(R.string.adults));
        NameList.add(getString(R.string.kids));
        NameList.add(getString(R.string.babys));


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.ageLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.ageText.setText(NameList.get(position));
                    if (position == 1){
                        passengerType = "adult";
                    } else if (position ==2){
                        passengerType = "child";
                    } else if (position ==3){
                        passengerType = "baby";
                    }
                } else {
                    binding.ageText.setText(getString(R.string.choose_age));
                    passengerType = null;
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

    @Override
    public void onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse) {
        if (isSuccess){
            setUpNationalitySpinner(binding.nationalitySpinner,registerCollectionResponse);
            setUpGenderSpinner(binding.genderSpinner);
        }
    }
    String imagePath;

    @Override
    public void onResponse(boolean isSuccess, AddPassengerResponse registerResponse) {

        if (isSuccess){
            DialogsHelper.disable(binding.wholeView, true);
            Toast.makeText(requireActivity(), getString(R.string.passenger_added)
                    , Toast.LENGTH_SHORT).show();
            new PreferenceHelper(requireActivity()).setReloadProfile(true);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment);
            navController.navigateUp();
            try {
                imagePath= PathUtil.getPath(requireContext(),uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if(imagePath != null) {
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
                } catch (UnsupportedOperationException ignored){
                }
            }
        }
        DialogsHelper.disable(binding.wholeView, true);

    }

    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        DialogsHelper.disable(binding.wholeView, true);

        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else if (code == 404) {
                    Log.v("404", "my booking fragment page not found");

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
        binding.savePassenger.setVisibility(View.VISIBLE);
        binding.confirmProgress.setVisibility(View.GONE);
        for (int i = 0; i < binding.wholeView.getChildCount(); i++) {
            View child = binding.wholeView.getChildAt(i);
            child.setEnabled(true);
        }
    }

}