package com.satiate.emelie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.satiate.emelie.R;

import java.util.ArrayList;

/**
 * Created by Rishabh Bhatia on 23/10/16.
 */

public class PhotosDetailsVerticalPhotoAdapter extends RecyclerView.Adapter<PhotosDetailsVerticalPhotoAdapter.PhotosViewHolder> {

    private Context context;
    private ArrayList<String> photoUrls;

    public PhotosDetailsVerticalPhotoAdapter(Context context, ArrayList<String> photoUrls) {
        this.context = context;
        this.photoUrls = photoUrls;
    }

    @Override
    public PhotosDetailsVerticalPhotoAdapter.PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return  new PhotosViewHolder(layoutInflater.inflate(R.layout.row_photos_details_vertical, parent, false));

    }

    @Override
    public void onBindViewHolder(PhotosDetailsVerticalPhotoAdapter.PhotosViewHolder holder, int position) {

        String photoUrl = photoUrls.get(position);
        Glide.with(context).load(photoUrl).crossFade().into(holder.ivPhoto);

        animate(holder);
    }

    @Override
    public int getItemCount() {
        return photoUrls.size();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.overshoot);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_row_photos_vertical);
        }
    }
}


