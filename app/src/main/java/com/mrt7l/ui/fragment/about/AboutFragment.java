package com.mrt7l.ui.fragment.about;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.AboutFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

public class AboutFragment extends Fragment implements AboutInterface {

    private AboutAppResponse aboutAppResponse;
    private FaqResponse faqResponse;
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
    AboutFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.about_fragment, container, false);
        binding.questions.setOnClickListener(view -> {
            binding.questionsRecycler.setVisibility(View.VISIBLE);
            binding.versionText.setVisibility(View.GONE);
        });
        faqResponse  = FaqResponse.getInstance();
        aboutAppResponse = AboutAppResponse.getInstance();
        binding.information.setOnClickListener(view -> {
            if (aboutAppResponse.getMrt7al() !=null)
                binding.versionText.setText(aboutAppResponse.getMrt7al().getData().getApp_info());
            binding.questionsRecycler.setVisibility(View.GONE);
            binding.versionText.setVisibility(View.VISIBLE);
        });
        binding.reservePolicies.setOnClickListener(view -> {
            if (aboutAppResponse.getMrt7al() !=null)
            binding.versionText.setText(html2text(aboutAppResponse.getMrt7al().getData().getReservation_rules()));
            binding.questionsRecycler.setVisibility(View.GONE);
            binding.versionText.setVisibility(View.VISIBLE);
        });
        binding.policy.setOnClickListener(view -> {
            if (aboutAppResponse.getMrt7al() !=null)
                binding.versionText.setText(html2text(aboutAppResponse.getMrt7al().getData().getApp_rules()));
            binding.questionsRecycler.setVisibility(View.GONE);
            binding.versionText.setVisibility(View.VISIBLE);
        });
        AboutViewModel mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        if (aboutAppResponse.getMrt7al() == null){
            binding.mainProgress.setVisibility(View.VISIBLE);
            mViewModel.getAboutUs();
        }
        if(faqResponse.getMrt7al() == null) {
            binding.mainProgress.setVisibility(View.VISIBLE);
            mViewModel.init(this);
        } else {
            binding.mainProgress.setVisibility(View.GONE);
            FaqAdapter adapter = new FaqAdapter(requireActivity(),faqResponse.getMrt7al().
                    getData());
            binding.questionsRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.questionsRecycler.setAdapter(adapter);
        }


        return binding.getRoot();
    }

    public static String html2text(String html) {
       return Html.fromHtml(html).toString();

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onResponse(boolean isSuccess, AboutAppResponse contactUsModel) {
        binding.mainProgress.setVisibility(View.GONE);

        if (isSuccess){
            aboutAppResponse = contactUsModel;

        }
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        binding.mainProgress.setVisibility(View.GONE);

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
        binding.mainProgress.setVisibility(View.GONE);

    }

    @Override
    public void onFavouriteAd(boolean isSuccess) {

    }

    @Override
    public void onFaqResponse(boolean isSuccess, FaqResponse faqResponse) {
        binding.mainProgress.setVisibility(View.GONE);

        if (isSuccess) {
            FaqAdapter adapter = new FaqAdapter(requireActivity(), faqResponse
                    .getMrt7al().getData());
            binding.questionsRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.questionsRecycler.setAdapter(adapter);
        }
    }
}