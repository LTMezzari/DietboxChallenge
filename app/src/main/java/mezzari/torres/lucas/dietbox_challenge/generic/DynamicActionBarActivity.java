package mezzari.torres.lucas.dietbox_challenge.generic;

import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public interface DynamicActionBarActivity {
    void updateActionBar(@NotNull Toolbar toolbar);

    void resetActionBar();
}
