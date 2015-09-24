package vit.vithandbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import vit.vithandbook.helperClass.ScrollableImageView;

import vit.vithandbook.R;


public class MapFragment extends BackHandlerFragment {

    private ImageView imageView;
    View rootView;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        new BitmapLoaderTask().execute("vit_map.jpg");
        return rootView;
    }
    private void setImageBitmap(Bitmap bmp) {
        imageView = new ScrollableImageView(getActivity());
        imageView.setLayoutParams(new LayoutParams(bmp.getWidth(), bmp.getHeight()));
        imageView.setImageBitmap(bmp);
        ((LinearLayout)rootView).addView(imageView);
    }

    private class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            AssetManager assets = getActivity().getAssets();
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(assets.open(params[0]));
            } catch (IOException e) {
                Log.e("VIThandbookImage", e.getMessage(), e);
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            setImageBitmap(result);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
