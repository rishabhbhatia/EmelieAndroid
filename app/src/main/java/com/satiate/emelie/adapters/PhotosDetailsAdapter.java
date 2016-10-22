package com.satiate.emelie.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.satiate.emelie.R;
import com.satiate.emelie.images.DynamicImageView;
import com.satiate.emelie.models.Story;
import com.satiate.emelie.utils.Const;

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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new PhotosViewHolder(inflater.inflate(R.layout.row_photos_details, parent, false));
    }

    @Override
    public void onBindViewHolder(final PhotosViewHolder holder, int position) {

//        holder.clear();
        /*StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFullSpan(true);*/

        String photoUrl = story.getPhotos().get(position);

        Glide.with(context).load(photoUrl).asBitmap().placeholder(R.drawable.background).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.d(Const.TAG, "bitmap loaded "+bitmap.getHeight()+" & width "+bitmap.getWidth());

               /* RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                holder.photo.setLayoutParams(layoutParams);*/
                holder.photo.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return story.getPhotos().size();
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private DynamicImageView dynamicImageView;

        PhotosViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.iv_row_photos);
        }

        public void clear()
        {
            photo.setImageDrawable(null);
        }
    }
}
