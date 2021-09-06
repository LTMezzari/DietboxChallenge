package mezzari.torres.lucas.dietbox_challenge.scenes;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import mezzari.torres.lucas.dietbox_challenge.databinding.ActivityMainBinding;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseActivity;
import mezzari.torres.lucas.dietbox_challenge.generic.DynamicActionBarActivity;

public class MainActivity extends BaseActivity implements DynamicActionBarActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDefaultActionBar();
    }

    private void setDefaultActionBar() {
        setSupportActionBar(binding.activityToolbar);
    }

    @Override
    public void updateActionBar(@NotNull Toolbar toolbar) {
        changeToolbarVisibility(false);
        setSupportActionBar(toolbar);
    }

    @Override
    public void resetActionBar() {
        setDefaultActionBar();
        changeToolbarVisibility(true);
    }
}