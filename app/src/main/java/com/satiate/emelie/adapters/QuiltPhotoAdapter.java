package com.satiate.emelie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.satiate.emelie.R;
import com.satiate.emelie.models.Story;

import java.util.ArrayList;

/**
 * Created by Rishabh Bhatia on 22/10/16.
 */

public class QuiltPhotoAdapter extends BaseAdapter {

    private Context context;
    private Story story;
    private ArrayList<String> photos;

    public QuiltPhotoAdapter(Context context, Story story) {
        this.context = context;
        this.story = story;
        this.photos = story.getPhotos();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.row_photos_details_horizontal, parent, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_row_photos);
        final RelativeLayout rvPhotos = (RelativeLayout) view.findViewById(R.id.rv_row_photos);

        Glide.with(context).load(photos.get(position)).asBitmap().dontAnimate().placeholder(R.drawable.background).
                into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,
                        Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                      /*  GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(
                                new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));

                        rvPhotos.setLayoutParams(gridLayoutParams);
                        imageView.setLayoutParams(layoutParams);*/

                        imageView.setImageBitmap(bitmap);
                    }
                });
        return view;
    }


}
