package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView horizontalRecyclerView;
    private HorizontalAdapter horizontalAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        horizontalRecyclerView = rootView.findViewById(R.id.horizontalRecyclerView);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontalAdapter = new HorizontalAdapter(getHorizontalItems());
        horizontalRecyclerView.setAdapter(horizontalAdapter);

        return rootView;
    }

    private List<List<Integer>> getHorizontalItems() {
        List<List<Integer>> horizontalItems = new ArrayList<>();

        List<Integer> row1 = new ArrayList<>();
        row1.add(R.drawable.order1);
        row1.add(R.drawable.order2);
        horizontalItems.add(row1);

        List<Integer> row2 = new ArrayList<>();
        row2.add(R.drawable.order3);
        row2.add(R.drawable.order4);
        horizontalItems.add(row2);

        List<Integer> row3 = new ArrayList<>();
        row3.add(R.drawable.order5);
        row3.add(R.drawable.order6);
        horizontalItems.add(row3);

        List<Integer> row4 = new ArrayList<>();
        row4.add(R.drawable.order7);
        row4.add(R.drawable.order8);
        horizontalItems.add(row4);

        return horizontalItems;
    }

    private static class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
        private final List<List<Integer>> horizontalItems;

        HorizontalAdapter(List<List<Integer>> horizontalItems) {
            this.horizontalItems = horizontalItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_row_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            List<Integer> verticalItems = horizontalItems.get(position);
            holder.verticalPagerAdapter.setData(verticalItems);
        }

        @Override
        public int getItemCount() {
            return horizontalItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ViewPager verticalViewPager;
            VerticalPagerAdapter verticalPagerAdapter;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                verticalViewPager = itemView.findViewById(R.id.verticalViewPager);
                verticalPagerAdapter = new VerticalPagerAdapter();
                verticalViewPager.setAdapter(verticalPagerAdapter);
            }
        }
    }

    private static class VerticalPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
        private List<Integer> verticalItems = new ArrayList<>();

        void setData(List<Integer> verticalItems) {
            this.verticalItems = verticalItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return verticalItems.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.vertical_item, container, false);
            ImageView imageView = itemView.findViewById(R.id.imageView);
            imageView.setImageResource(verticalItems.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int drawableId = verticalItems.get(position);
                    if (drawableId == R.drawable.order1) {
                        intent = new Intent(container.getContext(), Gif1Activity.class);
                    } else if (drawableId == R.drawable.order2) {
                        intent = new Intent(container.getContext(), Gif2Activity.class);
                    } else if (drawableId == R.drawable.order3) {
                        intent = new Intent(container.getContext(), Gif3Activity.class);
                    } else if (drawableId == R.drawable.order4) {
                        intent = new Intent(container.getContext(), Gif4Activity.class);
                    } else if (drawableId == R.drawable.order5) {
                        intent = new Intent(container.getContext(), Gif5Activity.class);
                    } else if (drawableId == R.drawable.order6) {
                        intent = new Intent(container.getContext(), Gif6Activity.class);
                    } else if (drawableId == R.drawable.order7) {
                        intent = new Intent(container.getContext(), Gif7Activity.class);
                    } else if (drawableId == R.drawable.order8) {
                        intent = new Intent(container.getContext(), Gif8Activity.class);
                    } else {
                        intent = null;
                    }
                    if (intent != null) {
                        container.getContext().startActivity(intent);
                    }
                }
            });
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
