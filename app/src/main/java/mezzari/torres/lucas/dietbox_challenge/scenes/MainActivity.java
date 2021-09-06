package mezzari.torres.lucas.dietbox_challenge.scenes;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.net.Network;
import android.os.Build;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.databinding.ActivityMainBinding;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseActivity;
import mezzari.torres.lucas.dietbox_challenge.generic.DynamicActionBarActivity;
import mezzari.torres.lucas.dietbox_challenge.util.ConnectionUtils;

public final class MainActivity extends BaseActivity implements DynamicActionBarActivity {

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

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(ConnectionUtils.isInternetConnected(this) ? R.string.title_online : R.string.title_offline);
    }

    @Override
    protected void onConnectionAvailable(@NotNull Network network) {
        super.onConnectionAvailable(network);
        setTitle(R.string.title_online);
    }

    @Override
    protected void onConnectionUnavailable(@Nullable Network network) {
        super.onConnectionAvailable(network);
        setTitle(R.string.title_offline);
    }
}