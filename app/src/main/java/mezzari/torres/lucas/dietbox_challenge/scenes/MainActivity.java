package mezzari.torres.lucas.dietbox_challenge.scenes;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.net.Network;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.databinding.ActivityMainBinding;
import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseActivity;
import mezzari.torres.lucas.dietbox_challenge.generic.DynamicActionBarActivity;
import mezzari.torres.lucas.dietbox_challenge.network.state.INetworkState;
import mezzari.torres.lucas.dietbox_challenge.util.ConnectionUtils;

public final class MainActivity extends BaseActivity implements DynamicActionBarActivity {

    @Inject
    public INetworkState networkState;

    private ActivityMainBinding binding;

    public MainActivity() {
        DaggerHelper.getInstance().getMainComponent().inject(this);
    }

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

    private void setupNavigation() {
        NavigationUI.setupActionBarWithNavController(this, getNavController());
    }

    private NavController getNavController() {
        return Navigation.findNavController(this, R.id.fcvNavHost);
    }

    @Override
    public void updateActionBar(@NotNull Toolbar toolbar) {
        changeToolbarVisibility(false);
        setSupportActionBar(toolbar);
        setupNavigation();
    }

    @Override
    public void resetActionBar() {
        setDefaultActionBar();
        setupNavigation();
        changeToolbarVisibility(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(ConnectionUtils.isInternetConnected(this) ? R.string.title_online : R.string.title_offline);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return getNavController().navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onConnectionAvailable(@NotNull Network network) {
        super.onConnectionAvailable(network);
        setTitle(R.string.title_online);
        networkState.setNetworkActive(true);
    }

    @Override
    protected void onConnectionUnavailable(@Nullable Network network) {
        super.onConnectionAvailable(network);
        setTitle(R.string.title_offline);
        networkState.setNetworkActive(false);
    }
}