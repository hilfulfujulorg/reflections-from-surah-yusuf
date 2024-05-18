package com.toufikhasan.reflectionsfromsurahyusuf;


import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdManager {

    private static final long AD_SHOW_INTERVAL = 30000; // 30 Seconds in milliseconds
    public static InterstitialAd interstitialAd;
    public static String IMAGE_ADS_ID = "ca-app-pub-5980068077636654/8186929781";
    private static CountDownTimer countDownTimer;

    public static void loadAdsInterstitial(Context mContext, String imageAdsId) {
        IMAGE_ADS_ID = imageAdsId;
        if (interstitialAd == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(mContext, IMAGE_ADS_ID, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    InterstitialAdManager.interstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    InterstitialAdManager.interstitialAd = null;
                }
            });
        } else {
            startCountdownTimer(() -> loadAdsInterstitial(mContext, IMAGE_ADS_ID));
        }
    }

    public static void showAdIfAvailable(Context context) {
        if (interstitialAd != null) {
            interstitialAd.show((Activity) context);
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    interstitialAd = null;
                    startCountdownTimer(() -> loadAdsInterstitial(context, IMAGE_ADS_ID));
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    interstitialAd = null;
                    startCountdownTimer(() -> loadAdsInterstitial(context, IMAGE_ADS_ID));
                }
            });
        } else {
            startCountdownTimer(() -> loadAdsInterstitial(context, IMAGE_ADS_ID));
        }
    }

    private static void startCountdownTimer(TimeListener timeListener) {
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
                if (timeListener != null) {
                    timeListener.onTimeCountFinished();
                }
            }
        };
        countDownTimer.start();
    }

    private interface TimeListener {
        void onTimeCountFinished();
    }
}