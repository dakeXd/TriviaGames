package com.example.triviagames.fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultimediaImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultimediaImageFragment extends MultimediaFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView iv;
    private TextView tv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MultimediaImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultimediaImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultimediaImageFragment newInstance(String param1, String param2) {
        MultimediaImageFragment fragment = new MultimediaImageFragment();
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
        View root = inflater.inflate(R.layout.fragment_multimedia_image, container, false);
        iv = root.findViewById(R.id.imageViewFragment);
        tv = root.findViewById(R.id.tv_question2);
        start();
        return root;
    }

    @Override
    public void start() {
        if(ready){
            Resources resources = getResources();
            Drawable imageId = resources.getDrawable(resources.getIdentifier(multimediaSource, "drawable", getContext().getPackageName()));
            iv.setImageDrawable(imageId);
            tv.setText(question);
        }

    }
}