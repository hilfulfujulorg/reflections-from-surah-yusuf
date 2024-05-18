package com.toufikhasan.reflectionsfromsurahyusuf;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BannerAd {
    private static Context mContext;
    private static BannerAd instance;
    private final AdView adView;
    private static String mBannerAdsId;

    private BannerAd() {
        adView = new AdView(mContext);
        adView.setAdUnitId(mBannerAdsId);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
    }

    public static BannerAd getInstance(Context context, String bannerAdsId) {
        mContext = context;
        mBannerAdsId = bannerAdsId;
        if (instance == null) {
            instance = new BannerAd();
        }
        return instance;
    }

    public AdView getAdView() {
        return adView;
    }
}
