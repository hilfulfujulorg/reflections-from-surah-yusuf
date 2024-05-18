package com.toufikhasan.reflectionsfromsurahyusuf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements OnChapterClickListener {
    public static boolean ADS_SHOW_STATUS = false;
    RecyclerView chapterRecyclerView;
    Bundle bundle;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        String[] myStringArray = getResources().getStringArray(R.array.chapter_string_array);

        chapterRecyclerView = view.findViewById(R.id.chapterRecyclerView);

        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ChapterListAdapter chapterListAdapter = new ChapterListAdapter(myStringArray, this);
        chapterRecyclerView.setAdapter(chapterListAdapter);

    }

    @Override
    public void onChapterClick(int id, String chapter) {
        bundle = new Bundle();
        bundle.putString("c_title", chapter);
        bundle.putInt("c_id", id);

        if (ADS_SHOW_STATUS) {
            InterstitialAdManager.showAdIfAvailable(requireActivity());
        }

        navController.navigate(R.id.chapterViewFragment, bundle);
    }
}