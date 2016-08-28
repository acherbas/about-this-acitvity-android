package com.webserveis.app.abouttemplate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;


public class AboutListAdapter extends RecyclerView.Adapter<AboutListAdapter.ViewHolder> {

    private static final String TAG = AboutListAdapter.class.getSimpleName();
    private AppCompatActivity context;
    private List<AboutItem> listItems;

    public AboutListAdapter(List<AboutItem> listItems, Context context) {
        super();
        this.listItems = listItems;
        this.context = (AppCompatActivity) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.draw_item_about, parent, false);
        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vh.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Log.d(TAG, "onClick: position " + position);
                    AboutItem entry = listItems.get(position);
                    if (entry.getUri() != null) {
                        Uri uri = Uri.parse(entry.getUri());

                        if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
                            Log.d(TAG, "is web: " + uri.toString());
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(browserIntent);
                        } else if (uri.getScheme().equals("mailto")) {
                            Log.d(TAG, "is email: " + uri.toString());
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(browserIntent);
                        } else if (uri.getScheme().equals("dialog")) {
                            Log.d(TAG, "Show dialog: " + uri.toString());

                            new CustomDialogHTML();
                            CustomDialogHTML myDiag = CustomDialogHTML.newInstance(entry.getText(), uri);
                            //myDiag.setCancelable(false);
                            myDiag.show(context.getSupportFragmentManager(), "DiaglogCustomHTML");
                        }
                    }


                }
            }

        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AboutItem entry = listItems.get(position);
        Log.i(TAG, "onBindViewHolder: ");
        Log.d(TAG, "entry: " + entry.toString());

        holder.ivImage.setImageResource(entry.getImage());
        //holder.ivImage.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
        //holder.ivImage.setColorFilter(Color.argb(255, 255, 255, 255));
        holder.tvText.setText(entry.getText());
        holder.tvText2.setText(entry.getText2());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvText;
        public TextView tvText2;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.image);
            tvText = (TextView) itemView.findViewById(R.id.text);
            tvText2 = (TextView) itemView.findViewById(R.id.text2);

        }
    }


}
