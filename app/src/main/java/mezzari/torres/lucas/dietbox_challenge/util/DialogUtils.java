package mezzari.torres.lucas.dietbox_challenge.util;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.StringRes;

import mezzari.torres.lucas.dietbox_challenge.R;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class DialogUtils {
    public static void createAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.action_ok, null);
        builder.show();
    }

    public static void createAlertDialog(Context context, @StringRes int title, @StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.action_ok, null);
        builder.show();
    }
}
