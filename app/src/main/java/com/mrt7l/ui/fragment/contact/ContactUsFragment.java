package com.mrt7l.ui.fragment.contact;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.ContactUsFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.model.ContactUsModel;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

public class ContactUsFragment extends Fragment implements ContactInterface {

    private ContactUsViewModel mViewModel;
    private ContactUsFragmentBinding binding;
    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }
    private AboutAppResponse aboutAppResponse;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.contact_us_fragment, container, false);
        aboutAppResponse = AboutAppResponse.getInstance();
        mViewModel = new ViewModelProvider(this).get(ContactUsViewModel.class);
        mViewModel.init(getActivity(),this);
        if (aboutAppResponse.getMrt7al() == null){
            mViewModel.getAboutUs();
        } else {
            binding.firstPhone.setText(aboutAppResponse.getMrt7al().getData().getReservation_num()+ "   ادارة الحجوزات   ");
            binding.secondPhone.setText(aboutAppResponse.getMrt7al().getData().getSupport_num()+ "   الدعم الفني   ");
            binding.thirdPhone.setText(aboutAppResponse.getMrt7al().getData().getCustomer_num()+ "   الشكاوي والاقتراحات   ");

        }

        binding.send.setOnClickListener(view -> {
            if (checkInputs()){
                DialogsHelper.showProgressDialog(getActivity(),getString(R.string.sending));
                mViewModel.contact(binding.nameEt.getText().toString(),binding.phoneEt.getText().toString(),
                        binding.notesEt.getText().toString(),
                        binding.emailEt.getText().toString());
            }
        });

        binding.whatsAppLayout.setOnClickListener(view -> {
            openWhatsappContact(aboutAppResponse.getMrt7al().getData().getMobile_whats());
        });
        binding.phones.setOnClickListener(view -> {
            binding.phonesLayout.setVisibility(View.VISIBLE);
            binding.contactLayout.setVisibility(View.GONE);
        });
        binding.contacts.setOnClickListener(view -> {
            binding.phonesLayout.setVisibility(View.GONE);
            binding.contactLayout.setVisibility(View.VISIBLE);
        });

        binding.firstPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + aboutAppResponse.getMrt7al().getData().getReservation_num()));
            startActivity(intent);
        });
        binding.secondPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + aboutAppResponse.getMrt7al().getData().getSupport_num()));
            startActivity(intent);
        });
        binding.thirdPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + aboutAppResponse.getMrt7al().getData().getCustomer_num()));
            startActivity(intent);
        });

        return binding.getRoot();
    }

    private boolean checkInputs() {
        if (binding.nameEt.getText().toString().isEmpty()) {
            DialogsHelper.showTextError(binding.nameEt, getActivity());
            return false;
        } else if (binding.phoneEt.getText().toString().isEmpty()) {
            DialogsHelper.showTextError(binding.phoneEt, getActivity());
            return false;
        }  else if (binding.emailEt.getText().toString().isEmpty()) {
            DialogsHelper.showTextError(binding.emailEt, getActivity());
            return false;
        }else if (binding.notesEt.getText().toString().isEmpty()) {
            DialogsHelper.showTextError(binding.notesEt, getActivity());
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onResponse(boolean isSuccess, ContactUsModel contactUsModel) {
        DialogsHelper.removeProgressDialog();
        if (isSuccess) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), getString(R.string.notes_sent), Toast.LENGTH_SHORT).show();
                binding.nameEt.setText("");
                binding.notesEt.setText("");
                binding.phoneEt.setText("");
            }
        }
    }

    @Override
    public void onAboutUsResponse(boolean isSuccess, AboutAppResponse aboutAppResponse) {
        if (isSuccess){
            binding.firstPhone.setText(aboutAppResponse.getMrt7al().getData().getReservation_num()+ "   ادارة الحجوزات   ");
            binding.secondPhone.setText(aboutAppResponse.getMrt7al().getData().getSupport_num()+ "   الدعم الفني   ");
            binding.thirdPhone.setText(aboutAppResponse.getMrt7al().getData().getCustomer_num()+ "   الشكاوي والاقتراحات   ");

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
                }else {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();

                    try {
                        assert body != null;
                        Log.v("dddddssss",body.string());
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
        } else
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();

    }

    void openWhatsappContact(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }



}