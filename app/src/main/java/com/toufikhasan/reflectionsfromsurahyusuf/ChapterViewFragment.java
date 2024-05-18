package com.toufikhasan.reflectionsfromsurahyusuf;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ChapterViewFragment extends Fragment {
    WebView webView;
    String chapterTitle;
    int chapterId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter_view, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            chapterTitle = args.getString("c_title");
            chapterId = args.getInt("c_id");
        }

        requireActivity().setTitle(chapterTitle);

        String url = "chapter/" + chapterId + ".html";

        webView = view.findViewById(R.id.webview);
        webView.setOnLongClickListener(v -> true);
        webView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Long press detected, prevent screenshot
                return event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout();
            }
            return false;
        });
        webView.getSettings().setBuiltInZoomControls(false);


        AssetManager assetManager = requireActivity().getAssets();
        String html;
        try {
            InputStream inputStream = assetManager.open(url);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            html = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            html = "<html><body><h4 style='color:red'>Error loading chapter file!</h1></body></html>";
        }

        String htmlContent = "<html><head><link rel='stylesheet' type='text/css' href='file:///android_asset/css/style.css'></head><body><h1 style='text-align:centre;'>" + chapterTitle + "</h1>" + html + "<br/></body></html>";

        String baseUrl = "file:///android_asset/";
        webView.loadDataWithBaseURL(baseUrl, htmlContent, "text/html", "UTF-8", null);

    }

    @Override
    public void onDestroy() {
        requireActivity().setTitle(getString(R.string.app_name));
        super.onDestroy();
    }
}