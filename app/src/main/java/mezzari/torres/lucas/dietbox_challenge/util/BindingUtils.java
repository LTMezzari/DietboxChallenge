package mezzari.torres.lucas.dietbox_challenge.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class BindingUtils {
    public static void bindRatingBar(LifecycleOwner owner, LiveData<Integer> liveData, RatingBar ratingBar) {
        liveData.observe(owner, (value) -> {
            if (ratingBar.getProgress() != value) {
                ratingBar.setProgress(value);
            }
        });
    }

    public static void bindTextView(LifecycleOwner owner, LiveData<String> liveData, TextView textView) {
        liveData.observe(owner, (value) -> {
            if (!textView.getText().equals(value)) {
                textView.setText(value);
            }
        });
    }

    public static void bindEditText(LifecycleOwner owner, MutableLiveData<String> liveData, EditText editText) {
        liveData.observe(owner, (value) -> {
            if (!editText.getText().toString().equals(value)) {
                editText.setText(value);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals(liveData.getValue())) {
                    liveData.setValue(editable.toString());
                }
            }
        });
    }

    public static void bindSearchView(LifecycleOwner owner, MutableLiveData<String> liveData, SearchView searchView, Runnable runnable) {
        liveData.observe(owner, (value) -> {
            if (!searchView.getQuery().toString().equals(value)) {
                searchView.setQuery(value, false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                runnable.run();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(liveData.getValue())) {
                    liveData.setValue(newText);
                }
                return false;
            }
        });
    }
}
