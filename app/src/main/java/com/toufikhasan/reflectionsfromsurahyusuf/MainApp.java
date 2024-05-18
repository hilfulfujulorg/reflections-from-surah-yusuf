package com.toufikhasan.reflectionsfromsurahyusuf;

import static com.toufikhasan.reflectionsfromsurahyusuf.HomeFragment.ADS_SHOW_STATUS;
import static com.toufikhasan.reflectionsfromsurahyusuf.InterstitialAdManager.IMAGE_ADS_ID;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainApp extends Application {
    private static boolean ADS_STATUS = false;
    DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();

        if (InternetConnected.isConnected(this)) {
            adsSetting(this);
        }
    }


    private void adsSetting(Context context) {
        myRef = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.AdsSetting));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ADS_STATUS = Boolean.TRUE.equals(snapshot.child("Show").getValue(boolean.class));
                IMAGE_ADS_ID = snapshot.child("ImageAds").getValue(String.class);
                if (ADS_STATUS) {
                    ADS_SHOW_STATUS = true;
                    if (InterstitialAdManager.interstitialAd == null) {
                        InterstitialAdManager.loadAdsInterstitial(context, IMAGE_ADS_ID);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
