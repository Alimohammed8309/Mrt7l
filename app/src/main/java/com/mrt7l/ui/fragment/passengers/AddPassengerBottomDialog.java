package com.mrt7l.ui.fragment.passengers;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mrt7l.R;
import com.mrt7l.databinding.AddPassengerBottomDialogBinding;

import java.lang.ref.WeakReference;

public class AddPassengerBottomDialog extends BottomSheetDialogFragment

{
     public static AddPassengerBottomDialog newInstance(ChoosePassengerDialogInterface choosePassengerDialogInterface) {
        setNavigator(choosePassengerDialogInterface);
        return new AddPassengerBottomDialog();
    }

    AddPassengerBottomDialogBinding bottomDialogBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomDialogBinding = DataBindingUtil.inflate(inflater,R.layout.add_passenger_bottom_dialog,container,false);
        bottomDialogBinding.pastPassenger.setOnClickListener(view -> {
            getNavigator().onPastPassenger();
        });
        bottomDialogBinding.newPassenger.setOnClickListener(view -> {
            getNavigator().onNewPassenger();
        });
        return bottomDialogBinding.getRoot();

    }

    public interface ChoosePassengerDialogInterface {

        void onNewPassenger();
        void  onPastPassenger();

    }

    private static WeakReference<ChoosePassengerDialogInterface> mNavigator;

    private static ChoosePassengerDialogInterface getNavigator() {
        return mNavigator.get();
    }

    private static void setNavigator(ChoosePassengerDialogInterface navigator) {
        mNavigator = new WeakReference<>(navigator);
    }


}

