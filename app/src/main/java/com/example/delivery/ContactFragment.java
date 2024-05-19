package com.example.delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFragment extends Fragment {

    private TextView phoneNumberTextView;
    private TextView websiteTextView;

    public ContactFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        // Initialize views
        phoneNumberTextView = rootView.findViewById(R.id.phoneNumberTextView);
        websiteTextView = rootView.findViewById(R.id.websiteTextView);

        // Set click listeners
        phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberTextView.getText().toString();
                initiatePhoneCall(phoneNumber);
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = websiteTextView.getText().toString();
                openWebsite(websiteUrl);
            }
        });

        return rootView;
    }

    private void initiatePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void openWebsite(String websiteUrl) {
        Uri webpage = Uri.parse(websiteUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
