package com.ngangavic.truecallerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

public class MainActivity extends AppCompatActivity {


    private final ITrueCallback sdkCallback = new ITrueCallback() {
        @Override
        public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
            Log.d("MAIN ACTIVITY", "Verified successfully: " + trueProfile.firstName);
        }

        @Override
        public void onFailureProfileShared(@NonNull TrueError trueError) {
            Log.e("MAIN ACTIVITY", String.valueOf(trueError.getErrorType()));
        }

        @Override
        public void onVerificationRequired() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonReg = findViewById(R.id.buttonReg);

        TrueSdkScope trueSdkScope = new TrueSdkScope.Builder(this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN)
                .consentTitleOption(TrueSdkScope.SDK_CONSENT_TITLE_VERIFY)
                .footerType(TrueSdkScope.FOOTER_TYPE_SKIP)
                .build();
        TrueSDK.init(trueSdkScope);
        TrueSDK.getInstance().isUsable();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrueSDK.getInstance().getUserProfile(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrueSDK.getInstance().onActivityResultObtained(this, resultCode, data);
    }
}
