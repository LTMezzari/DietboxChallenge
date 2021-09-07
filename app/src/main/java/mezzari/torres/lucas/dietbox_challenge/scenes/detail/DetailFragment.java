package mezzari.torres.lucas.dietbox_challenge.scenes.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.databinding.FragmentDetailBinding;
import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseFragment;
import mezzari.torres.lucas.dietbox_challenge.scenes.home.HomeFragment;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class DetailFragment extends BaseFragment {
    @Inject
    public DetailFragmentViewModel viewModel;

    private FragmentDetailBinding binding;

    public DetailFragment() {
        DaggerHelper.getInstance().getMainComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long movieId = getArguments().getLong("movieId", -1);
        viewModel.getMovieDetails(movieId);

        viewModel.getMovie().observe(getViewLifecycleOwner(), (value) -> {
            if (value == null) return;
            binding.tvMovieTitle.setText(value.getTitle());
            binding.tvSynopsis.setText(value.getOverview());
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(),
                (value) -> binding.rlLoader.setVisibility(value != null && value ? View.VISIBLE : View.GONE));

        viewModel.getError().observe(getViewLifecycleOwner(), (error) -> {
            if (error == null || error.trim().isEmpty()) return;
            if (BuildConfig.DEBUG) {
                Log.e(HomeFragment.class.getName(), error);
            }
            showError(R.string.message_fetch_movie_detail_failed);
        });
    }
}
