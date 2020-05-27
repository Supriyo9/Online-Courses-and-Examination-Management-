package com.example.mylearning;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment {

    onFragmentSettingSelected listener;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        Button chngpass = view.findViewById(R.id.Changepassword);
        chngpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                listener.onChangePassword();
            }
        });


        return view;

    }

    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof MainFragment.onFragmentBtnSelected) {
            listener = (FragmentSetting.onFragmentSettingSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onFragmentSettingSelected {

        public void onChangePassword();
    }


}
