package mezzari.torres.lucas.dietbox_challenge.scenes.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.databinding.FragmentDetailBinding;
import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseFragment;
import mezzari.torres.lucas.dietbox_challenge.util.BindingUtils;
import mezzari.torres.lucas.dietbox_challenge.util.StringUtils;

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

        BindingUtils.bindTextView(getViewLifecycleOwner(), viewModel.getTitle(), binding.tvMovieTitle);

        viewModel.getOverview().observe(getViewLifecycleOwner(), (overview) -> {
            if (overview == null || overview.trim().isEmpty()) {
                binding.tvSynopsis.setText(R.string.message_overview_unavailable);
                return;
            }
            binding.tvSynopsis.setText(overview);
        });

        viewModel.getReleaseDate().observe(getViewLifecycleOwner(), (releaseDate) -> {
            String releaseLabel = getString(R.string.label_release);
            String release = String.format("%s %s", releaseLabel, releaseDate);
            binding.tvRelease.setText(StringUtils.formatWord(release, releaseLabel, new StyleSpan(Typeface.BOLD)));
        });

        viewModel.getDuration().observe(getViewLifecycleOwner(), (duration) -> {
            String durationLabel = getString(R.string.label_duration);
            String text = String.format(getString(R.string.format_duration), durationLabel, duration);
            if (duration == 0) {
                text = getString(R.string.message_unavailable_duration);
            }
            binding.tvDuration.setText(StringUtils.formatWord(text, durationLabel, new StyleSpan(Typeface.BOLD)));
        });

        viewModel.getAppraisal().observe(getViewLifecycleOwner(), (appraisal) -> {
            String text = String.format(getString(R.string.format_average_appraisal), appraisal);
            binding.rbVotes.setProgress(appraisal);
            if (appraisal == 0) {
                binding.tvVotes.setText(R.string.message_not_appraised);
                return;
            }
            binding.tvVotes.setText(text);
        });

        viewModel.getCover().observe(getViewLifecycleOwner(), (cover) -> {
            Glide
                    .with(binding.ivMovieCover)
                    .load(BuildConfig.IMAGE_BASE_URL + cover)
                    .error(R.drawable.ic_baseline_image_24)
                    .into(binding.ivMovieCover);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(),
                (value) -> binding.rlLoader.setVisibility(value != null && value ? View.VISIBLE : View.GONE));

        viewModel.getError().observe(getViewLifecycleOwner(), (error) -> {
            if (error == null || error.trim().isEmpty()) return;
            if (BuildConfig.DEBUG) {
                Log.e(DetailFragment.class.getName(), error);
            }
            showError(R.string.message_fetch_movie_detail_failed);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        long movieId = getArguments().getLong("movieId", -1);
        viewModel.getMovieDetails(movieId);
    }
}
