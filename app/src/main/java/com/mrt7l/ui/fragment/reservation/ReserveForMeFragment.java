package com.mrt7l.ui.fragment.reservation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.mrt7l.R;
import com.mrt7l.databinding.PolicyDialogBinding;
import com.mrt7l.databinding.RegisterErrorDialogBinding;
import com.mrt7l.databinding.ReservationAcceptedBinding;
import com.mrt7l.databinding.ReserveForMeFragmentBinding;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.FilePath;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.activity.PayActivity;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.home.HomeResponse;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.reservation.addpassengers.AddPassengersFragment;
import com.mrt7l.ui.fragment.reservation.addpassengers.PassengerPriceModel;
import com.mrt7l.ui.fragment.reservation.addpassengers.PassengersAdapter;
import com.mrt7l.ui.fragment.reservation.addpassengers.PassengersPriceAdapter;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReserveForMeFragment extends Fragment implements ReservationInterface, PassengersAdapter.ClickListener {

    private ReserveForMeViewModel mViewModel;
    private ArrayList<PassengerModel> passengers = new ArrayList<>();

    public static ReserveForMeFragment newInstance() {
        return new ReserveForMeFragment();
    }

    ReserveForMeFragmentBinding fragmentBinding;
    private ReservationPost postModel;
    CompanyDetailsResponse.Mrt7alBean.DataBean datesBean;
    SearchTripsResponse.Mrt7alBean.DataBean dataBean;

    private ReservationPostModel reservationPostModel;
    private boolean isTripForMeOnly = false;
    private int companyID = 0;
    private int pos;
    private String page;
    private HomeResponse.Mrt7alBean.DataBean homeBean;
    private SearchTripsResponse.Mrt7alBean.DataBean searchBean;
    private CompanyDetailsResponse.Mrt7alBean.DataBean detailsModel;
    private String token;
    private static final int WEBVIEW_REQUEST_CODE = 100;
    private ActivityResultLauncher<Intent> webViewLauncher;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        this.uri = uri;
                        Picasso.get().load(Uri.parse(uri.toString()))
                                .error(Objects.requireNonNull(ContextCompat.getDrawable(
                                        requireActivity(), R.drawable.cameras)))
                                .into(fragmentBinding.resetImage);
                        fragmentBinding.resetImage.setVisibility(View.VISIBLE);

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

// Register Activity result launcher
        webViewLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Log.v("result",result.getData().toString());
                        mViewModel.checkPayStatus(token,mViewModel.requestPayModel.getMrt7al().getData().getId());
                    } else {
                        showPaymentFailedDialog("تم الغاء عملية الدفع يمكنك استكمال عملية الدفع من صفحة حجوزاتي");
                    }
                });
    }
    boolean isDetailsOpened = false;
    boolean isPricesOpened = false;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.reserve_for_me_fragment,
                container, false);
        reservationPostModel = ReservationPostModel.getInstance();
        postModel = new ReservationPost();
        postModel.setBeforeConfirm("ON");
        postModel.setMainPassanger(reservationPostModel.getMainPassanger());
        postModel.setSub_passanger_ids(reservationPostModel.getSub_passanger_ids());
        postModel.setTrip_date_id(reservationPostModel.getTrip_date_id());
        postModel.setPayMethod("cash");
        mViewModel =   new ViewModelProvider(requireActivity()).get(ReserveForMeViewModel.class);
        mViewModel.init(this);
        fragmentBinding.passengerProgress.setVisibility(View.VISIBLE);
        token = new PreferenceHelper(requireActivity()).getTOKEN();
        fragmentBinding.moreDetails.setOnClickListener((view)->{
            if (!isDetailsOpened){
                fragmentBinding.dataLayout.setVisibility(View.VISIBLE);
                isDetailsOpened = true;
            } else {
                fragmentBinding.dataLayout.setVisibility(View.GONE);
                isDetailsOpened = false;
            }
        });
        fragmentBinding.morePrices.setOnClickListener((view)->{
            if (!isPricesOpened){
                fragmentBinding.priceRecyclerView.setVisibility(View.VISIBLE);
                fragmentBinding.priceLine.setVisibility(View.VISIBLE);
                isPricesOpened = true;
            } else {
                fragmentBinding.priceRecyclerView.setVisibility(View.GONE);
                isPricesOpened = false;
                fragmentBinding.priceLine.setVisibility(View.GONE);
            }
        });
        assert getArguments() != null;
        page = getArguments().getString("page");
        String model = getArguments().getString("model");
        assert page != null;
        if (page.equals("home")) {
            homeBean = new Gson().fromJson(model, HomeResponse.Mrt7alBean.DataBean.class);
            if (homeBean.getCompany() != null) {
                fragmentBinding.companyName.setText(homeBean.getCompany().getName());
            }
            try {
                postModel.setTrip_date_id(homeBean.getId());
                fragmentBinding.tripDate.setText(formatDate(homeBean.getDatetime_from()));
                fragmentBinding.fromCity.setText(homeBean.getFrom_city().getName());
                fragmentBinding.toCity.setText(homeBean.getTo_city().getName());
                fragmentBinding.attendTime.setText(formatTime(homeBean.getDatetime_to()));
                fragmentBinding.startTime.setText(formatTime(homeBean.getDatetime_from()));
//                fragmentBinding.startStation.setText(homeBean.getFrom_city().getName());
                fragmentBinding.companyPlace.setText(homeBean.getCompany().getAddress());
                fragmentBinding.account.setText(homeBean.getCompany().getBank_account());
                fragmentBinding.waitingTime.setText(String.valueOf(homeBean.getWaitingTime()));
                fragmentBinding.tripNumber.setText(homeBean.getTrip_number());
            } catch (NullPointerException | Resources.NotFoundException e) {
                //e.printStackTrace();
            }


        } else if (page.equals("search")) {
            searchBean = new Gson().fromJson(model, SearchTripsResponse.Mrt7alBean.DataBean.class);
            if (searchBean.getCompany() != null) {
                fragmentBinding.companyName.setText(searchBean.getCompany().getName());
            }
            try {
                postModel.setTrip_date_id(searchBean.getId());
                fragmentBinding.tripDate.setText(formatDate(searchBean.getDatetime_from()));
                fragmentBinding.fromCity.setText(searchBean.getFrom_city().getName());
                fragmentBinding.toCity.setText(searchBean.getTo_city().getName());
                fragmentBinding.attendTime.setText(formatTime(searchBean.getDatetime_to()));
                fragmentBinding.startTime.setText(formatTime(searchBean.getDatetime_from()));
//                fragmentBinding.startStation.setText(searchBean.getFrom_city().getName());
                fragmentBinding.companyPlace.setText(searchBean.getCompany().getAddress());
                fragmentBinding.account.setText(searchBean.getCompany().getBank_account());
                fragmentBinding.waitingTime.setText(String.valueOf(searchBean.getWaitingTime()));
                fragmentBinding.tripNumber.setText(searchBean.getTrip_number());
            } catch (NullPointerException | Resources.NotFoundException e) {
                //e.printStackTrace();
            }
        } else {
            int position = Integer.parseInt(getArguments().getString("pos"));
            detailsModel = new Gson().fromJson(model, CompanyDetailsResponse.Mrt7alBean.DataBean.class);
            if (detailsModel.getName() != null) {
                fragmentBinding.companyName.setText(detailsModel.getName());
            }
            try {
                postModel.setTrip_date_id(detailsModel.getTrip_dates().get(position).getId());
                fragmentBinding.tripDate.setText(formatDate(detailsModel.getTrip_dates().get(position).getDatetime_from()));
                fragmentBinding.fromCity.setText(detailsModel.getTrip_dates().get(position).getFrom_city().getName());
                fragmentBinding.toCity.setText(detailsModel.getTrip_dates().get(position).getTo_city().getName());
                fragmentBinding.attendTime.setText(formatTime(detailsModel.getTrip_dates().get(position).getDatetime_to()));
                fragmentBinding.startTime.setText(formatTime(detailsModel.getTrip_dates().get(position).getDatetime_from()));
//                fragmentBinding.startStation.setText(detailsModel.getTrip_dates().get(position).getStation().getName());
                fragmentBinding.companyPlace.setText(detailsModel.getAddress());
                fragmentBinding.account.setText(detailsModel.getBank_account());
                fragmentBinding.waitingTime.setText(String.valueOf(detailsModel.
                        getTrip_dates().get(position).getWaitingTime()));
                fragmentBinding.tripNumber.setText(detailsModel.getTrip_dates().get(position).getTrip_number());
            } catch (NullPointerException | Resources.NotFoundException e) {
                //e.printStackTrace();
            }
        }
        if(mViewModel.getPassportImage().getValue() != null &&
                mViewModel.getPassportId().getValue() != null) {
            try {
                if (!mViewModel.isPassportSentToServer().getValue()) {
                    mViewModel.addTripWithPassport("Bearer " + token,
                            getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                            createPartFromString(""), createPartFromString(postModel.getBeforeConfirm()),
                            createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                    postModel.getTrip_date_id())),
                            createPartFromString(mViewModel.getPassportId().getValue()), preparePassportFilePart(
                                    mViewModel.getPassportImage().getValue().toString()));
                } else {
                    mViewModel.addTrip("Bearer " + token,
                            getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                            createPartFromString(""), createPartFromString(postModel.getBeforeConfirm()),
                            createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                    postModel.getTrip_date_id())));
                }
            } catch (NullPointerException e){
                mViewModel.addTrip("Bearer " + token,
                        getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                        createPartFromString(""), createPartFromString(postModel.getBeforeConfirm()),
                        createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                postModel.getTrip_date_id())));
            }
        } else {
            mViewModel.addTrip("Bearer " + token,
                    getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                    createPartFromString(""), createPartFromString(postModel.getBeforeConfirm()),
                    createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                            postModel.getTrip_date_id())));
        }

        fragmentBinding.policyText.setOnClickListener(v -> {
            if (homeBean != null) {
                showPolicyDialog(homeBean.getCompany().getReservation_policy(), requireActivity());
            } else
            if (searchBean != null) {
                showPolicyDialog(searchBean.getCompany().getReservation_policy(), requireActivity());
            } else
            if (detailsModel != null) {
                showPolicyDialog(detailsModel.getReservation_policy(), requireActivity());
            }
        });
        fragmentBinding.uploadBillLayout.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        fragmentBinding.verifyPromo.setOnClickListener(v -> {
            if (!fragmentBinding.voucherEt.getText().toString().isEmpty()) {
                fragmentBinding.promoProgress.setVisibility(View.VISIBLE);
                postModel.setBeforeConfirm("ON");
                postModel.setPromoCode(fragmentBinding.voucherEt.getText().toString());
                isFirstOpen = true;
                 mViewModel.verifyPromo("Bearer " + token,
                        getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                        createPartFromString(fragmentBinding.voucherEt.getText().toString())
                        , createPartFromString(postModel.getBeforeConfirm()),
                        createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                postModel.getTrip_date_id())));
            }
        });
        fragmentBinding.whatsappLogo.setOnClickListener(v -> {
            PackageManager packageManager = requireActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            try {
                String url = "https://api.whatsapp.com/send?phone=" + AboutAppResponse.getInstance().
                        getMrt7al().getData().getMobile_whats() + "&text="
                        + URLEncoder.encode("", "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    requireActivity().startActivity(i);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        });
        fragmentBinding.payCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                fragmentBinding.uploadBillLayout.setVisibility(View.GONE);
            }
        });
        fragmentBinding.payVisa.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                fragmentBinding.uploadBillLayout.setVisibility(View.GONE);
            }
        });
        fragmentBinding.payBank.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fragmentBinding.uploadBillLayout.setVisibility(View.VISIBLE);
            } else {
                fragmentBinding.uploadBillLayout.setVisibility(View.GONE);
            }
        });
        fragmentBinding.confirmReservation.setOnClickListener(v -> {
            if (fragmentBinding.payCash.isChecked()) {
                postModel.setPayMethod("cash");
            } else if (fragmentBinding.payBank.isChecked()) {
                postModel.setPayMethod("transfer");
            } else if (fragmentBinding.payVisa.isChecked()) {
                postModel.setPayMethod("tap");
            } else {
                DialogsHelper.showErrorDialog(getString(R.string.choose_pay_type), requireActivity());
                return;
            }

            if (checkData()) {
                fragmentBinding.confirmProgress.setVisibility(View.VISIBLE);
                fragmentBinding.confirmReservation.setVisibility(View.GONE);
                fragmentBinding.scrollView.post(() ->
                        fragmentBinding.scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                postModel.setBeforeConfirm("OFF");
                if (uri != null) {
                    mViewModel.addTripWithResetConfirmed("Bearer " + token,
                            getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                            createPartFromString(fragmentBinding.voucherEt.getText().toString())
                            , createPartFromString(postModel.getBeforeConfirm()),
                            createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                    postModel.getTrip_date_id())), prepareFilePart(uri.toString()));
                } else {
                    mViewModel.addTripConfirmed("Bearer " + token,
                            getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                            createPartFromString(fragmentBinding.voucherEt.getText().toString())
                            , createPartFromString(postModel.getBeforeConfirm()),
                            createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                    postModel.getTrip_date_id())));
                }
            }
        });


        return fragmentBinding.getRoot();
    }
    private void openPayActivity(String url) {
        Intent intent = new Intent(getActivity(), PayActivity.class);
        intent.putExtra("url", url);
        webViewLauncher.launch(intent);
    }
    private boolean checkData() {
        if (fragmentBinding.payBank.isChecked() && uri == null) {
            DialogsHelper.showErrorDialog(getString(R.string.choose_reset), requireActivity());
            return false;
        } else if (!fragmentBinding.policyCheckBox.isChecked()){
            if (homeBean != null) {
                showPolicyDialog(homeBean.getCompany().getReservation_policy(), requireActivity());
            } else
            if (searchBean != null) {
                showPolicyDialog(searchBean.getCompany().getReservation_policy(), requireActivity());
            } else
            if (detailsModel != null) {
                showPolicyDialog(detailsModel.getReservation_policy(), requireActivity());
            }
            return false;
        }
        return true;
    }

    private List<RequestBody> getSubIdsList(ReservationPost reservationPost) {
        ArrayList<RequestBody> subIdsList = new ArrayList<>();
        for (int i = 0; i < reservationPost.getSub_passanger_ids().size(); i++) {
            subIdsList.add(createPartFromString(String.valueOf(
                    reservationPost.getSub_passanger_ids().get(i))));
        }
        return subIdsList;
    }

    private RequestBody createPartFromString(String param) {
        return RequestBody.create(param, MediaType.parse("multipart/form-data"));
    }
    private MultipartBody.Part preparePassportFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(requireActivity(), Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,
                    MediaType.parse(Objects.requireNonNull(requireActivity().getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("passport_file", file.getName(), requestFile);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    private MultipartBody.Part prepareFilePart(String fileUri) {
        File file;
        try {
            String selectedFilePath = FilePath.getPath(requireActivity(), Uri.parse(fileUri));
            assert selectedFilePath != null;
            file = new File(selectedFilePath);
            RequestBody requestFile = RequestBody.create(file,
                    MediaType.parse(Objects.requireNonNull(requireActivity().getContentResolver().getType(Uri.parse(fileUri)))));
            return MultipartBody.Part.createFormData("receipt_file", file.getName(), requestFile);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }



//    public void loadResetImage() {
//        Intent intent = createIntent(requireContext());
//        launchSomeActivity.launch(intent);
//    }

    private ArrayList<String> mOriginData;

    Uri uri,passportUri;

    private Bitmap getBitmap(String filePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
//        bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
        return bitmap;
    }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String formatDate(String date) {
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH);
        try {
            Date datse = format.parse(date.replace("T", " ").replace("+03:00", ""));
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return theDate;
    }

    public String formatTime(String date) {
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH);

        try {
            Date datse = format.parse(date.replace("T", " ").replace("+03:00", ""));
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return theDate;
    }

    public void showCompanyErrorDialog(String message, Activity activity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        RegisterErrorDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.register_error_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(view -> {
            dialog.cancel();
            Navigation.findNavController(requireActivity(),
                    R.id.main_fragment).navigate(R.id.backToHome);
        });
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }


    @Override
    public void onResponse(boolean isSuccess, ReservationResponse homeResponse) {
        fragmentBinding.passengerProgress.setVisibility(View.GONE);
        fragmentBinding.confirmProgress.setVisibility(View.GONE);
        fragmentBinding.confirmReservation.setVisibility(View.VISIBLE);
        fragmentBinding.promoProgress.setVisibility(View.GONE);
        if (!isSuccess) {
            DialogsHelper.showErrorDialog(homeResponse.getMrt7al().getMsg(), requireActivity());
        } else {
            if (isFirstOpen) {
                if (reservationPostModel.getSubPassengers().size() > 0) {
                    fragmentBinding.passengersRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    if (reservationPostModel.getSubPassengers().get(0).getUser_id() != 0)
                        reservationPostModel.getSubPassengers().add(0, new AddPassengersFragment.DataBean());
                    PassengersAdapter adapter = new PassengersAdapter(reservationPostModel.
                            getSubPassengers(),
                            requireContext());
                    fragmentBinding.passengersRecyclerView.setAdapter(adapter);
                } else {
                    fragmentBinding.passengersRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
//                    reservationPostModel.getSubPassengers().add(new PassengersResponse.Mrt7alBean.DataBean(loginResponse.getMrt7al().getData().getId(),
//                            loginResponse.getMrt7al().getData().getId(),loginResponse.getMrt7al()
//                            .getData().getUsername(),loginResponse.getMrt7al().getData().
//                            getDate_of_birth(),loginResponse.getMrt7al().getData().getPassport_id(),
//                            loginResponse.getMrt7al().getData().getMobile(),loginResponse.getMrt7al().getData().getGender())) ;
                    reservationPostModel.getSubPassengers().add(0, new AddPassengersFragment.DataBean());
                    PassengersAdapter adapter = new PassengersAdapter(reservationPostModel.getSubPassengers()
                            ,requireContext());
                    fragmentBinding.passengersRecyclerView.setAdapter(adapter);
                }
                ArrayList<PassengerPriceModel> priceModels = new ArrayList<>();
                priceModels.add(new PassengerPriceModel(getString(R.string.passenger_type),
                        getString(R.string.count), getString(R.string.text_ticket_price)));
                priceModels.add(new PassengerPriceModel(getString(R.string.adult),
                        String.valueOf(homeResponse.getMrt7al().getData().get(0).getStatistics()
                                .getAdults_count()), String.valueOf(homeResponse.getMrt7al()
                        .getData().get(0).getStatistics()
                        .getAdultPrice())));
                priceModels.add(new PassengerPriceModel(getString(R.string.child),
                        String.valueOf(homeResponse.getMrt7al().getData().get(0).getStatistics()
                                .getChildren_count()), String.valueOf(homeResponse.getMrt7al()
                        .getData().get(0).getStatistics()
                        .getChildPrice())));
                priceModels.add(new PassengerPriceModel(getString(R.string.baby),
                        String.valueOf(homeResponse.getMrt7al().getData().get(0).getStatistics()
                                .getBabies_count()), String.valueOf(homeResponse.getMrt7al()
                        .getData().get(0).getStatistics()
                        .getBabyPrice())));
                PassengersPriceAdapter passengersPriceAdapter = new PassengersPriceAdapter(priceModels, requireContext());
                fragmentBinding.priceRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                fragmentBinding.priceRecyclerView.setAdapter(passengersPriceAdapter);
                fragmentBinding.ticketsPrice.setText(String.valueOf(homeResponse.getMrt7al().getData()
                        .get(0).getStatistics().getTotal_ticket_price()));
                fragmentBinding.voucherDiscount.setText(String.valueOf(homeResponse.getMrt7al().getData()
                        .get(0).getStatistics().getDiscountPromo()));
                fragmentBinding.finalTotal.setText(String.valueOf(homeResponse.getMrt7al().getData()
                        .get(0).getStatistics().getTotal_price()));
                fragmentBinding.startStation.setText(homeResponse.getMrt7al().getData().get(0).getStatistics().getStation());
                if (homeResponse.getMrt7al().getData().get(0).getStatistics().getDiscountPromo() > 0) {
                    fragmentBinding.verified.setVisibility(View.VISIBLE);
                    fragmentBinding.voucherEt.setEnabled(false);
//                    fragmentBinding.verifyPromo.setVisibility(View.GONE);
                    fragmentBinding.finalTotal.setText(String.valueOf(homeResponse.getMrt7al().getData()
                            .get(0).getStatistics().getTotal_price()));
                    fragmentBinding.voucherDiscount.setText(String.valueOf(homeResponse.getMrt7al().getData()
                            .get(0).getStatistics().getDiscountPromo()));
                } else {
                    fragmentBinding.verified.setVisibility(View.GONE);
                    fragmentBinding.voucherEt.setEnabled(true);
//                    fragmentBinding.verifyPromo.setVisibility(View.VISIBLE);
                }
                isFirstOpen = false;
            } else {
                new PreferenceHelper(requireActivity()).setReloadMyTrips(true);
                showAcceptedDialog();
            }
        }
    }

    @Override
    public void onConfirmedResponse(boolean isSuccess, ReservationConfirmedResponse reservationConfirmedResponse) {

        if (!isSuccess) {
            DialogsHelper.removeProgressDialog();
            DialogsHelper.showErrorDialog(reservationConfirmedResponse.getMrt7al().getMsg(),
                    requireActivity());
        } else {
             if (fragmentBinding.payVisa.isChecked()) {
                 mViewModel.requestPay(token,reservationConfirmedResponse.getMrt7al().getData().getId().toString());
            } else {
                 DialogsHelper.removeProgressDialog();
                 showAcceptedDialog();
            }

        }
    }


    public void showAcceptedDialog() {
        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        ReservationAcceptedBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.reservation_accepted, null, false);
        registerErrorDialogBinding.okButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(R.id.backToHome);

            dialog.cancel();
        });
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }
    public void showPaymentFailedDialog(String message) {
            final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            RegisterErrorDialogBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.register_error_dialog, null, false);
            registerErrorDialogBinding.content.setText(message);
            registerErrorDialogBinding.okButton.setOnClickListener(view -> {
                        Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(R.id.backToHome);
                        dialog.cancel();
                    });
            dialog.setContentView(registerErrorDialogBinding.getRoot());
            dialog.show();
        }
    @Override
    public void handleError(String t) {
        try {
            DialogsHelper.removeProgressDialog();
            fragmentBinding.passengerProgress.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), t, Toast.LENGTH_SHORT).show();

            if (!t.equals("الرمز الترويجى غير صحيح")) {
                reservationPostModel.getSub_passanger_ids().clear();
                reservationPostModel.getSubPassengers().clear();
                reservationPostModel.setMainPassanger("OFF");

                Navigation.findNavController(requireActivity(), R.id.main_fragment).navigateUp();
            }
            fragmentBinding.promoProgress.setVisibility(View.GONE);
        } catch (NullPointerException | IllegalStateException e){
            //e.printStackTrace();
        }
    }

    @Override
    public void handlePayError(String message) {
        showPaymentFailedDialog(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reservationPostModel.getSub_passanger_ids().clear();
        reservationPostModel.getSubPassengers().clear();
        reservationPostModel.setMainPassanger("OFF");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reservationPostModel.getSub_passanger_ids().clear();
        reservationPostModel.getSubPassengers().clear();
        reservationPostModel.setMainPassanger("OFF");
    }

    private int discount = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public void onVerifyPromo(ReservationResponse verifyPromoResponse) {
        DialogsHelper.removeProgressDialog();
        fragmentBinding.promoProgress.setVisibility(View.GONE);
        if (verifyPromoResponse.getMrt7al().getSuccess()) {
            discount =   verifyPromoResponse.getMrt7al().getData()
                    .get(0).getStatistics().getDiscountPromo();
            if (datesBean != null) {
                datesBean.getTrip_dates().get(pos).setPrice(datesBean.getTrip_dates().get(pos).getPrice() - discount);
                fragmentBinding.finalTotal.setText(String.valueOf(
                        verifyPromoResponse.getMrt7al().getData()
                                .get(0).getStatistics().getTotal_price()));
                fragmentBinding.voucherDiscount.setText(String.valueOf(
                        verifyPromoResponse.getMrt7al().getData()
                                .get(0).getStatistics().getDiscountPromo()));
            } else if (dataBean != null) {
                dataBean.setPrice(dataBean.getPrice() - discount);

            }
            fragmentBinding.finalTotal.setText(String.valueOf(
                    verifyPromoResponse.getMrt7al().getData()
                            .get(0).getStatistics().getTotal_price()));
            fragmentBinding.voucherDiscount.setText(String.valueOf(
                    verifyPromoResponse.getMrt7al().getData()
                            .get(0).getStatistics().getDiscountPromo()));
            fragmentBinding.verifyPromo.setVisibility(View.GONE);
            fragmentBinding.verified.setVisibility(View.VISIBLE);
            fragmentBinding.voucherEt.setEnabled(false);
        } else {
            fragmentBinding.promoProgress.setVisibility(View.GONE);
            DialogsHelper.showErrorDialog("من فضلك تأكد من ادخال رمز ترويجي صالح", requireActivity());
        }
    }

    @Override
    public void onGetPassengers(PassengersResponse passengersResponse) {

    }

    @Override
    public void onAddPassenger(AddPassengerResponse addPassengerResponse) {

    }

    @Override
    public void setCheckPassport(CheckPassportResponse checkPassportResponse) {
        if(checkPassportResponse.getMrt7al().getSuccess()){
            if (uri != null) {
                mViewModel.addTripWithResetConfirmed("Bearer " + token,
                        getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                        createPartFromString(fragmentBinding.voucherEt.getText().toString())
                        , createPartFromString(postModel.getBeforeConfirm()),
                        createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                postModel.getTrip_date_id())), prepareFilePart(uri.toString()));
            } else {
                mViewModel.addTripConfirmed("Bearer " + token,
                        getSubIdsList(postModel), createPartFromString(postModel.getMainPassanger()),
                        createPartFromString(fragmentBinding.voucherEt.getText().toString())
                        , createPartFromString(postModel.getBeforeConfirm()),
                        createPartFromString(postModel.getPayMethod()), createPartFromString(String.valueOf(
                                postModel.getTrip_date_id())));
            }
        } else {

        }
    }

    @Override
    public void onEditProfile(EditProfileResponse editProfileResponse) {

    }

    @Override
    public void onGetCollections(RegisterCollectionResponse registerCollectionResponse) {

    }

    @Override
    public void onCheckingPayStatus(boolean success, String message) {
        if(success){
            showAcceptedDialog();
        } else {
            showPaymentFailedDialog(message);
        }
    }

    @Override
    public void onPayRequested(boolean success, RequestPayModel requestPayModel) {
        if(requestPayModel.getMrt7al().getSuccess()){
            openPayActivity(requestPayModel.getMrt7al().getData().getTransaction().getUrl());
        }
    }


    private boolean isFirstOpen = true;

//    public void showConfirmDialog(String total) {
//        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        ConfirmReservationDialogBinding registerErrorDialogBinding =
//                DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.confirm_reservation_dialog,
//                        null, false);
//
//        ArrayList<Integer> sudIds = new ArrayList<>();
////        postModel.setAdults_count(0);
////        postModel.setChildren_count(0);
////        postModel.setBabies_count(0);
//
//        for (int i = 0; i < passengers.size(); i++) {
////            if (passengers.get(i).getPassengerType().equals("adult")) {
////                postModel.setAdults_count(postModel.getAdults_count() + 1);
////            } else if (passengers.get(i).getPassengerType().equals("child")) {
////                postModel.setChildren_count(postModel.getChildren_count() + 1);
////            } else if (passengers.get(i).getPassengerType().equals("baby")) {
////                postModel.setBabies_count(postModel.getBabies_count() + 1);
////            }
//            sudIds.add(passengers.get(i).getId());
//        }if (isTripForMeOnly){
//            postModel.setMainPassanger("ON");
//        } else {
//
////        if (fragmentBinding.alsoMe.isChecked()){
////            postModel.setMainPassanger("ON");
////        } else {
////            postModel.setMainPassanger("OFF");
////        }
//        }
//        if (datesBean != null) {
////            postModel.setAvailable_count(datesBean.getTrip_dates().get(pos).getAvailable_count());
//            postModel.setTrip_date_id(datesBean.getTrip_dates().get(pos).getId());
//
//        } else if (reservationPreviewModel != null) {
////            postModel.setAvailable_count(reservationPreviewModel.getAvailableCount());
//            postModel.setTrip_date_id(reservationPreviewModel.getTripID());
//
//        } else if (dataBean != null){
////            postModel.setAvailable_count(dataBean.getAvailableCount());
//            postModel.setTrip_date_id(dataBean.getId());
//
//        }
////        postModel.setTotal_price(Integer.parseInt(total.replace(" ريال","")));
//
//        if (!isTripForMeOnly){
//            postModel.setSub_passanger_ids(sudIds);
//        }
//
////        postModel.setUser_id(loginResponse.getMrt7al().getData().getId());
////        postModel.setPassenger_count(passengers.size());
//
////        if (fragmentBinding.alsoMe.isChecked()){
////            registerErrorDialogBinding.totalPassengers.setText(String.valueOf(passengers.size() + 1));
////        } else {
////            registerErrorDialogBinding.totalPassengers.setText(String.valueOf(passengers.size()));
////        }
//        registerErrorDialogBinding.totalPrice.setText(total);
//
//        registerErrorDialogBinding.confirmReservation.setOnClickListener(view -> {
//            DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading));
//            postModel.setBeforeConfirm("OFF");
//            mViewModel.addTrip("Bearer " +loginResponse.getMrt7al().getData().getToken()
//                    ,postModel);
//            dialog.cancel();
//        });
//         registerErrorDialogBinding.cancel.setOnClickListener(view -> dialog.cancel());
//        dialog.setContentView(registerErrorDialogBinding.getRoot());
//        dialog.show();
//    }

    @Override
    public void onReservation(CompanyDetailsResponse.Mrt7alBean.DataBean datesBean, int pos) {

    }


    public void showPolicyDialog(String message, Activity activity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        PolicyDialogBinding policyDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.policy_dialog, null, false);
        policyDialogBinding.content.setText(message);
        policyDialogBinding.okButton.setOnClickListener(v -> {
            fragmentBinding.policyCheckBox.setChecked(true);
            dialog.cancel();
        });
        policyDialogBinding.close.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(policyDialogBinding.getRoot());
        dialog.show();
    }



}