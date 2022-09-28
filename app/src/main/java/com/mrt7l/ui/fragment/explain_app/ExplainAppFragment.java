package com.mrt7l.ui.fragment.explain_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.FragmentExplainAppBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplainAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplainAppFragment extends Fragment implements VideosInterface, VideosAdapter.ClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ExplainAppFragment() {
        // Required empty public constructor
    }


    public static ExplainAppFragment newInstance(String param1, String param2) {
        ExplainAppFragment fragment = new ExplainAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        if (DashboardActivity.tvTitle != null){
            DashboardActivity.tvTitle.setText(getString(R.string.preview_app));
        }
    }

    FragmentExplainAppBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_explain_app, container, false);
        VideosResponse videosResponse = VideosResponse.getInstance();
        VideosViewModel videosViewModel = new ViewModelProvider(requireActivity()).get(VideosViewModel.class);
        if(videosResponse.getMrt7al() == null) {
            binding.mainProgress.setVisibility(View.VISIBLE);
            videosViewModel.init(this);
        } else {
            VideosAdapter videosAdapter = new VideosAdapter(requireContext(), videosResponse.getMrt7al().getData(),this);
            binding.videosRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.videosRecycler.setAdapter(videosAdapter);
        }
//        binding.videoFile.setOnClickListener(view -> {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(aboutAppResponse.getMrt7al().getData().getAbout().getVideoAPP())));
//
//        });

        return binding.getRoot();
    }

    @Override
    public void onResponse(boolean isSuccess, VideosResponse videosResponse) {
        binding.mainProgress.setVisibility(View.GONE);
        if (isSuccess) {
            try {
                VideosAdapter videosAdapter = new VideosAdapter(requireContext(), videosResponse.getMrt7al().getData(), this);
                binding.videosRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
                binding.videosRecycler.setAdapter(videosAdapter);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
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
        } else
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavouriteAd(boolean isSuccess) {

    }

    @Override
    public void onClick(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }



}