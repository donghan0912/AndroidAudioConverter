package cafe.adriel.androidaudioconverter.sample;

import android.app.Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        copyFile();
        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
                error.printStackTrace();
            }
        });
    }

    private void copyFile() {
        File file = new File(getFilesDir(), "audio_demo.mp3");
        if (file.exists()) {
            return;
        }
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("audio_demo.mp3");
            OutputStream outputStream = Files.newOutputStream(file.toPath());

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            // 复制完成
        } catch (IOException e) {
            e.printStackTrace();
            // 复制过程中出现异常
        }

    }
}