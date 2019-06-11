package com.example.lee.deme_two.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lee.deme_two.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ControlFragment extends android.app.Fragment {
    @BindView(R.id.microphone_button)
    ImageButton microphone_button;
    @BindView(R.id.speaker_button)
    ImageButton speaker_button;
    @BindView(R.id.camera_button) ImageButton camera_button;
    @BindView(R.id.disconnect_button)
    ImageButton disconnect_button;
    @BindView(R.id.camera_switch_button)
    ImageButton camera_switch_button;
    public View mControlView;
    public Unbinder unbinder;
    public OnCallEvents mCallEvents;

    public interface OnCallEvents {
        void onCallHangUp();

        void onCameraSwitch();

        boolean onToggleMic();

        boolean onToggleVideo();

        boolean onToggleSpeaker();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mControlView = inflater.inflate(R.layout.fragment_container, container, false);
        unbinder = ButterKnife.bind(this, mControlView);
//        ImageButton disconnect_button=mControlView.findViewById(R.id.disconnect_button);
//        ImageButton camera_switch_button=mControlView.findViewById(R.id.camera_switch_button);
//        ImageButton speaker_button=mControlView.findViewById(R.id.speaker_button);
//        ImageButton microphone_button=mControlView.findViewById(R.id.microphone_button);
//        ImageButton camera_button=mControlView.findViewById(R.id.camera_button);
        disconnect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallEvents.onCallHangUp();
            }
        });
        camera_switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallEvents.onCameraSwitch();
            }
        });
        speaker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = mCallEvents.onToggleMic();
                speaker_button.setImageResource(enabled ? R.mipmap.microphone : R.mipmap.microphone_disable);
            }
        });

        microphone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = mCallEvents.onToggleSpeaker();
                microphone_button.setImageResource(enabled ? R.mipmap.loudspeaker : R.mipmap.loudspeaker_disable);
            }
        });
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = mCallEvents.onToggleVideo();
                camera_button.setImageResource(enabled ? R.mipmap.video_open : R.mipmap.video_close);
            }
        });

        return mControlView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallEvents= (OnCallEvents) context;
    }
}
