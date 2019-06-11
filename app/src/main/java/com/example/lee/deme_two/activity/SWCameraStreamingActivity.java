package com.example.lee.deme_two.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lee.deme_two.CameraPreviewFrameView;

import com.example.lee.deme_two.R;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;

import java.net.URISyntaxException;
import java.util.List;


public class SWCameraStreamingActivity extends Activity implements StreamingStateChangedListener,StreamingSessionListener,StreamStatusCallback {
    private String TAG="SWCameraStreamingActivity";
    private Button fb,morri;
    private MediaStreamingManager mMediaStreamingManager;
    private ImageButton port,front,reback;
    private String mStatusMsgContent;
    private boolean isface=true;//美颜开关
    private boolean ism=false;//镜像开关
    private boolean mIsEncOrientationPort=true;//横竖屏开关
    protected  StreamingProfile mProfile=new StreamingProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        //屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera_streaming);
         fb= (Button) findViewById(R.id.fb);
        reback= (ImageButton) findViewById(R.id.reback);
         morri= (Button) findViewById(R.id.morri);
         port= (ImageButton) findViewById(R.id.port);
         front= (ImageButton) findViewById(R.id.front);
        CameraPreviewFrameView cameraPreviewFrameView= (CameraPreviewFrameView) findViewById(R.id.cameraPreview_surfaceView);


       String url="rtmp://pili-publish.live.dsppa.com/dsppa/dsppa";
        try {

            mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH3)//设置视频质量。
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM1)//设置音频质量。
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_720)//获取当前编码大小级别。
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)//设置速率控制模式。
                    .setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(2))
                    .setPublishUrl(url);
            Log.d("videoPath", "videoPath:"+url);

            CameraStreamingSetting setting=new CameraStreamingSetting();
            //设置请求相机的id
            setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                    //启用/禁用连续自动对焦（CAF）模式。
                    .setContinuousFocusModeEnabled(true)
                    //设置预览大小的水平
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    //设置预览大小比例
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9)
                    //美颜
                    .setBuiltInFaceBeautyEnabled(isface)
                    //美颜效果
                    .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f,1.0f,1.0f));

            //设置滤镜
            if (isface) {
                setting.setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);
            } else {
                setting.setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_NONE);
            }

            mMediaStreamingManager=new MediaStreamingManager(this,cameraPreviewFrameView,AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);
            mMediaStreamingManager.prepare(setting,mProfile);

            mMediaStreamingManager.setStreamingStateListener(this);
            mMediaStreamingManager.setStreamStatusCallback(this);
            mMediaStreamingManager.setStreamingSessionListener(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final Button bg_st= (Button) findViewById(R.id.bg_st);
        bg_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bg_st.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
    }

    private boolean startStreaming(){
        return mMediaStreamingManager.startStreaming();
    }

   public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.morri:
                if (ism==false){
                    mMediaStreamingManager.setPreviewMirror(true);
                    mMediaStreamingManager.setEncodingMirror(true);
                    ism=true;
                }else if (ism==true){
                    mMediaStreamingManager.setPreviewMirror(false);
                    mMediaStreamingManager.setEncodingMirror(false);
                    ism=false;
                }
                break;
            case R.id.fb:
                     isface=!isface;
                mMediaStreamingManager.setVideoFilterType(isface ?
                        CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY
                        : CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_NONE);

                Toast.makeText(this,"美颜"+isface,Toast.LENGTH_SHORT).show();
                break;
            case R.id.front:
            mMediaStreamingManager.switchCamera();
                break;
            case R.id.port:
                mIsEncOrientationPort = !mIsEncOrientationPort;
                //编码方向
               mProfile.setEncodingOrientation(mIsEncOrientationPort ? StreamingProfile.ENCODING_ORIENTATION.PORT : StreamingProfile.ENCODING_ORIENTATION.LAND);
               mMediaStreamingManager.setStreamingProfile(mProfile);
                // 手机屏幕旋转
                setRequestedOrientation(mIsEncOrientationPort ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mMediaStreamingManager.notifyActivityOrientationChanged();
                break;
            case R.id.reback:
                finish();
                break;
        }
   }
    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        Log.e(TAG, "StreamingState streamingState:" + streamingState + ",extra:" + extra);
        switch (streamingState) {
            case PREPARING:
                Log.e(TAG, "onStateChanged: " + "准备");
                break;
            case READY:
                // start streaming when READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            mMediaStreamingManager.startStreaming();
                            boolean b = startStreaming();
                            Log.e(TAG, "run: " + "推流" + b);
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                Log.e(TAG, "onStateChanged: " + "已连接");
                break;
            case STREAMING:

                Log.e(TAG, "onStateChanged: " + "已发送");
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                Log.e(TAG, "onStateChanged: " + "推流完成");
                // The streaming had been finished.
                break;
            case IOERROR:
                Log.e(TAG, "onStateChanged: " + "IO错误");
                // Network connect error.
                break;
            case SENDING_BUFFER_EMPTY:
                Log.e(TAG, "onStateChanged: " + "缓冲区数据为空");
                break;
            case SENDING_BUFFER_FULL:
                Log.e(TAG, "onStateChanged: " + "缓冲区数据存满");
                break;
            case AUDIO_RECORDING_FAIL:
                Log.e(TAG, "onStateChanged: " + "录音失败");
                // Failed to record audio.
                break;
            case OPEN_CAMERA_FAIL:
                Log.e(TAG, "onStateChanged: " + "打开相机失败");
                // Failed to open camera.
                break;
            case DISCONNECTED:
                Log.e(TAG, "onStateChanged: " + "断开连接");
                // The socket is broken while streaming
        }
    }


    @Override
    public void notifyStreamStatusChanged(StreamingProfile.StreamStatus streamStatus) {

    }

    @Override
    public boolean onRecordAudioFailedHandled(int i) {
        mMediaStreamingManager.updateEncodingType(AVCodecType.SW_VIDEO_CODEC);
        mMediaStreamingManager.startStreaming();
        return true;
    }

    @Override
    public boolean onRestartStreamingHandled(int i) {
        return mMediaStreamingManager.startStreaming();
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        if (list!=null){
            for (Camera.Size s:list){
                Log.i(TAG,"W"+s.width+",h:"+s.height);
            }
        }
        return null;
    }

    @Override
    public int onPreviewFpsSelected(List<int[]> list) {
        return 0;
    }
}