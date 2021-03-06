package com.example.android.exoplayerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.lang.reflect.Array;
import java.net.URI;

public class MainActivity extends AppCompatActivity {


  // creating a variable for exoplayerview.
            SimpleExoPlayerView exoPlayerView;
            ImageButton forwrd_btn;
            int pos = 0;


    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;

    // url of video which we are loading.

    static final String[] items = {
            "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4",
            "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
//            "https://jiostreamingdash.akamaized.net/content/entry/wvdata/24/67/d889ff00236a11ebb25a6fbdddcb3e99_voot_mobile_premium_1626940058375.mpd",
//            "https://prod.media.jio.com/wvproxy?videoid=2000358752&vootid=998096&iat=1635422513&voottoken=BToken_178b5cda9533e08b3e9414e6c7fdba21f1a77c5c767593fff5634bcb13c95a5109c6ae9432bba62f6256dc14fa37d70057b9756095ec30507659edaacd4efff0"

    };
//    String videoURL = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4";
   String videoURL1 = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
//    String videoUrl = "https://jiostreamingdash.akamaized.net/content/entry/wvdata/24/67/d889ff00236a11ebb25a6fbdddcb3e99_voot_mobile_premium_1626940058375.mpd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exoPlayerView = findViewById(R.id.idExoPlayerVIew);

        // forward btn
        forwrd_btn = findViewById(R.id.exo_ffwd);

        forwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (pos == 0){
                   pos =1;
               }else{
                   pos =0;
                   exoPlayer.stop();
                }
               startPlayer(items[pos]);
//               exoPlayer.stop();
            }
        });

        //hide action bar
        getSupportActionBar().hide();
        startPlayer(items[pos]);
//        exoPlayer.stop();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (exoPlayer == null) {
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        exoPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    public void startPlayer(String videoUrl){
        try {

            // bandwisthmeter is used for
            // getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            // track selector is used to navigate between
            // video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
            Uri videouri = Uri.parse(String.valueOf(videoUrl));

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            exoPlayerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.setPlayWhenReady(true);

        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }
}