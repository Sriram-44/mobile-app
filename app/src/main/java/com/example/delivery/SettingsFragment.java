package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingsFragment extends Fragment {

    // Your existing code

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button buttonAccountSettings = rootView.findViewById(R.id.buttonAccountSettings);
        Button buttonAppSettings = rootView.findViewById(R.id.buttonAppSettings);
        Button buttonAbout = rootView.findViewById(R.id.buttonAbout);

        buttonAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccountSettings();
            }
        });

        buttonAppSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppSettings();
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAboutVisibility();
            }
        });

        return rootView;
    }

    private void openAccountSettings() {
        Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
        startActivity(intent);
    }

    private void openAppSettings() {
        Intent intent = new Intent(getActivity(), AppSettingsActivity.class);
        startActivity(intent);
    }

    private void toggleAboutVisibility() {
        // Toggle the visibility of the about details layout
        View layoutAboutDetails = getView().findViewById(R.id.layoutAboutDetails);
        if (layoutAboutDetails.getVisibility() == View.VISIBLE) {
            layoutAboutDetails.setVisibility(View.GONE);
        } else {
            layoutAboutDetails.setVisibility(View.VISIBLE);
        }
    }
}
