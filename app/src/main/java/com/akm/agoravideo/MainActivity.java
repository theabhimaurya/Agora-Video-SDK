package com.akm.agoravideo;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.util.Log;

import io.agora.rtc2.Constants;
import io.agora.agorauikit_android.*;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    // Object of AgoraVideoVIewer class
    private AgoraVideoViewer agView = null;

    // Fill the App ID of your project generated on Agora Console.
    private String appId = "84159835e25f46fa8f927ac562a0fa61";

    // Fill the channel name.
    private String channelName = "test";

    // Fill the temp token generated on Agora Console.
    private String token = "007eJxTYLCNX5opKed97OnzroT76txxmqsjNTqX21m53OqZVZvyqUSBwcLE0NTSwtg01cg0zcQsLdEizdLIPDHZ1Mwo0SAt0czQUz0guSGQkWFO1T1GRgYIBPFZGEpSi0sYGADLBx3S";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAndJoinChannel();
    }

    private void initializeAndJoinChannel() {
        // Create AgoraVideoViewer instance
        try {
            agView = new AgoraVideoViewer(this, new AgoraConnectionData(appId, token), AgoraVideoViewer.Style.FLOATING, new AgoraSettings(), null);
        } catch (Exception e) {
            Log.e("AgoraVideoViewer",
                    "Could not initialize AgoraVideoViewer. Check that your app Id is valid.");
            Log.e("Exception", e.toString());
            return;
        }
        // Add the AgoraVideoViewer to the Activity layout
        this.addContentView(agView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        );
        // Check permission and join a channel
        if (DevicePermissionsKt.requestPermissions(AgoraVideoViewer.Companion, this)) {
            joinChannel();
        } else {
            Button joinButton = new Button(this);
            joinButton.setText("Allow camera and microphone access, then click here");
            joinButton.setOnClickListener(new View.OnClickListener() {
                // When the button is clicked, check permissions again and join channel
                @Override
                public void onClick(View view) {
                    if (DevicePermissionsKt.requestPermissions(AgoraVideoViewer.Companion, getApplicationContext())) {
                        ((ViewGroup) joinButton.getParent()).removeView(joinButton);
                        joinChannel();
                    }
                }
            });
            this.addContentView(joinButton, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200));
        }
    }
    void joinChannel(){
        agView.join(channelName, token, Constants.CLIENT_ROLE_BROADCASTER, 0);
    }
}