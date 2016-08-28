package com.webserveis.app.abouttemplate;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AboutActivity extends AppCompatActivity {

    private ImageView imageHeader;
    private List<AboutItem> listItems;
    private String appName;
    private String appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //setAppBarDragging(false);
        if (imageHeader != null) {
            ((BitmapDrawable) imageHeader.getDrawable()).getBitmap().recycle();
        }
        imageHeader = (ImageView) findViewById(R.id.image_header);
        imageHeader.setImageResource(R.drawable.about_background_header);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        /*  16:9        3:2        4:3        1:1        3:4        2:3         */
        android.view.ViewGroup.LayoutParams layoutParams = imageHeader.getLayoutParams();
        layoutParams.width = metrics.widthPixels;
        layoutParams.height = getHeightAspectRatio(16f / 9f, metrics.widthPixels);
        imageHeader.setLayoutParams(layoutParams);

        appName = getApplicationName(this);
        appVersion = getApplicationVersionDisplay();

        dataSource();

        int listSize = listItems.size();
        for (int i = 0; i < listSize; i++) {
            Log.i("Member name: ", "(" + i + ")" + listItems.get(i));
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        AboutListAdapter mAdapter = new AboutListAdapter(listItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionShareToFriend();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                ActionShareToFriend();
                return true;
            case R.id.action_shownews:
                ActionShowNewFeatures();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageHeader.setImageDrawable(null);
    }

    private void dataSource() {

        listItems = new ArrayList<>();

        /* Change this values*/

        listItems.add(new AboutItem(
                appName,
                appVersion,
                R.drawable.ic_flag_black_24dp,
                null));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_upgrade_app_key),
                getResources().getString(R.string.about_upgrade_app_value),
                R.drawable.ic_new_releases_black_24dp,
                null));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_rate_app_key),
                getResources().getString(R.string.about_rate_app_value),
                R.drawable.ic_star_black_24dp,
                "https://play.google.com/store/apps/details?id=app"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_support_page_key),
                "www.supportpage.com",
                R.drawable.ic_webpage_black_24dp,
                "http://www.suppportpage.com"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_support_email_key),
                "email@support.com",
                R.drawable.ic_email_black_24dp,
                "mailto:email@support.com?subject=about%20app&body=this%20a%20body"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_community_key),
                getResources().getString(R.string.about_community_value),
                R.drawable.ic_group_work_black_24dp,
                "https://plus.google.com/communities/appcomunityid"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_feedback_key),
                getResources().getString(R.string.about_feedback_value),
                R.drawable.ic_feedback,
                "mailto:feedback@app.com"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_twitter_key),
                "@twitter_user",
                R.drawable.ic_twitter,
                "https://twitter.com/user"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_facebook_key),
                "facebook/user",
                R.drawable.ic_facebook,
                "https://www.facebook.com/user.name"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_googleplus_key),
                "+user",
                R.drawable.ic_google_plus,
                "https://plus.google.com/id"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_eula_key),
                getResources().getString(R.string.about_eula_value),
                R.drawable.ic_handshake,
                "dialog://license"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_other_license_key),
                getResources().getString(R.string.about_other_license_value),
                R.drawable.ic_quill_ink,
                "dialog://other_license"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_changelog_key),
                getResources().getString(R.string.about_changelog_value),
                R.drawable.ic_show_propety,
                "dialog://changelog"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_supportlang_key),
                getResources().getString(R.string.about_supportlang_value),
                R.drawable.ic_translate_black_24dp,
                "dialog://credits"));

        listItems.add(new AboutItem(
                getResources().getString(R.string.about_copyright_key),
                getResources().getString(R.string.about_copyright_value),
                R.drawable.ic_copyright_black_24dp,
                null));

    }

    /*
     * Other functions
     */
    public int getHeightAspectRatio(float ratio, int width) {
        return (int) (width / ratio);
    }

    @NonNull
    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

    private String getApplicationVersionDisplay() {
        String result = null;
        try {
            result = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void ActionShareToFriend() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = String.format(Locale.getDefault(),
                getResources().getString(R.string.about_action_share_content),
                appName, "htpp");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, appName);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        try {
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.about_action_share)));
        } catch (ActivityNotFoundException ex) {
            View parentLayout = findViewById(R.id.root_view);
            Snackbar.make(parentLayout, getResources().getString(R.string.about_action_share_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void ActionShowNewFeatures() {
        new CustomDialogHTML();
        Uri uri = Uri.parse("dialog://whats_the_new");
        CustomDialogHTML myDiag = CustomDialogHTML.newInstance(
                getResources().getString(R.string.about_whatsthenew_title), uri);
        //myDiag.setCancelable(false);
        myDiag.show(this.getSupportFragmentManager(), "DiaglogCustomHTML");
    }


}