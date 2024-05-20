package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.droidsonroids.gif.GifImageView;

public class OrderFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        GifImageView photo1 = rootView.findViewById(R.id.photo1);
        GifImageView photo2 = rootView.findViewById(R.id.photo2);
        GifImageView photo3 = rootView.findViewById(R.id.photo3);
        GifImageView photo4 = rootView.findViewById(R.id.photo4);
        GifImageView photo5 = rootView.findViewById(R.id.photo5);
        GifImageView photo6 = rootView.findViewById(R.id.photo6);
        GifImageView photo7 = rootView.findViewById(R.id.photo7);
        GifImageView photo8 = rootView.findViewById(R.id.photo8);

        photo1.setOnClickListener(v -> startGifActivity(Gif1Activity.class, R.drawable.order1));
        photo2.setOnClickListener(v -> startGifActivity(Gif2Activity.class, R.drawable.order2));
        photo3.setOnClickListener(v -> startGifActivity(Gif3Activity.class, R.drawable.order3));
        photo4.setOnClickListener(v -> startGifActivity(Gif4Activity.class, R.drawable.order4));
        photo5.setOnClickListener(v -> startGifActivity(Gif5Activity.class, R.drawable.order5));
        photo6.setOnClickListener(v -> startGifActivity(Gif6Activity.class, R.drawable.order6));
        photo7.setOnClickListener(v -> startGifActivity(Gif7Activity.class, R.drawable.order7));
        photo8.setOnClickListener(v -> startGifActivity(Gif8Activity.class, R.drawable.order8));

        return rootView;
    }

    private void startGifActivity(Class<?> activityClass, int imageResourceId) {
        Intent intent = new Intent(getContext(), activityClass);
        intent.putExtra("imageResourceId", imageResourceId);
        startActivity(intent);
    }
}
