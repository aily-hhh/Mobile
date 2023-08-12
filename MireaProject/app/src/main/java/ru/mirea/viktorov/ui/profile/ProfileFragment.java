package ru.mirea.viktorov.ui.profile;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ImageView imgProfile;
    private AppCompatEditText etFirstName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etCity;
    private Button btnSaveProfile;
    private ActivityResultLauncher<String[]> activityResultLauncher;
    private boolean isWork = false;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        imgProfile = binding.imgProfile;
        etFirstName = binding.etFirstName;
        etLastName = binding.etLastName;
        etCity = binding.etCity;
        btnSaveProfile = binding.btnSaveProfile;

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        etFirstName.setText(sharedPref.getString("firstName", ""));
        etLastName.setText(sharedPref.getString("lastName", ""));
        etCity.setText(sharedPref.getString("city", ""));
        String uri = sharedPref.getString("image", "");
        if (!uri.equals("")) {
            imgProfile.setImageURI(Uri.parse(uri));
        } else {
            imgProfile.setImageResource(R.drawable.ic_face);
        }

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if (etFirstName.getText() != null) {
                    editor.putString("firstName", etFirstName.getText().toString().trim());
                }
                if (etLastName.getText() != null) {
                    editor.putString("lastName", etLastName.getText().toString().trim());
                }
                if (etCity.getText() != null) {
                    editor.putString("city", etCity.getText().toString().trim());
                }
                if (imageUri != null) {
                    editor.putString("image", imageUri.toString());
                }
                editor.apply();
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.e("CAMERA", "Result: " + result.toString());
                isWork = true;
                for(Boolean b : result.values()) {
                    isWork = isWork && b;
                }
            }
        });

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    imgProfile.setImageURI(imageUri);
                }
            }
        };

        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback
        );

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Log.d("CAMERA", "Tap: " + isWork);

                // проверка на наличие разрешений для камеры
                String[] appPerms;
                appPerms = new String[] {
                        Manifest.permission.CAMERA
                };
                activityResultLauncher.launch(appPerms);

                if (isWork) {
                    try {
                        File photoFile = createImageFile();

                        // генерирование пути к файлу на основе authoritiesLog.d("CAMERA", requireActivity().getApplicationContext().getPackageName() + ".fileprovider");
                        String authorities = requireActivity().getApplicationContext().getPackageName() + ".fileprovider";
                        imageUri = FileProvider.getUriForFile(requireActivity(), authorities, photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        cameraActivityResultLauncher.launch(cameraIntent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private File createImageFile() throws IOException {
        String imageFileName = "IMAGE_PROFILE";
        File storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

}