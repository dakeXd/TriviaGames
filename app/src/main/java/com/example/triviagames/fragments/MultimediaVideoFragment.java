package com.example.triviagames.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultimediaVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultimediaVideoFragment extends MultimediaFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private View root;

    private WebView webView;
    private VideoView videoView;
    private TextView tv;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MultimediaVideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultimediaVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultimediaVideoFragment newInstance(String param1, String param2) {
        MultimediaVideoFragment fragment = new MultimediaVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root= inflater.inflate(R.layout.fragment_multimedia_video, container, false);
        videoView = (VideoView) root.findViewById(R.id.vv_f);

        MediaController mediaController = new MediaController(root.getContext());
        videoView.setMediaController(mediaController);
        tv = root.findViewById(R.id.tv_question3);
        start();
        return root;
    }

    @Override
    public void start() {
        if(ready){
            int rawId = getResources().getIdentifier(multimediaSource,  "raw", getContext().getPackageName());
            String path = "android.resource://" + getContext().getPackageName() + "/" + rawId;
            videoView.setVideoURI(Uri.parse(path));
            tv.setText(question);
        }
    }
}