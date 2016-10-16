package com.satiate.emelie.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.base.EmelieFragment;
import com.satiate.emelie.camera.CameraUtils;
import com.satiate.emelie.utils.Const;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 10/14/2016.
 */

public class AddStoryFragment extends EmelieFragment {

    @BindView(R.id.iv_add_story)
    ImageView ivAddStory;

    private Uri imageUri;
    private EmelieActivity activity;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public static AddStoryFragment newInstance() {
        return new AddStoryFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof EmelieActivity)
        {
            activity = (EmelieActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_story, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(Const.TAG, "view created called");
        checkForCameraPermission();
    }

    @Override
    public String onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        ivAddStory.setImageBitmap(bitmap);
                        Log.d(Const.TAG, "photo uploaded");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public void takePhoto()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        AddStoryFragment.this.startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                try {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    {
                        Log.d(Const.TAG, "Got camera permission");
                        takePhoto();
                    } else
                    {
                        checkForCameraPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    checkForCameraPermission();
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public void checkForCameraPermission()
    {
        try {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

            int hasStoragePermission = ContextCompat.checkSelfPermission(activity,
                    permissions[0]);
            int hasCameraPermission = ContextCompat.checkSelfPermission(activity,
                    permissions[1]);

            Log.d(Const.TAG, "Has storage permission is: " + hasStoragePermission);
            Log.d(Const.TAG, "Has camera permission is: " + hasCameraPermission);


            if (hasStoragePermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
            } else if (hasCameraPermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
            } else
            {
                takePhoto();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
