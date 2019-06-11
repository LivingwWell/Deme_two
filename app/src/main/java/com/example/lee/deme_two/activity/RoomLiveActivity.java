package com.example.lee.deme_two.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.fragment.ControlFragment;
import com.qiniu.droid.rtc.QNCameraSwitchResultCallback;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEngineEventListener;
import com.qiniu.droid.rtc.QNRTCSetting;
import com.qiniu.droid.rtc.QNRoomState;
import com.qiniu.droid.rtc.QNSourceType;
import com.qiniu.droid.rtc.QNStatisticsReport;
import com.qiniu.droid.rtc.QNSurfaceView;
import com.qiniu.droid.rtc.QNTrackInfo;
import com.qiniu.droid.rtc.QNTrackKind;
import com.qiniu.droid.rtc.QNVideoFormat;
import com.qiniu.droid.rtc.model.QNAudioDevice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;


public class RoomLiveActivity extends Activity implements QNRTCEngineEventListener, ControlFragment.OnCallEvents {
    String TAG = "RoomLiveActivity";
    private QNSurfaceView mLocalWindow;
    private QNSurfaceView mRemoteWindow;
    private QNRTCEngine mEngine;
    private QNTrackInfo mLocalVideoTrack;
    private QNTrackInfo mLocalAudioTrack;
    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_ROOM_TOKEN = "ROOM_TOKEN";
    public static final String EXTRA_ROOM_ID = "ROOM_ID";
    private ControlFragment mControlFragment;
    private Boolean mVideoEnabled = true;
    private Boolean mSpeakerEnabled = true;
    private Boolean mMicEnabled = true;
    private String mRoomToken;
    private String mUserId;
    private String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_liveroom);

        mLocalWindow = findViewById(R.id.local_surface_view);
        mRemoteWindow = findViewById(R.id.remote_surface_view);

        mEngine = QNRTCEngine.createEngine(getApplicationContext());
        mEngine.setEventListener(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mRoomId = intent.getStringExtra(EXTRA_ROOM_ID);
        mUserId = intent.getStringExtra(EXTRA_USER_ID);
        mRoomToken = intent.getStringExtra(EXTRA_ROOM_TOKEN);

        mControlFragment = new ControlFragment();
        mControlFragment.setArguments(intent.getExtras());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, mControlFragment);
        ft.commitAllowingStateLoss();

        QNVideoFormat format = new QNVideoFormat(640, 360, 30);
        QNRTCSetting setting = new QNRTCSetting();
        setting.setCameraID(QNRTCSetting.CAMERA_FACING_ID.BACK)
                /**
                 * 开启/关闭硬编码，默认为开
                 * @param enabled  是否开启硬编
                 */
                .setHWCodecEnabled(true)
                /**
                 * 设置是否固定分辨率
                 * @param maintainResolution 是否开启固定分辨率
                 */
                .setMaintainResolution(true)

                .setVideoBitrate(400 * 1000)
                /**
                 * 设置实时音视频互动的编码分辨率、帧率等参数。默认值分辨率和帧率取 DEFAULT_WIDTH(640)、DEFAULT_HEIGHT(480) 和 DEFAULT_FPS(20)
                 *
                 * @param videoFormat 目标视频配置，QNVideoFormat 需指定采集画面的宽、高以及帧率
                 */
                .setVideoEncodeFormat(format)
                /**
                 * 设置实时音视频互动的预览分辨率、帧率等参数。默认值分辨率和帧率取 DEFAULT_WIDTH(640)、DEFAULT_HEIGHT(480) 和 DEFAULT_FPS(20)
                 *
                 *@param videoFormat 目标视频配置，QNVideoFormat 需指定采集画面的宽、高以及帧率
                 */
                .setVideoPreviewFormat(format);

        mLocalVideoTrack = mEngine.createTrackInfoBuilder()
                .setVideoEncodeFormat(format)
                .setSourceType(QNSourceType.VIDEO_CAMERA)
                .setBitrate(600 * 1000)
                .setMaster(true)
                .create();

        mLocalAudioTrack = mEngine.createTrackInfoBuilder()
                .setSourceType(QNSourceType.AUDIO)
                .setBitrate(64 * 1000)
                .setMaster(true)
                .create();

        mEngine.setRenderWindow(mLocalVideoTrack, mLocalWindow);
    }

    @Override
    //发布本地 Tracks
    public void onRoomStateChanged(QNRoomState qnRoomState) {
        switch (qnRoomState) {
            case RECONNECTING:
                Log.d(TAG, "正在重连");
                break;
            case RECONNECTED:
                Log.d(TAG, "重连成功");
                break;
            case CONNECTED:
                Log.d(TAG, "连接成功");
                mEngine.publishTracks(Arrays.asList(mLocalVideoTrack, mLocalAudioTrack));
                break;
            case CONNECTING:
                Log.d(TAG, "正在连接");
                break;
        }
    }

    /**
     * 当远端用户加入房间时会触发此回调
     */
    public void onRemoteUserJoined(String s, String s1) {

    }

    /**
     * 当远端用户离开房间时会触发此回调
     */
    public void onRemoteUserLeft(String s) {

    }

    /**
     * 当本地 Track 发布时会触发此回调
     */
    public void onLocalPublished(List<QNTrackInfo> list) {

    }

    /**
     * 当远端 Track 发布时会触发此回调
     */
    public void onRemotePublished(String s, List<QNTrackInfo> list) {
       // mRemoteWindow.setVisibility(View.VISIBLE);
       // mLocalWindow.bringToFront();
        mRemoteWindow.setBackgroundColor(Color.alpha(00));
    }

    /**
     * 当远端 Track 取消发布时会触发此回调
     */
    public void onRemoteUnpublished(String s, List<QNTrackInfo> list) {
       // mRemoteWindow.setVisibility(View.INVISIBLE);
       mRemoteWindow.setBackgroundColor(Color.BLACK);
    }

    /**
     * 当远端用户 mute Track 时会触发此回调
     */
    public void onRemoteUserMuted(String s, List<QNTrackInfo> list) {

    }

    /**
     * 当订阅 Track 成功时会触发此回调
     */
    public void onSubscribed(String s, List<QNTrackInfo> list) {
        for (QNTrackInfo track : list) {
            if (track.getTrackKind().equals(QNTrackKind.VIDEO)) {
                mEngine.setRenderWindow(track, mRemoteWindow);
            }
        }
    }

    //被踢
    @Override
    public void onKickedOut(String s) {
        ToastUtils.showShortToast(this, "您被管理员提出房间！");
        finish();
    }

    /**
     * 当统计数据返回时会触发此回调。需要开启数据统计功能。
     */
    public void onStatisticsUpdated(QNStatisticsReport qnStatisticsReport) {

    }

    /**
     * 当本地音频播放设备改变时会触发此回调
     */
    public void onAudioRouteChanged(QNAudioDevice qnAudioDevice) {

    }


    /**
     * 当创建合流任务成功时会触发此回调
     *
     * @param
     */
    public void onCreateMergeJobSuccess(String s) {

    }

    /**
     * 当错误发生时触发此回调
     */
    public void onError(int i, String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEngine.destroy();
    }

    //加入房间
    @Override
    protected void onResume() {
        super.onResume();
        mEngine.joinRoom(mRoomToken);
        Log.d(TAG, "mRoomToken=" + mRoomToken);
    }


    //挂断
    @Override
    public void onCallHangUp() {
        if (mEngine != null) {
            mEngine.leaveRoom();
        }
        finish();
    }

    //切换摄像头
    @Override
    public void onCameraSwitch() {
        if (mEngine != null) {
            mEngine.switchCamera(new QNCameraSwitchResultCallback() {
                @Override
                public void onCameraSwitchDone(boolean isFrontCamera) {
                }

                @Override
                public void onCameraSwitchError(String errorMessage) {
                }
            });
        }
    }

    //关闭麦克风
    public boolean onToggleMic() {
        if (mEngine != null && mLocalAudioTrack != null) {
            mMicEnabled = !mMicEnabled;
            mLocalAudioTrack.setMuted(!mMicEnabled);
            mEngine.muteTracks(Collections.singletonList(mLocalAudioTrack));

        }
        return mMicEnabled;
    }

    //关闭摄像头
    @Override
    public boolean onToggleVideo() {
        if (mEngine != null && mLocalVideoTrack != null) {
            mVideoEnabled = !mVideoEnabled;
            mLocalVideoTrack.setMuted(!mVideoEnabled);
            mEngine.muteLocalVideo(!mVideoEnabled);
            mEngine.setPreviewEnabled(!mVideoEnabled);
        }
        return mVideoEnabled;
    }

    //静音
    @Override
    public boolean onToggleSpeaker() {
        if (mEngine != null) {
            mSpeakerEnabled = !mSpeakerEnabled;
            mEngine.muteRemoteAudio(!mSpeakerEnabled);
        }
        return mSpeakerEnabled;
    }
}
