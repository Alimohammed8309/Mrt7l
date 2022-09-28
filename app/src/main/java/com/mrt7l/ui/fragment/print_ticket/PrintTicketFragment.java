package com.mrt7l.ui.fragment.print_ticket;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrt7l.R;
import com.mrt7l.databinding.PrintTicketFragment2Binding;
import com.mrt7l.databinding.PrintTicketFragmentBinding;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.mytrips.CurrentOrdersResponse;

public class PrintTicketFragment extends Fragment {

    private PrintTicketViewModel mViewModel;

    public static PrintTicketFragment newInstance() {
        return new PrintTicketFragment();
    }
    private PrintTicketFragment2Binding binding;
    private CurrentOrdersResponse.Mrt7alBean.DataBean dataBean;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.print_ticket_fragment2, container, false);
        mViewModel = new ViewModelProvider(this).get(PrintTicketViewModel.class);

        return binding.getRoot();
    }



    @Override
    public void onResume() {
        super.onResume();

    }
}