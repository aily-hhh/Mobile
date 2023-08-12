package ru.mirea.viktorov.ui.files;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentFilesBinding;

public class FilesFragment extends Fragment {

    private FragmentFilesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilesBinding.inflate(inflater, container, false);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(
                        requireActivity(),
                        R.id.nav_host_fragment_content_main
                ).navigate(R.id.action_nav_files_to_newFileFragment);
            }
        });

        binding.btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.btnOpenFile.setClickable(isOpenFile());
    }

    private void openPdf() {
        File file = new File("/PDF/mireaFile.pdf");
        if (isOpenFile()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireActivity(), "No Application for pdf view", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isOpenFile() {
        File file = new File("/PDF/mireaFile.pdf");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}