package mezzari.torres.lucas.dietbox_challenge.scenes.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.adapter.MovieAdapter;
import mezzari.torres.lucas.dietbox_challenge.databinding.FragmentSearchBinding;
import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseFragment;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.util.BindingUtils;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class SearchFragment extends BaseFragment {

    @Inject
    public SearchFragmentViewModel viewModel;

    private FragmentSearchBinding binding;
    private MovieAdapter adapter;

    private int currentPage = 1;
    private boolean shouldLoadMore = true;
    private boolean isLoadingMore = false;

    public SearchFragment() {
        DaggerHelper.getInstance().getMainComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rvMovies.setAdapter(adapter = new MovieAdapter(binding.rvMovies));

        adapter.setOnEndReachedListener(this::onEndReached);
        adapter.setOnMovieClickedListener(this::onMovieClicked);

        BindingUtils.bindSearchView(getViewLifecycleOwner(), viewModel.getQuery(), binding.etSearch, this::onSearch);

        binding.srlMovies.setOnRefreshListener(this::onSearch);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), (isLoading) -> {
            adapter.setLoading(isLoading != null && isLoading);
        });

        viewModel.getMovies().observe(getViewLifecycleOwner(), (value) -> {
            binding.srlMovies.setRefreshing(false);
            shouldLoadMore = value != null && value.size() >= 20;
            List<Movie> list = value != null ? value : new ArrayList<>();
            if (isLoadingMore) {
                adapter.addMovies(list);
                return;
            }
            adapter.setMovies(list);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), (error) -> {
            if (error == null || error.trim().isEmpty()) return;
            if (BuildConfig.DEBUG) {
                Log.e(SearchFragment.class.getName(), error);
            }
            showError(R.string.message_search_movies_failed);
        });
    }

    private void onEndReached() {
        if (!shouldLoadMore) return;
        currentPage++;
        isLoadingMore = true;
        adapter.setLoading(true);
        viewModel.searchMovies(currentPage);
    }

    private void onSearch() {
        isLoadingMore = false;
        currentPage = 1;
        viewModel.searchMovies(1);
    }

    private void onMovieClicked(Movie movie) {
        Bundle extras = new Bundle();
        extras.putLong("movieId", movie.getMovieId());
        getNavController().navigate(R.id.action_searchFragment_to_detailFragment, extras);
    }
}
