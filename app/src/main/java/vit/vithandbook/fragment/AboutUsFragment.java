package vit.vithandbook.fragment;

/**
 * Created by Hemant on 2/8/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import vit.vithandbook.R;

/**
 * Created by Hemant on 1/21/2016.
 */
public class AboutUsFragment extends BackHandlerFragment {
    View rootView;
    private Bitmap bmp;
    ImageView imv;
    public AboutUsFragment() {
        // Required empty public constructor
    }

    //TODO add other profiles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        imv = (ImageView) rootView.findViewById(R.id.con_image);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL("https://scontent-ams3-1.xx.fbcdn.net/hphotos-xap1/t31.0-8/s960x960/1517797_10202693295270073_5995775202112481411_o.jpg").openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    imv.setImageBitmap(bmp);
            }

        }.execute();
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
