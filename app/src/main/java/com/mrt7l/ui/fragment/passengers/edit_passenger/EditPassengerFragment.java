package com.mrt7l.ui.fragment.passengers.edit_passenger;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.EditPassengerFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.FilePath;
import com.mrt7l.helpers.PathUtil;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
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

public class EditPassengerFragment extends Fragment implements EditPassengersInterface {

    private EditPassengerViewModel mViewModel;
    private String birthDay;
    private Calendar myCalendar;
    private Uri uri;
    private Bitmap imageBitmap;
    private int genderId = 0,countryId = 0;
    private LoginResponse loginResponse;
    private PassengersResponse.Mrt7alBean.DataBean bean;
    public static EditPassengerFragment newInstance() {
        return new EditPassengerFragment();
    }
    private EditPassengerFragmentBinding binding;
    private String token;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            uri = result.getData().getData();
                            binding.image.setImageURI(uri);
                        }
                    }
                });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.edit_passenger_fragment, container, false);
       token = new PreferenceHelper(requireActivity()).getTOKEN();
        Gson gson = new Gson();
        assert getArguments() != null;
        bean = gson.fromJson(getArguments().getString("model"),PassengersResponse.Mrt7alBean.DataBean.class);
        binding.fullName.setText(bean.getFull_name());
        binding.birthDay.setText(bean.getDate_of_birth());
        binding.phoneNumber.setText(bean.getMobile());
        binding.passportNumber.setText(bean.getPassport_id());
         mViewModel = new ViewModelProvider(this).get(EditPassengerViewModel.class);
        mViewModel.init(requireActivity(),this);
        binding.savePassenger.setOnClickListener(view -> {
            if (birthDay == null){
                birthDay = binding.birthDay.getText().toString();
            }
            if (checkInputs()) {
                if (loginResponse.getMrt7al() != null) {

                    binding.savePassenger.setVisibility(View.GONE);
                    binding.confirmProgress.setVisibility(View.VISIBLE);
                    DialogsHelper.disable(binding.wholeView, false);
                    String phone = binding.phoneNumber.getText().toString();
                    if (uri != null) {
                        mViewModel.EditPassenger(createPartFromString(binding.fullName.getText().toString()),
                                String.valueOf(bean.getId()), createPartFromString(
                                        String.valueOf(countryId)), createPartFromString(String.valueOf(genderId)),
                                createPartFromString(birthDay), createPartFromString(binding.passportNumber.getText().toString()),
                                createPartFromString(phone),
                                prepareFilePart(uri.toString())
                                , token);
                    } else {

                        mViewModel.EditPassenger(createPartFromString(binding.fullName.getText().toString()),
                                String.valueOf(bean.getId()), createPartFromString(
                                        String.valueOf(countryId)), createPartFromString(String.valueOf(genderId)),
                                createPartFromString(birthDay), createPartFromString(binding.passportNumber.getText().toString()),
                                createPartFromString(phone),
                                 token);
                    }
                }
            }
        });
         loginResponse = LoginResponse.getInstance();
        loginResponse = gson.fromJson(new PreferenceHelper(requireActivity()).getUSERNAME(),LoginResponse.class);
        binding.birthDay.setOnClickListener(view -> {
            myCalendar = Calendar.getInstance();
            new DatePickerDialog(requireActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.uploadImage.setOnClickListener(view -> checkPermission());
        if (bean.getPassport_file() != null && !bean.getPassport_file().isEmpty()) {
                Glide.with(requireActivity()).asBitmap().load("https://administrator.mrt7al.com/" +
                        bean.getPassport_file())
                        .into(new CustomTarget<Bitmap>() {


                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                        try {
                            binding.image.setImageBitmap(resource);
                        }catch (RuntimeException e){
                            e.printStackTrace();
                        }
                        uri = getImageUri(requireActivity(),resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

                    }

                });
            }


            binding.image.setVisibility(View.VISIBLE);

        binding.nationality.setOnClickListener(view -> binding.nationalitySpinner.performClick());
        binding.genderType.setOnClickListener(view -> binding.genderSpinner.performClick());
        binding.fullName.requestFocus();
        binding.cancel.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
            navController.navigateUp();
        });
        return binding.getRoot();
    }
    private RequestBody createPartFromString(String param) {
        return RequestBody.create(param,MediaType.parse("multipart/form-data"));
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
                    binding.ageLayout.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.edit_text_background));
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
        if (bean.getPassangerType().equals("adult")){
            binding.ageSpinner.setSelection(1);
        } else if (bean.getPassangerType().equals("child")){
            binding.ageSpinner.setSelection(2);
        } else if (bean.getPassangerType().equals("baby")){
            binding.ageSpinner.setSelection(3);
        }
    }

    private MultipartBody.Part prepareFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(requireActivity(), Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,MediaType.parse(Objects.requireNonNull
                    (requireActivity().getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("passport_img", file.getName(), requestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

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
        } else  if (uri == null){
            DialogsHelper.showErrorDialog(getString(R.string.upload_passport),requireActivity());
            return false;
//        } else  if (passengerType == null){
//            DialogsHelper.showSpinnerError(binding.ageLayout,requireActivity());
//            Toast.makeText(requireActivity(), getString(R.string.choose_passenger_type), Toast.LENGTH_SHORT).show();
//            return false;
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


//    @Override
//    public void registerForActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.AVATARREQUEST && data != null) {
//            ArrayList<String> avatarList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//            if (avatarList != null && avatarList.size() > 0) {
//                Bitmap bitmap = getBitmap(avatarList.get(0));
//                if (bitmap != null) {
//                    imageBitmap = bitmap;
//                    Picasso.get().load(Uri.parse(uri.toString()))
//                            .error(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.cameras)))
//                            .into(binding.image);
//                    binding.image.setVisibility(View.VISIBLE);
//                }
//             }
//        }
//    }

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
    ActivityResultLauncher<Intent> mLauncher;
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
            triggerChooser();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
//            Toast.makeText(requireActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


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


    private void setUpGenderSpinner(Spinner spinners, final RegisterCollectionResponse initialData) {
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
                    binding.genderLayout.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.edit_text_background));
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
        if (bean.getGender().equals("انثى")){
            binding.genderSpinner.setSelection(2);
        } else {
            binding.genderSpinner.setSelection(1);
        }
        setUpAgeSpinner(binding.ageSpinner);
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
                    binding.nationalityLayout.setBackground(ContextCompat.getDrawable(requireActivity(),R.drawable.edit_text_background));
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

        for (int i=0;i<initialData.getMrt7al().getData().getNationalities().size();i++){
            if (bean.getNationality_id() == initialData.getMrt7al().getData().getNationalities().get(i).getId()){
                binding.nationalitySpinner.setSelection(i+1);
                break;
            }
        }
        setUpGenderSpinner(binding.genderSpinner,initialData);

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
        }
    }
    String imagePath;
    @Override
    public void onResponse(boolean isSuccess, EditPassengersResponse registerResponse) {
        if (isSuccess){
            try {
                imagePath= PathUtil.getPath(requireContext(),uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            new PreferenceHelper(requireActivity()).setReloadProfile(true);
            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
            navController.navigateUp();
            Toast.makeText(requireActivity(), getString(R.string.edited_successfully)
                    , Toast.LENGTH_SHORT).show();
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
        } else {
            binding.savePassenger.setVisibility(View.VISIBLE);
            binding.confirmProgress.setVisibility(View.GONE);
            DialogsHelper.showErrorDialog(registerResponse.getMrt7al().getMsg(),requireActivity());
        }
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
        for (int i = 0; i < binding.wholeView.getChildCount(); i++) {
            View child = binding.wholeView.getChildAt(i);
            child.setEnabled(true);
        }
        binding.confirmProgress.setVisibility(View.GONE);
        binding.savePassenger.setVisibility(View.VISIBLE);
    }

}