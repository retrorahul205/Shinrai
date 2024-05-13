package com.example.shinrai;
import android.content.Context;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricManager {

    private static final int BIOMETRIC_SUCCESS = 1;
    private final Context context;
    private final Executor executor;
    private final BiometricPrompt biometricPrompt;
    private final BiometricPrompt.PromptInfo promptInfo;

    public BiometricManager(Context context, Executor executor) {
        this.context = context;
        this.executor = executor;

        // Create biometric prompt
        biometricPrompt = new BiometricPrompt((FragmentActivity) context, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle authentication error
                Toast.makeText(context, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Handle authentication success
                Toast.makeText(context, "Biometric authentication succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Handle authentication failure
                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Create biometric prompt info
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric authentication")
                .setSubtitle("Verify your identity")
                .setNegativeButtonText("Cancel")
                .build();
    }

    public void initialize() {
        // Check if biometric authentication is available
        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(context);
        if (biometricManager.canAuthenticate() != androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(context, "Biometric authentication is not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void authenticate(app_model app) {
        // Trigger biometric authentication
        biometricPrompt.authenticate(promptInfo);
    }
}
