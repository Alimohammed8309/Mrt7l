package com.mrt7l.ui.fragment.favourite;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.FavouriteFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class FavouriteFragment extends Fragment implements FavouriteInterface, FavouriteAdapter.ClickListener {

    private FavouriteViewModel mViewModel;
    private String token;
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }
    private FavouriteFragmentBinding binding;
    private int favPosition;
    private MyFavouriteResponse myFavouriteResponse;
    private final ArrayList<MyFavouriteResponse.Mrt7alBean.DataBean> arrayList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false);
            myFavouriteResponse = MyFavouriteResponse.getInstance();

        binding.favouriteRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        token = new PreferenceHelper(requireActivity()).getTOKEN();
        if (myFavouriteResponse.getMrt7al() == null) {
            binding.mainProgress.setVisibility(View.VISIBLE);
            mViewModel.init(this, token);
        } else {
            arrayList.clear();
            arrayList.addAll(myFavouriteResponse.getMrt7al().getData());
            adapter = new FavouriteAdapter(requireActivity(), arrayList, this);
            binding.favouriteRecycler.setAdapter(adapter);
            binding.favouriteRecycler.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    private FavouriteAdapter adapter;
    @Override
    public void onResponse(boolean isSuccess, MyFavouriteResponse contactUsModel) {
        myFavouriteResponse = contactUsModel;
         binding.mainProgress.setVisibility(View.GONE);
        arrayList.clear();
        if (isSuccess) {
            if (contactUsModel.getMrt7al().getData().size() > 0) {
                arrayList.addAll(contactUsModel.getMrt7al().getData());
                adapter = new FavouriteAdapter(requireActivity(), arrayList, this);
                binding.favouriteRecycler.setAdapter(adapter);
                binding.favouriteRecycler.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
            } else {
                binding.favouriteRecycler.setVisibility(View.GONE);
                binding.noData.setVisibility(View.VISIBLE);
            }
        }
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
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

        binding.mainProgress.setVisibility(View.GONE);
        binding.favouriteRecycler.setVisibility(View.GONE);
        binding.noData.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFavouriteAd(boolean isSuccess) {
        DialogsHelper.removeProgressDialog();
        arrayList.remove(favPosition);
        adapter.notifyDataSetChanged();
        if(arrayList.size() == 0){
            binding.noData.setVisibility(View.VISIBLE);
            binding.favouriteRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDelete(MyFavouriteResponse.Mrt7alBean.DataBean dataBean, int pos) {
        favPosition = pos;
        DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));
        mViewModel.addToFavourite(token,String.valueOf(
                myFavouriteResponse.getMrt7al().getData().get(pos).getCompany_id()),"dislike");
    }

    @Override
    public void onClick(MyFavouriteResponse.Mrt7alBean.DataBean datesBean) {
        NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
        Bundle bundle = new Bundle();
        bundle.putString("companyID", String.valueOf(datesBean.getCompany_id()));
        navController.navigate(R.id.action_favouriteFragment_to_companyDetailsFragment,bundle);
    }


}