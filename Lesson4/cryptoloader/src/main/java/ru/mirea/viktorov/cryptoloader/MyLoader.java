package ru.mirea.viktorov.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    public static final String ARG_WORD = "word";
    private byte[] cryptText;
    private  byte[] key;
    private SecretKey originalKey;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        cryptText = args.getByteArray(ARG_WORD);
        key = args.getByteArray("key");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        // Восстановление ключав
        originalKey = new SecretKeySpec(key, 0, key.length, "AES");
        String message = decryptMsg(cryptText, originalKey);
        return message;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        /* Decrypt the message */
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
