package ru.mirea.viktorov.ui.files;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentNewFileBinding;
import ru.mirea.viktorov.databinding.FragmentProfileBinding;


public class NewFileFragment extends Fragment {

    private FragmentNewFileBinding binding;
    private boolean isWork = false;
    private final ActivityResultLauncher<String> resultLauncher
            = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    isWork = true;
                    Log.d("FILES", "Permissions true");
                } else {
                    isWork = false;
                    Log.d("FILES", "Permissions false");
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewFileBinding.inflate(inflater, container, false);

        binding.btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWork) {
                    String title = binding.txtTitleFile.getText().toString();
                    String description = binding.txtDescriptionFile.getText().toString();

                    createPdfFile(title, description);

                    Navigation.findNavController(
                            requireActivity(),
                            R.id.nav_host_fragment_content_main
                    ).popBackStack();
                } else {
                    Log.d("FILES", "Permissions request");
                    resultLauncher.launch(
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    );
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("FILES", "onStart");
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            resultLauncher.launch(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            );
        } else {
            isWork = false;
            Log.d("FILES", "Permissions false on Start");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createPdfFile(String title, String description) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float pageHeight = displaymetrics.heightPixels;
        float pageWidth = displaymetrics.widthPixels;

        Paint textPaint = new Paint();

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument
                .PageInfo.Builder((int)pageWidth, (int)pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        textPaint.setTextSize(24);
        textPaint.setColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, 209, 100, textPaint);

        textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.ITALIC));
        textPaint.setTextSize(18);
        textPaint.setColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(description, 396, 560, textPaint);

        pdfDocument.finishPage(myPage);

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String targetPdf = dir.getAbsolutePath() + "/mireaFile.pdf";
        File filePath = new File(targetPdf);
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            Toast.makeText(requireContext(), "PDF file saved in " + filePath.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Что-то пошло не так: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e("FILES", "Create file, " + e.getMessage());
        }

        pdfDocument.close();
    }
}