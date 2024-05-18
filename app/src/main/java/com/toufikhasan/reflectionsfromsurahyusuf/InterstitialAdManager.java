package com.toufikhasan.reflectionsfromsurahyusuf;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdManager {

    private static final long AD_SHOW_INTERVAL = 40000; // 40 Seconds in milliseconds
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mImageAdsId;
    private static InterstitialAd interstitialAd;
    private static long lastAdShowTime = 0;
    private static CountDownTimer countDownTimer;

    public static void showAdIfAvailable(Context context, String imageAdsId) {
        mContext = context;
        mImageAdsId = imageAdsId;
        if (isAdAvailable()) {
            if (interstitialAd != null) {
                interstitialAd.show((Activity) context);
                lastAdShowTime = System.currentTimeMillis();
            } else {
                loadAd();
            }
        }
    }

    private static boolean isAdAvailable() {
        long elapsedTimeSinceLastAdShow = System.currentTimeMillis() - lastAdShowTime;
        return elapsedTimeSinceLastAdShow >= AD_SHOW_INTERVAL;
    }

    private static void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(mContext, mImageAdsId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                InterstitialAdManager.interstitialAd = interstitialAd;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        InterstitialAdManager.interstitialAd = null;
                        startCountdownTimer();

                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                InterstitialAdManager.interstitialAd = null;
            }
        });
    }

    private static void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(AD_SHOW_INTERVAL, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Not used
            }

            @Override
            public void onFinish() {
                if (interstitialAd == null) {
                    loadAd();
                }
            }
        };
        countDownTimer.start();
    }
}