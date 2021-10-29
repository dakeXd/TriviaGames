package com.example.triviagames.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultimediaAudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultimediaAudioFragment extends MultimediaFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MediaPlayer mp;
    private Button play_pause;
    private View root;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MultimediaAudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultimediaAudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultimediaAudioFragment newInstance(String param1, String param2) {
        MultimediaAudioFragment fragment = new MultimediaAudioFragment();
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
        root = inflater.inflate(R.layout.fragment_multimedia_audio, container, false);
        play_pause = (Button) root.findViewById(R.id.btn_play);
        return root;
    }


    public void PlayPause(){

        if (mp.isPlaying()){
            mp.pause();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(root.getContext(), "Pause", Toast.LENGTH_SHORT).show();
        }else{
            mp.start();
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(root.getContext(), "Play", Toast.LENGTH_SHORT).show();


        }

    }

    public void Stop(){
        if(mp != null){
            mp.stop();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(root.getContext(), "Stop", Toast.LENGTH_SHORT).show();
        }
    }

}