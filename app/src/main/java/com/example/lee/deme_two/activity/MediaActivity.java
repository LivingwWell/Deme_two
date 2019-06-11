package com.example.lee.deme_two.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lee.deme_two.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;


public class MediaActivity extends Activity {
    private String TAG = "MediaActivity";
    private PLVideoTextureView mVideoView;
    private TextView mStatInfoTextView;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT;
    private int mRotation = 0;
    String videoPath;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_media);

        videoPath =getIntent().getStringExtra("videoPath") ;
        mVideoView = findViewById(R.id.VideoView);
        View loadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(loadingView);
        View coverView = findViewById(R.id.CoverView);
        mVideoView.setCoverView(coverView);
        mStatInfoTextView = findViewById(R.id.StatInfoTextView);
        AVOptions options = new AVOptions();
        // 打开视频时单次 http 请求的超时时间，一次打开过程最多尝试五次
        // 单位为 ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 5 * 1000);
        // 是否开启直播优化，1 为开启，0 为关闭。若开启，视频暂停后再次开始播放时会触发追帧机制
        // 默认为 0
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        // 默认值是：MEDIA_CODEC_SW_DECODE
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
        // 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
        options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
        // 预设置 SDK 的 log 等级， 0-4 分别为 v/d/i/w/e
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 4);
        // 设置开始播放位置
        // 默认不开启，单位为 ms
        options.setInteger(AVOptions.KEY_START_POSITION, 0 * 1000);
        mVideoView.setAVOptions(options);

        mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setOnInfoListener(mOnInfoListener);//注册信息/警告可用时要调用的回调。
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);//注册要在视频大小已知或更新时调用的回调。
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);//当网络流缓冲区的状态发生变化时，注册要调用的回调。
        mVideoView.setOnCompletionListener(mOnCompletionListener);//注册在回放期间到达媒体源末尾时要调用的回调。
        mVideoView.setOnErrorListener(mOnErrorListener);//注册在异步操作期间发生错误时要调用的回调。


        mVideoView.setVideoPath(videoPath);
        Log.d(TAG, "videoPath:"+videoPath);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    public void onClickRotate(View v) {
        mRotation = (mRotation + 90) % 360;
        mVideoView.setDisplayOrientation(mRotation);
    }

    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoTextureView.ASPECT_RATIO_ORIGIN:
                Toast.makeText(MediaActivity.this, "Origin mode", Toast.LENGTH_SHORT).show();
                break;
            case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
                Toast.makeText(MediaActivity.this, "Fit parent !", Toast.LENGTH_SHORT).show();

                break;
            case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
                Toast.makeText(MediaActivity.this, "Paved parent !", Toast.LENGTH_SHORT).show();

                break;
            case PLVideoTextureView.ASPECT_RATIO_16_9:
                Toast.makeText(MediaActivity.this, "16 : 9 !", Toast.LENGTH_SHORT).show();

                break;
            case PLVideoTextureView.ASPECT_RATIO_4_3:
                Toast.makeText(MediaActivity.this, "4 : 3 !", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }
    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    Toast.makeText(MediaActivity.this, "First video render time: " + extra + "ms", Toast.LENGTH_SHORT).show();
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.i(TAG, "First audio render time: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    updateStatInfo();
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                default:
                    break;
            }
        }
    };


    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    showToastTips(MediaActivity.this, "IO Error !");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    showToastTips(MediaActivity.this, "failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    showToastTips(MediaActivity.this, "failed to seek !");
                    return true;
                default:
                    showToastTips(MediaActivity.this, "unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };
    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
            showToastTips(MediaActivity.this, "Play Completed !");
            finish();
        }
    };
    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };
    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private void updateStatInfo() {
        long bitrate = mVideoView.getVideoBitrate() / 1024;
        final String stat = bitrate + "kbps, " + mVideoView.getVideoFps() + "fps";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatInfoTextView.setText(stat);
            }
        });
    }

    public static void showToastTips(final Context context, final String tips) {
        Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }
}
