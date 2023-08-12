package ru.mirea.viktorov.ui.record;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentRecordBinding;


public class RecordFragment extends Fragment {

    private FragmentRecordBinding binding;

    private ActivityResultLauncher<String[]> activityResultLauncher;
    private String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };
    private boolean isWork;
    private boolean isRecording = false;
    private boolean isPlaying = false;
    private static String TAG = "record";
    private MediaRecorder mediaRecorder;
    private MediaPlayer player = new MediaPlayer();
    private File audioFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecordBinding.inflate(inflater, container, false);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.e("RECORD", "Result: " + result.toString());
                isWork = true;
                for(Boolean b : result.values()) {
                    isWork = isWork && b;
                }
            }
        });
        activityResultLauncher.launch(PERMISSIONS);

        mediaRecorder = new MediaRecorder();

        binding.fabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    processAudioFile();
                    binding.descriptRecord.setText("Запись не производится");
                    binding.fabRecord.setImageResource(R.drawable.ic_menu_record);
                } else {
                    if (isWork) {
                        try {
                            startRecording();
                            binding.descriptRecord.setText("Запись идет");
                            binding.fabRecord.setImageResource(R.drawable.record_off);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        activityResultLauncher.launch(PERMISSIONS);
                        isRecording = !isRecording;
                    }
                }
                isRecording = !isRecording;
            }
        });

        binding.playingRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    Log.d(TAG, "stopPlaying();");
                    stopPlaying();
                    binding.playingRecord.setText("Произвести запись");
                } else {
                    if (audioFile != null) {
                        Log.d(TAG, "startPlaying();");
                        startPlaying();
                        binding.playingRecord.setText("Остановить запись");
                    } else {
                        Log.d(TAG, "Файла не существует");
                        isPlaying = !isPlaying;
                        Toast.makeText(requireActivity(), "Файла не существует", Toast.LENGTH_SHORT).show();
                    }
                }
                isPlaying = !isPlaying;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static boolean getPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            audioFile = new File(requireActivity().getExternalFilesDir(
                    Environment.DIRECTORY_MUSIC), "mirea.3gp");

            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(requireActivity(), "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(requireActivity(), "You are not recording right now!",
                    Toast.LENGTH_SHORT).show();
        }
        mediaRecorder = null;
        mediaRecorder = new MediaRecorder();
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.getAbsolutePath());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);

        requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    private void startPlaying() {
        player = null;
        player = new MediaPlayer();
        try {
            player.setDataSource(audioFile.getAbsolutePath());
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        binding.playingRecord.setText("Послушать запись");
        isPlaying = true;
    }
}