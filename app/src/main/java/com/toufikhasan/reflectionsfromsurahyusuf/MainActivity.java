package com.toufikhasan.reflectionsfromsurahyusuf;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int UPDATE_IN_APP_CODE = 4545;
    Toolbar toolbar;
    DrawerLayout drawerLayoutMain;
    ActionBarDrawerToggle toggle;
    NavController navController;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
        });

        toolbar = findViewById(R.id.mApplicationToolbar);
        bundle = new Bundle();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);

        // Set the secure flag on the window
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // TODO: Toggle Menu
        drawerLayoutMain = findViewById(R.id.drawerLayout);

        // Banner Ads

        // Drawable Navigation Show Toggle
        toggle = new ActionBarDrawerToggle(this, drawerLayoutMain, toolbar, R.string.openNavigation, R.string.closeNavigation);
        drawerLayoutMain.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_justification_icon);

        NavigationView navigationView = findViewById(R.id.navigation_view);

        navController = Navigation.findNavController(this, R.id.fragmentLayout);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Register options menu
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_home).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.writter).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.privacy).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.update_app).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.aber_vinno_kichu_hok_app).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.ahobban_app).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.moreApp).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.ratting).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.contact_us).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.website).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.shair).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.facebook_page).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.facebook_group).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.youtube).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.linkedin).setOnMenuItemClickListener(this::onOptionsItemSelected);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutMain.closeDrawer(GravityCompat.START);
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.nav_home) {
                    // Navigate to the destination fragment
                    navController.navigateUp();
                }
            }
            return true;
        } else if (id == R.id.writter) {
            if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutMain.closeDrawer(GravityCompat.START);
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.chapterViewFragment) {
                    // Navigate to the destination fragment
                    bundle.putString("c_title", "লেখকের বিবারণী");
                    bundle.putInt("c_id", 15);
                    navController.navigate(R.id.chapterViewFragment, bundle);
                }
            }
            return true;
        } else if (id == R.id.privacy) {
            gotoUrlRedirect("https://blog.hilfulfujul.org/p/privacy-policy-for-reflections-froms.html");
            return true;
        } else if (id == R.id.update_app) {
            in_application_update();
            return true;
        } else if (id == R.id.aber_vinno_kichu_hok_app) {
            gotoUrlRedirect("https://play.google.com/store/apps/details?id=com.toufikhasan.abarvinnokichuhok");
            return true;
        } else if (id == R.id.ahobban_app) {
            gotoUrlRedirect("https://play.google.com/store/apps/details?id=com.toufikhasan.ahobban");
            return true;
        } else if (id == R.id.moreApp) {
            gotoUrlRedirect("https://play.google.com/store/apps/dev?id=5871408368342725724");
            return true;
        } else if (id == R.id.ratting) {
            in_application_review();
            return true;
        } else if (id == R.id.contact_us) {
            if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutMain.closeDrawer(GravityCompat.START);
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.chapterViewFragment) {
                    // Navigate to the destination fragment
                    bundle.putString("c_title", "যোগাযোগ");
                    bundle.putInt("c_id", 121);
                    navController.navigate(R.id.chapterViewFragment, bundle);
                }
            }
            return true;
        } else if (id == R.id.website) {
            gotoUrlRedirect("https://hilfulfujul.org/?ref=reflectionsfromsurahyusuf_android_application");
            return true;
        } else if (id == R.id.shair) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Reflections From Surah Yusuf App contact");
            String shareMassage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMassage);

            startActivity(Intent.createChooser(shareIntent, "ShareVia"));
            return true;
        } else if (id == R.id.facebook_page) {
            gotoUrlRedirect("https://hilfulfujul.org/facebook/");
            return true;
        } else if (id == R.id.facebook_group) {
            gotoUrlRedirect("https://hilfulfujul.org/facebook-group/");
            return true;
        } else if (id == R.id.youtube) {
            gotoUrlRedirect("https://hilfulfujul.org/youtube/");
            return true;
        } else if (id == R.id.linkedin) {
            gotoUrlRedirect("https://hilfulfujul.org/linkedin/");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void in_application_update() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.

                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, UPDATE_IN_APP_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "এখনো আপডেট আসে নাই!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void in_application_review() {

        ReviewManager manager = ReviewManagerFactory.create(this);

        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                assert reviewInfo != null;
                Task<Void> voidTask = manager.launchReviewFlow(this, reviewInfo);

                voidTask.addOnSuccessListener(unused -> Toast.makeText(this, "রেটিং দেওয়ার জন্য ধন্যবাদ.", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "Something ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotoUrlRedirect(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_IN_APP_CODE) {
            Toast.makeText(this, "Updating now...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp() || navController.popBackStack();
    }
}