package mezzari.torres.lucas.dietbox_challenge.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class BindingUtils {
    public static void bindTextView(LifecycleOwner owner, MutableLiveData<String> liveData, TextView textView) {
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
}
