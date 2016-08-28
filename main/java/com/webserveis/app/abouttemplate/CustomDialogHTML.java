package com.webserveis.app.abouttemplate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomDialogHTML extends DialogFragment {
    private static final String TAG = "CustomDialogHTML";

    public static CustomDialogHTML newInstance(String title, Uri uri) {
        CustomDialogHTML frag = new CustomDialogHTML();
        Bundle args = new Bundle();
        String _uri = (uri != null) ? uri.toString() : "";
        args.putString("title", title);
        args.putString("uri", _uri);
        frag.setArguments(args);
        return frag;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "onCreateDialog: ");

        String title = getArguments().getString("title", "Dialog");
        Uri uri = Uri.parse(getArguments().getString("uri", ""));

        Log.d(TAG, "uri: " + uri.toString());

        String fileName = uri.getHost();

        InputStream inputStream = getResources().openRawResource(
                getResources().getIdentifier(fileName,
                        "raw", getActivity().getApplicationContext().getPackageName()));

        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder contentFile = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                contentFile.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        SpannableString msg;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            msg = (SpannableString) Html.fromHtml(contentFile.toString(),Html.FROM_HTML_MODE_LEGACY);
        } else {
            msg = new SpannableString(Html.fromHtml(contentFile.toString()));
        }

        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton(getResources().getString(R.string.about_btn_close_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: Positive");
                dismiss();
            }
        });

        return builder.create();
    }


}