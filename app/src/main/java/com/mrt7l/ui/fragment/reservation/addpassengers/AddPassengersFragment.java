package com.mrt7l.ui.fragment.reservation.addpassengers;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.mrt7l.R;
import com.mrt7l.databinding.AddPassengerReservationDialogBinding;
import com.mrt7l.databinding.DeletePassengerDialogBinding;
import com.mrt7l.databinding.EnterPassportDialogBinding;
import com.mrt7l.databinding.ReservationPassengersBinding;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.FilePath;
import com.mrt7l.helpers.PathUtil;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.activity.SignInActivity;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.reservation.CheckPassportResponse;
import com.mrt7l.ui.fragment.reservation.ReservationConfirmedResponse;
import com.mrt7l.ui.fragment.reservation.ReservationInterface;
import com.mrt7l.ui.fragment.reservation.ReservationPostModel;
import com.mrt7l.ui.fragment.reservation.ReservationResponse;
import com.mrt7l.ui.fragment.reservation.ReserveForMeViewModel;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPassengersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPassengersFragment extends Fragment implements ReservationInterface, HandlePassengersInterface {


    public AddPassengersFragment() {
        // Required empty public constructor
    }

    public static AddPassengersFragment newInstance() {
        AddPassengersFragment fragment = new AddPassengersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    String model,pageFrom,itemPosition;
    private String birthDay;
    private Calendar myCalendar;
    private Uri uri;
    private String token;
    private int userId;
    private int genderId = 0,countryId = 0;
    private LoginResponse loginResponse;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    ActivityResultLauncher<PickVisualMediaRequest> passportPickMedia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageFrom = getArguments().getString("page") ;
            model =  getArguments().getString("model");
            itemPosition = getArguments().getString("pos");
        }
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        this.uri = uri;
                        binding.image.setImageURI(uri);
                        binding.image.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        passportPickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        if (addPassportDialog.isShowing()) {
                        passportUri = uri;
                        Picasso.get().load(Uri.parse(passportUri.toString()))
                                .error(Objects.requireNonNull(ContextCompat.getDrawable(
                                        requireActivity(), R.drawable.cameras)))
                                .into(enterPassportDialogBinding.image);
                        enterPassportDialogBinding.image.setVisibility(View.VISIBLE);
                    }
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

    }



    ReservationPostModel reservationPostModel;
    ReservationPassengersBinding binding;
    PassengersResponse passengersResponse;
    ReserveForMeViewModel viewModel;
    Passengers_adapter passengersAdapter;
    boolean isUserAddedBefore = false;
    boolean isAddPassenger = false;
    ArrayList<DataBean> pastPassengersList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.reservation_passengers, container, false);
        reservationPostModel = ReservationPostModel.getInstance();

        passengersResponse = PassengersResponse.getInstance();
        token = new PreferenceHelper(requireActivity()).getTOKEN();
        userId = new PreferenceHelper(requireActivity()).getUSERID();
        viewModel = new ViewModelProvider(requireActivity()).get(ReserveForMeViewModel.class);
        viewModel.init(this);
        viewModel.setIsPassportSent(true);
        binding.cancel.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
        });
        binding.close.setOnClickListener(v -> {
            binding.addPassengerLayout.setVisibility(View.GONE);
            isAddPassenger = false;
        });
        Gson gson = new Gson();
        loginResponse = LoginResponse.getInstance();
        loginResponse = gson.fromJson(new PreferenceHelper(requireActivity()).getUSERNAME(),LoginResponse.class);
        setUpGenderSpinner(binding.genderSpinner);
        binding.me.setText( loginResponse.getMrt7al().getData().getUsername() + "  " +
                "(" + getString(R.string.main_passenger) +")");
        binding.pastPassenger.setEnabled(false);
        if (passengersResponse.getMrt7al() == null){
            viewModel.getPassengers(1,new PreferenceHelper(requireActivity()).getTOKEN());
        } else {
            binding.pastPassenger.setEnabled(true);
            if (pastPassengersList.size() ==0)
                for (int i=0;i<passengersResponse.getMrt7al().getData().size();i++){
                    DataBean dataBean = new DataBean();
                    dataBean.setNationality_id(passengersResponse.getMrt7al().getData().get(i).getNationality_id());
                    dataBean.setUser_id(passengersResponse.getMrt7al().getData().get(i).getUser_id());
                    dataBean.setId(passengersResponse.getMrt7al().getData().get(i).getId());
                    dataBean.setPassport_id(passengersResponse.getMrt7al().getData().get(i).getPassport_id());
                    dataBean.setFull_name(passengersResponse.getMrt7al().getData().get(i).getFull_name());
                    dataBean.setDate_of_birth(passengersResponse.getMrt7al().getData().get(i).getDate_of_birth());
                    dataBean.setPassport_file(passengersResponse.getMrt7al().getData().get(i).getPassport_file());
                    dataBean.setGender(passengersResponse.getMrt7al().getData().get(i).getGender());
                    dataBean.setMobile(passengersResponse.getMrt7al().getData().get(i).getMobile());
                    dataBean.setCreated(passengersResponse.getMrt7al().getData().get(i).getCreated());
                    dataBean.setPassangerType(passengersResponse.getMrt7al().getData().get(i).getPassangerType());
                    pastPassengersList.add(dataBean);
                }
            binding.passengerRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
            passengersAdapter = new Passengers_adapter(requireActivity(),pastPassengersList,this,true);
            binding.passengerRecycler.setAdapter(passengersAdapter);
            binding.passengerRecycler.setVisibility(View.VISIBLE);
        }
        binding.me.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (int i=0;i<SubPassengers.size();i++){
                if (SubPassengers.get(i).getId() ==
                        loginResponse.getMrt7al().getData().getId()){
                    SubPassengers.remove(i);
                    break;
                }
            }
            if (isChecked){
                reservationPostModel.setMainPassanger("ON");
                if (SubPassengers.size()>0)
                    for(int i=0;i<SubPassengers.size();i++){
                        if (loginResponse.getMrt7al().getData().getId() ==
                                SubPassengers.get(i).getId()){
                            isUserAddedBefore = true;
                        }
                    }
                if (!isUserAddedBefore) {
                    DataBean dataBean = new DataBean();
                    dataBean.setNationality_id(loginResponse.getMrt7al().getData().getNationality_id());
                    dataBean.setUser_id(loginResponse.getMrt7al().getData().getId());
                    dataBean.setId(loginResponse.getMrt7al().getData().getId());
                    dataBean.setPassport_id(loginResponse.getMrt7al().getData().getPassport_id());
                    dataBean.setFull_name(loginResponse.getMrt7al().getData().getUsername());
                    dataBean.setDate_of_birth(loginResponse.getMrt7al().getData().getDate_of_birth());
                    dataBean.setPassport_file(loginResponse.getMrt7al().getData().getPassport_file());
                    dataBean.setGender(loginResponse.getMrt7al().getData().getGender());
                    dataBean.setMobile(loginResponse.getMrt7al().getData().getMobile());
                    dataBean.setCreated(loginResponse.getMrt7al().getData().getCreated());
                    dataBean.setPassangerType(loginResponse.getMrt7al().getData().getPassangerType());
                    dataBean.setSelected(true);
                    SubPassengers.add(dataBean);
                    isUserAddedBefore = false ;
                }
            } else {
                reservationPostModel.setMainPassanger("OFF");
                for (int i=0;i<SubPassengers.size();i++) {
                    if (loginResponse.getMrt7al().getData().getId()
                            == SubPassengers.get(i).getId()) {
                        SubPassengers.remove(i);
                        break;
                    }
                }
            }
        });

        reservationPostModel.setBeforeConfirm("ON");
//        binding.pastPassenger.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked){
//                if (pastPassengersList.size() ==0) {
//                    for (int i = 0; i < passengersResponse.getMrt7al().getData().size(); i++) {
//                        DataBean dataBean = new DataBean();
//                        dataBean.setNationality_id(passengersResponse.getMrt7al().getData().get(i).getNationality_id());
//                        dataBean.setUser_id(passengersResponse.getMrt7al().getData().get(i).getUser_id());
//                        dataBean.setId(passengersResponse.getMrt7al().getData().get(i).getId());
//                        dataBean.setPassport_id(passengersResponse.getMrt7al().getData().get(i).getPassport_id());
//                        dataBean.setFull_name(passengersResponse.getMrt7al().getData().get(i).getFull_name());
//                        dataBean.setDate_of_birth(passengersResponse.getMrt7al().getData().get(i).getDate_of_birth());
//                        dataBean.setPassport_file(passengersResponse.getMrt7al().getData().get(i).getPassport_file());
//                        dataBean.setGender(passengersResponse.getMrt7al().getData().get(i).getGender());
//                        dataBean.setMobile(passengersResponse.getMrt7al().getData().get(i).getMobile());
//                        dataBean.setCreated(passengersResponse.getMrt7al().getData().get(i).getCreated());
//                        dataBean.setPassangerType(passengersResponse.getMrt7al().getData().get(i).getPassangerType());
//                        dataBean.setSelected(true);
//                        pastPassengersList.add(dataBean);
//                    }
//                } else {
//                    for (int i=0;i<pastPassengersList.size();i++){
//                        pastPassengersList.get(i).setSelected(true);
//                        passengersAdapter.notifyDataSetChanged();
//                    }
//                }
//                binding.passengerRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
//                passengersAdapter = new Passengers_adapter(requireActivity(),pastPassengersList,this,true);
//                binding.passengerRecycler.setAdapter(passengersAdapter);
//                binding.passengerRecycler.setVisibility(View.VISIBLE);
//            } else {
////                binding.passengerRecycler.setVisibility(View.GONE);
//                if (pastPassengersList.size() >0){
//                    for (int i=0;i<pastPassengersList.size();i++){
//                        pastPassengersList.get(i).setSelected(false);
//                        passengersAdapter.notifyDataSetChanged();
//                    }
//                    for (int i=0;i<pastPassengersList.size();i++) {
//                        for (int a = 0; a < SubPassengers.size(); a++) {
//                            if (pastPassengersList.get(i).getId() ==
//                                    SubPassengers.get(a).getId()){
//                                SubPassengers.remove(a);
//                            }
//                        }
//                    }
//                }
//            }
//        });
        binding.birthDay.setOnClickListener(view -> {
            myCalendar = Calendar.getInstance();
            new DatePickerDialog(requireActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.uploadImage.setOnClickListener(view ->  pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
        binding.nationality.setOnClickListener(view -> binding.nationalitySpinner.performClick());
        binding.genderType.setOnClickListener(view -> binding.genderSpinner.performClick());
        binding.fullName.requestFocus();
        setUpAgeSpinner(binding.ageSpinner);
        binding.addPassengerBtn.setOnClickListener(view -> {
            if (checkInputs()) {
                if (loginResponse.getMrt7al() != null) {
                    DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.adding_passenger));
                    String phone = binding.phoneNumber.getText().toString();
                    if (uri != null) {
                        viewModel.AddPassenger(token, createPartFromString(binding.fullName.getText().toString()),
                                createPartFromString(String.valueOf(loginResponse.getMrt7al().getData().getId())), createPartFromString(
                                        String.valueOf(countryId)), createPartFromString(String.valueOf(genderId)),
                                createPartFromString(birthDay), createPartFromString(binding.passportNumber.getText().toString()),
                                createPartFromString(phone)
                                , prepareFilePart(uri.toString()));
                    } else {
                        viewModel.AddPassenger(token, createPartFromString(binding.fullName.getText().toString()),
                                createPartFromString(String.valueOf(loginResponse.getMrt7al().getData().getId())), createPartFromString(
                                        String.valueOf(countryId)), createPartFromString(String.valueOf(genderId)),
                                createPartFromString(birthDay), createPartFromString(binding.passportNumber.getText().toString()),
                                createPartFromString(phone)
                        );

                    }
                }
            }
        });
        binding.newPassenger.setOnClickListener(v -> {;
            if (!isAddPassenger){
                binding.addPassengerLayout.setVisibility(View.VISIBLE);
                isAddPassenger = true;
            } else {
                binding.addPassengerLayout.setVisibility(View.GONE);
                isAddPassenger = false;
            }
        });

        binding.nextStep.setOnClickListener(v -> {
            if (binding.me.isChecked()) {
                binding.confirmProgress.setVisibility(View.VISIBLE);
                binding.nextStep.setVisibility(View.GONE);
                viewModel.checkPassport(token);
            } else {
                reservationPostModel.getSub_passanger_ids().addAll(pastSubPassengers);
                reservationPostModel.getSubPassengers().addAll(SubPassengers);
                if (binding.me.isChecked()) {
                    reservationPostModel.setMainPassanger("ON");
                } else {
                    reservationPostModel.setMainPassanger("OFF");
                }
                if (reservationPostModel.getSub_passanger_ids().size() == 0
                        && reservationPostModel.getMainPassanger().equals("OFF")) {
                    Toast.makeText(requireActivity(), getString(R.string.please_choose_passengers),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("model", model);
                bundle.putString("pos", itemPosition);
                bundle.putString("page", pageFrom);

                Navigation.findNavController(requireActivity(), R.id.main_fragment).
                        navigate(R.id.action_addPassengersFragment_to_reserveForMeFragment, bundle);
            }
        });

        return binding.getRoot();
    }
    private String imagePath;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireView().setFocusableInTouchMode(true);
        requireView().requestFocus();
        requireView().setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK )
            {
                if(isAddPassenger){
                    binding.addPassengerLayout.setVisibility(View.GONE);
                    isAddPassenger = false;

                    return true;
                } else {
//                    Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
                    return false;
                }

            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();

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

    }
    private RequestBody createPartFromString(String param) {
        return RequestBody.create(param,MediaType.parse("multipart/form-data")  );
    }
    private MultipartBody.Part prepareFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(requireActivity(), Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,MediaType.parse(Objects.requireNonNull(requireActivity().getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("passport_img", file.getName(), requestFile);
        } catch (Exception e) {
            //e.printStackTrace();
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

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthDay = sdf.format(myCalendar.getTime());
        binding.birthDay.setText(birthDay);
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
    }

    private ArrayList<String> HandleNationalityData(RegisterCollectionResponse response){
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0; i<response.getMrt7al().getData().getNationalities().size();i++){
            arrayList.add(response.getMrt7al().getData().getNationalities().get(i).getName());
        }
        return arrayList;
    }


    @Override
    public void onResponse(boolean isSuccess, ReservationResponse homeResponse) {

    }

    @Override
    public void onConfirmedResponse(boolean isSucces, ReservationConfirmedResponse reservationConfirmedResponse) {

    }

    @Override
    public void handleError(String t) {
        binding.confirmProgress.setVisibility(View.GONE);
        binding.nextStep.setVisibility(View.VISIBLE);
        if(addPassportDialog != null && addPassportDialog.isShowing()){
            enterPassportDialogBinding.progress.setVisibility(View.GONE);
            enterPassportDialogBinding.okButton.setVisibility(View.VISIBLE);
        }
        Toast.makeText(requireActivity(), t, Toast.LENGTH_SHORT).show();
        if (t.equals("غير مسموح بالدخول لهذه الصفحة")){
            Intent intent = new Intent(requireActivity(), SignInActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    @Override
    public void onVerifyPromo(ReservationResponse verifyPromoResponse) {

    }


    @Override
    public void onGetPassengers(PassengersResponse response) {
        DialogsHelper.removeProgressDialog();
        ArrayList<DataBean> pList = new ArrayList<> ();
        if (passengersResponse.getMrt7al().getPagination().getPage() ==1) {
            try {
                if (!requireActivity().isFinishing()) {
                    binding.passengerRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    for (int i = 0; i < response.getMrt7al().getData().size(); i++) {
                        DataBean dataBean = new DataBean();
                        dataBean.setNationality_id(response.getMrt7al().getData().get(i).getNationality_id());
                        dataBean.setUser_id(response.getMrt7al().getData().get(i).getUser_id());
                        dataBean.setId(response.getMrt7al().getData().get(i).getId());
                        dataBean.setPassport_id(response.getMrt7al().getData().get(i).getPassport_id());
                        dataBean.setFull_name(response.getMrt7al().getData().get(i).getFull_name());
                        dataBean.setDate_of_birth(response.getMrt7al().getData().get(i).getDate_of_birth());
                        dataBean.setPassport_file(response.getMrt7al().getData().get(i).getPassport_file());
                        dataBean.setGender(response.getMrt7al().getData().get(i).getGender());
                        dataBean.setMobile(response.getMrt7al().getData().get(i).getMobile());
                        dataBean.setCreated(response.getMrt7al().getData().get(i).getCreated());
                        dataBean.setPassangerType(response.getMrt7al().getData().get(i).getPassangerType());
                        pList.add(dataBean);

                    }
                    passengersAdapter = new Passengers_adapter(requireActivity(),
                            pList, this, true);
                    binding.passengerRecycler.setAdapter(passengersAdapter);
                }
            } catch (IllegalStateException e){
                //e.printStackTrace();
            }
//            binding.passengerRecycler.setVisibility(View.VISIBLE);
        } else {
            passengersAdapter.notifyDataSetChanged();
        }
        binding.pastPassenger.setEnabled(true);
    }

    @Override
    public void onAddPassenger(AddPassengerResponse addPassengerResponse) {
        DialogsHelper.removeProgressDialog();
        if (addPassengerResponse.getMrt7al().getSuccess()){
            try {
                imagePath= PathUtil.getPath(requireContext(),uri);
            } catch (URISyntaxException e) {
                //e.printStackTrace();
            }
            new PreferenceHelper(requireActivity()).setReloadProfile(true);
            pastSubPassengers.add(addPassengerResponse.getMrt7al().getData().getId());
            DataBean dataBean = new DataBean();
            dataBean.setNationality_id(addPassengerResponse.getMrt7al().getData().getNationality_id());
            dataBean.setUser_id(addPassengerResponse.getMrt7al().getData().getUser_id());
            dataBean.setId(addPassengerResponse.getMrt7al().getData().getId());
            dataBean.setPassport_id(addPassengerResponse.getMrt7al().getData().getPassport_id());
            dataBean.setFull_name(addPassengerResponse.getMrt7al().getData().getFull_name());
            dataBean.setDate_of_birth(addPassengerResponse.getMrt7al().getData().getDate_of_birth());
            dataBean.setPassport_file(addPassengerResponse.getMrt7al().getData().getPassport_file());
            dataBean.setGender(addPassengerResponse.getMrt7al().getData().getGender());
            dataBean.setMobile(addPassengerResponse.getMrt7al().getData().getMobile());
            dataBean.setCreated(addPassengerResponse.getMrt7al().getData().getCreated());
            dataBean.setPassangerType(addPassengerResponse.getMrt7al().getData().getPassangerType());
            dataBean.setSelected(true);
            PassengersResponse.Mrt7alBean.DataBean passenger = new PassengersResponse.Mrt7alBean.DataBean();
            passenger.setNationality_id(addPassengerResponse.getMrt7al().getData().getNationality_id());
            passenger.setUser_id(addPassengerResponse.getMrt7al().getData().getUser_id());
            passenger.setId(addPassengerResponse.getMrt7al().getData().getId());
            passenger.setPassport_id(addPassengerResponse.getMrt7al().getData().getPassport_id());
            passenger.setFull_name(addPassengerResponse.getMrt7al().getData().getFull_name());
            passenger.setDate_of_birth(addPassengerResponse.getMrt7al().getData().getDate_of_birth());
            passenger.setPassport_file(addPassengerResponse.getMrt7al().getData().getPassport_file());
            passenger.setGender(addPassengerResponse.getMrt7al().getData().getGender());
            passenger.setMobile(addPassengerResponse.getMrt7al().getData().getMobile());
            passenger.setCreated(addPassengerResponse.getMrt7al().getData().getCreated());
            passenger.setPassangerType(addPassengerResponse.getMrt7al().getData().getPassangerType());
            SubPassengers.add(dataBean);
            passengersResponse.getMrt7al().getData().add(passenger);
            pastPassengersList.add(dataBean);
            passengersAdapter.notifyDataSetChanged();
            Toast.makeText(requireActivity(), addPassengerResponse.getMrt7al().getMsg()
                    , Toast.LENGTH_SHORT).show();
            binding.fullName.setText("");
            binding.birthDay.setText("");
            binding.passportNumber.setText("");
            binding.phoneNumber.setText("");
            binding.genderSpinner.setSelection(0);
            binding.nationalitySpinner.setSelection(0);
            binding.ageSpinner.setSelection(0);
            binding.image.setImageDrawable(null);
            binding.image.setVisibility(View.GONE);
            isAddPassenger = false;
            binding.passengerRecycler.setVisibility(View.VISIBLE);
            binding.addPassengerLayout.setVisibility(View.GONE);
            isAddPassenger = false;
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
            DialogsHelper.showErrorDialog(addPassengerResponse.getMrt7al().getMsg(),requireActivity());
        }
    }

    @Override
    public void setCheckPassport(CheckPassportResponse checkPassportResponse) {
        binding.confirmProgress.setVisibility(View.GONE);
        binding.nextStep.setVisibility(View.VISIBLE);
        if(checkPassportResponse.getMrt7al().getData().getPassport_file() == null){
            showAddPassportDialog(requireActivity());
        } else {
            reservationPostModel.getSub_passanger_ids().addAll(pastSubPassengers);
            reservationPostModel.getSubPassengers().addAll(SubPassengers);
            if (binding.me.isChecked()) {
                reservationPostModel.setMainPassanger("ON");
            } else {
                reservationPostModel.setMainPassanger("OFF");
            }
            if (reservationPostModel.getSub_passanger_ids().size() == 0
                    && reservationPostModel.getMainPassanger().equals("OFF")) {
                Toast.makeText(requireActivity(), getString(R.string.please_choose_passengers),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putString("pos", itemPosition);
            bundle.putString("page", pageFrom);

            Navigation.findNavController(requireActivity(), R.id.main_fragment).
                    navigate(R.id.action_addPassengersFragment_to_reserveForMeFragment, bundle);
        }
    }

    @Override
    public void onEditProfile(EditProfileResponse editProfileResponse) {
        if(editProfileResponse.getMrt7al().getSuccess()){
            addPassportDialog.cancel();
        }
    }

    @Override
    public void onGetCollections(RegisterCollectionResponse registerCollectionResponse) {
        setUpNationalitySpinner(binding.nationalitySpinner,registerCollectionResponse);
    }

    Dialog addPassportDialog;
    Uri passportUri;
    EnterPassportDialogBinding enterPassportDialogBinding;
    public void showAddPassportDialog(Activity activity) {

        addPassportDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        addPassportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPassportDialog.setCancelable(false);
        enterPassportDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.enter_passport_dialog, null, false);


        enterPassportDialogBinding.uploadImage.setOnClickListener(v -> {
            passportPickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        enterPassportDialogBinding.okButton.setOnClickListener(v -> {
            if (enterPassportDialogBinding.passportNumber.getText().toString().isEmpty()) {
                Toast.makeText(activity, "من فضلك قم بإدخال رقم جواز السفر", Toast.LENGTH_SHORT).show();
            } else if(passportUri == null) {
                Toast.makeText(activity, "من فضلك قم باختيار صورة جواز السفر", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.setPassportUri(passportUri);
                viewModel.setPassportId(enterPassportDialogBinding.passportNumber.getText().toString());
                viewModel.setIsPassportSent(false);
                for(int i=0;i<SubPassengers.size();i++){
                    if(SubPassengers.get(i).getUser_id() == userId){
                        SubPassengers.get(i).setPassport_id(enterPassportDialogBinding.passportNumber.getText().toString());
                        break;
                    }
                }
                reservationPostModel.getSub_passanger_ids().addAll(pastSubPassengers);
                reservationPostModel.getSubPassengers().addAll(SubPassengers);
                if (binding.me.isChecked()) {
                    reservationPostModel.setMainPassanger("ON");
                } else {
                    reservationPostModel.setMainPassanger("OFF");
                }
                if (reservationPostModel.getSub_passanger_ids().size() == 0
                        && reservationPostModel.getMainPassanger().equals("OFF")) {
                    Toast.makeText(requireActivity(), getString(R.string.please_choose_passengers),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                addPassportDialog.cancel();
                Bundle bundle = new Bundle();
                bundle.putString("model", model);
                bundle.putString("pos", itemPosition);
                bundle.putString("page", pageFrom);

                Navigation.findNavController(requireActivity(), R.id.main_fragment).
                        navigate(R.id.action_addPassengersFragment_to_reserveForMeFragment, bundle);
//                enterPassportDialogBinding.progress.setVisibility(View.VISIBLE);
//                enterPassportDialogBinding.okButton.setVisibility(View.GONE);
//                     viewModel.editProfile("Bearer " + token,
//                            createPartFromString(enterPassportDialogBinding.passportNumber.
//                                    getText().toString()),String.valueOf(userId),
//                             prepareFilePart(passportUri.toString()));
            }
        });
        enterPassportDialogBinding.cancel.setOnClickListener(view ->{
            addPassportDialog.cancel();
//            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
        }) ;
        addPassportDialog.setContentView(enterPassportDialogBinding.getRoot());
        addPassportDialog.show();
    }

//    PermissionListener passportPermissionlistener = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() {
////            Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            pLauncher.launch(intent);
//        }
//
//        @Override
//        public void onPermissionDenied(List<String> deniedPermissions) {
////            Toast.makeText(requireActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//
//    };


    ArrayList<Integer> pastSubPassengers = new ArrayList<>();
    ArrayList<DataBean>  SubPassengers = new ArrayList<>();

    @Override
    public void OnEdit(DataBean registerResponse) {

    }

    @Override
    public void OnDelete(DataBean registerResponse, int pos) {

    }

    @Override
    public void onPassengerChecked(DataBean  Bean) {
        if (Bean.isSelected()) {
            pastSubPassengers.add(Bean.getId());
            DataBean dataBean = new DataBean();
            dataBean.setNationality_id(Bean.getNationality_id());
            dataBean.setUser_id(Bean.getUser_id());
            dataBean.setId(Bean.getId());
            dataBean.setPassport_id(Bean.getPassport_id());
            dataBean.setFull_name(Bean.getFull_name());
            dataBean.setDate_of_birth(Bean.getDate_of_birth());
            dataBean.setPassport_file(Bean.getPassport_file());
            dataBean.setGender(Bean.getGender());
            dataBean.setMobile(Bean.getMobile());
            dataBean.setCreated(Bean.getCreated());
            dataBean.setPassangerType(Bean.getPassangerType());
            SubPassengers.add(dataBean);
        } else {
            for (int i=0;i<pastSubPassengers.size();i++){
                if (Bean.getId() == pastSubPassengers.get(i)){
                    pastSubPassengers.remove(i);
                    break;
                }
            }
            for (int i=0;i<SubPassengers.size();i++){
                if (Bean.getId() == SubPassengers.get(i).getId()){
                    SubPassengers.remove(i);
                    break;
                }
            }
        }
    }

    public static class DataBean  {
        private boolean isSelected;
        private int id;
        private int user_id;
        private String full_name;
        private String date_of_birth;
        private int nationality_id;
        private String gender;
        private String passport_id;
        private String passport_file;
        private String mobile;
        private String created;
        private String modifed;
        private String passangerType;
        private int city_id;
        private int is_active;
        private String isMain;
        private String city;
        private PassengersResponse.Mrt7alBean.DataBean.NationalityBean nationality;


        public DataBean(){};
        public DataBean(Integer id, Integer id1, String username, String date_of_birth, String passport_id, String mobile, String gender) {
            this.id = id;
            this.full_name = full_name;
            this.date_of_birth = date_of_birth;
            this.gender = gender;
            this.passport_id = passport_id;
            this.passport_file = passport_file;
            this.mobile = mobile;
            this.created = created;
            this.modifed = modifed;
            this.passangerType = passangerType;
            this.isMain = isMain;
            this.city = city;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;

        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;

        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;

        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;

        }

        public int getNationality_id() {
            return nationality_id;
        }

        public void setNationality_id(int nationality_id) {
            this.nationality_id = nationality_id;

        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;

        }

        public String getPassport_id() {
            return passport_id;
        }

        public void setPassport_id(String passport_id) {
            this.passport_id = passport_id;

        }

        public String getPassport_file() {
            return passport_file;
        }

        public void setPassport_file(String passport_file) {
            this.passport_file = passport_file;

        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;

        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;

        }

        public String getModifed() {
            return modifed;
        }

        public void setModifed(String modifed) {
            this.modifed = modifed;

        }

        public String getPassangerType() {
            return passangerType;
        }

        public void setPassangerType(String passangerType) {
            this.passangerType = passangerType;

        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;

        }

        public int getIs_active() {
            return is_active;
        }

        public void setIs_active(int is_active) {
            this.is_active = is_active;

        }

        public String getIsMain() {
            return isMain;
        }

        public void setIsMain(String isMain) {
            this.isMain = isMain;

        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;

        }

        public PassengersResponse.Mrt7alBean.DataBean.NationalityBean getNationality() {
            return nationality;
        }

        public void setNationality(PassengersResponse.Mrt7alBean.DataBean.NationalityBean nationality) {
            this.nationality = nationality;

        }

        public class NationalityBean extends Observable {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;

            }
        }
    }

}