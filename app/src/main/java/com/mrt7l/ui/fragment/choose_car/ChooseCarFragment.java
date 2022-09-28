package com.mrt7l.ui.fragment.choose_car;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrt7l.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCarFragment extends Fragment {


    public ChooseCarFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
//    public static ChooseCarFragment newInstance(String param1, String param2) {
//        ChooseCarFragment fragment = new ChooseCarFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_car, container, false);
    }
}