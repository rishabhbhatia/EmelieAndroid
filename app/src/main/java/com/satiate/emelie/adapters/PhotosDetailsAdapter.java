package com.satiate.emelie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.satiate.emelie.R;
import com.satiate.emelie.models.Story;

/**
 * Created by Rishabh Bhatia on 20/10/16.
 */

public class PhotosDetailsAdapter extends RecyclerView.Adapter<PhotosDetailsAdapter.PhotosViewHolder> {


    private Context context;
    private Story story;

    public PhotosDetailsAdapter(Context context, Story story) {
        this.context = context;
        this.story = story;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos_details, null);
        return new PhotosViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {

        holder.clear();

        String photoUrl = story.getPhotos().get(position);

        Glide.with(context).load(photoUrl).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return story.getPhotos().size();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.iv_row_photos);
        }

        public void clear()
        {
            photo.setImageDrawable(null);
        }
    }
}
