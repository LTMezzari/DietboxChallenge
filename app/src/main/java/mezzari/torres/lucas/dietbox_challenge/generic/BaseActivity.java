package mezzari.torres.lucas.dietbox_challenge.generic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void changeToolbarVisibility(boolean shouldShowActionBar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        if (shouldShowActionBar) {
            actionBar.show();
            return;
        }
        actionBar.hide();
    }
}
