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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements OnChapterClickListener {
    RecyclerView chapterRecyclerView;
    Bundle bundle;
    NavController navController;
    String ImageAdsId;
    DatabaseReference myRef;

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

    private void adsSetting() {
        myRef = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.AdsSetting));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean AdsStatus = Boolean.TRUE.equals(snapshot.child("Show").getValue(boolean.class));
                ImageAdsId = snapshot.child("ImageAds").getValue(String.class);
                if (AdsStatus) {
                    InterstitialAdManager.showAdIfAvailable(requireActivity(), ImageAdsId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onChapterClick(int id, String chapter) {
        bundle = new Bundle();
        bundle.putString("c_title", chapter);
        bundle.putInt("c_id", id);

        if (InternetConnected.isConnected(requireContext())) {
            adsSetting();
        }

        navController.navigate(R.id.chapterViewFragment, bundle);
    }
}