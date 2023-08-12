package ru.mirea.viktorov.ui.webView;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ru.mirea.viktorov.databinding.FragmentWebViewBinding;

public class WebViewFragment extends Fragment {

    private WebView webPage;
    private FragmentWebViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebViewBinding.inflate(inflater, container, false);

        webPage = binding.webPage;
        webPage.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webPage.loadUrl("https://ya.ru/");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}