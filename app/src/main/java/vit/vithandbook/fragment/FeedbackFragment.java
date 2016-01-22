package vit.vithandbook.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vit.vithandbook.R;

/**
 * Created by Hemant on 1/21/2016.
 */
public class FeedbackFragment extends BackHandlerFragment {
    View rootView;
    Button feedbackButton;
    TextView fromName, message;
    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        feedbackButton = (Button) rootView.findViewById(R.id.send_button);
        fromName = (TextView) rootView.findViewById(R.id.from_text);
        message = (TextView) rootView.findViewById(R.id.feedback_text);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedBack(view);
            }
        });

        return rootView;
    }
    void sendFeedBack(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType(getResources().getString(R.string.INTENT_TYPE_PLAIN));
        intent.setData(Uri.parse(getResources().getString(R.string.FEEDBACK_EMAIL_LINK)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.FEEDBACK_EMAIL_SUBJECT));
        startActivity(intent);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

}
