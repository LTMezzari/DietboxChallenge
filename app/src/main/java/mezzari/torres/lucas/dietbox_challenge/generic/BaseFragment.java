package mezzari.torres.lucas.dietbox_challenge.generic;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public abstract class BaseFragment extends Fragment {

    private boolean hasToolbarUpdate = true;
    private boolean shouldShowActionBar = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasToolbarUpdate);
    }

    @Override
    @CallSuper
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        setActionBar();
        setActionBarVisibility(shouldShowActionBar);
    }

    @Override
    @CallSuper
    public void onDestroyOptionsMenu() {
        resetActionBar();
        setActionBarVisibility(true);
    }

    @NotNull
    protected final NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }

    protected final void setHasToolbarUpdate(boolean hasToolbarUpdate) {
        this.hasToolbarUpdate = hasToolbarUpdate;
    }

    protected final void setShouldShowActionBar(boolean shouldShowActionBar) {
        this.shouldShowActionBar = shouldShowActionBar;
    }

    private void setActionBar() {
        DynamicActionBarActivity activity = getDynamicActionBarActivity();
        if (activity == null) {
            return;
        }
        Toolbar actionBar = getActionBar();
        if (actionBar != null) {
            activity.updateActionBar(actionBar);
        }
    }

    private void resetActionBar() {
        DynamicActionBarActivity activity = getDynamicActionBarActivity();
        if (activity == null) {
            return;
        }
        activity.resetActionBar();
    }

    private void setActionBarVisibility(boolean shouldShowActionBar) {
        if (!(getActivity() instanceof AppCompatActivity)) {
            return;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        if (shouldShowActionBar) {
            actionBar.show();
            return;
        }
        actionBar.hide();
    }

    @Nullable
    private DynamicActionBarActivity getDynamicActionBarActivity() {
        if (getActivity() == null || !(getActivity() instanceof DynamicActionBarActivity)) {
            return null;
        }
        return (DynamicActionBarActivity) getActivity();
    }

    @Nullable
    protected Toolbar getActionBar() {
        return null;
    }
}
