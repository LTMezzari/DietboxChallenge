package mezzari.torres.lucas.dietbox_challenge.scenes.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import mezzari.torres.lucas.dietbox_challenge.databinding.FragmentHomeBinding;
import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;
import mezzari.torres.lucas.dietbox_challenge.generic.BaseFragment;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class HomeFragment extends BaseFragment {

    @Inject
    public HomeFragmentViewModel viewModel;

    private FragmentHomeBinding binding;
    private MovieAdapter adapter;

    private int currentPage = 1;
    private boolean shouldLoadMore = true;
    private boolean isLoadingMore = false;

    public HomeFragment() {
        DaggerHelper.getInstance().getMainComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasToolbarUpdate(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rvMovies.setAdapter(adapter = new MovieAdapter(binding.rvMovies));

        adapter.setOnEndReachedListener(this::onEndReached);
        adapter.setOnMovieClickedListener(this::onMovieClicked);

        binding.srlMovies.setOnRefreshListener(() -> {
            isLoadingMore = false;
            currentPage = 1;
            viewModel.loadMovies(1);
        });

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
                Log.e(HomeFragment.class.getName(), error);
            }
            showError(R.string.message_fetch_movies_failed);
        });

        currentPage = 1;
        shouldLoadMore = true;
        isLoadingMore = false;
        viewModel.loadMovies(1);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            getNavController().navigate(R.id.action_homeFragment_to_searchFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onEndReached() {
        if (!shouldLoadMore) return;
        currentPage++;
        isLoadingMore = true;
        adapter.setLoading(true);
        viewModel.loadMovies(currentPage);
    }

    private void onMovieClicked(Movie movie) {
        Bundle extras = new Bundle();
        extras.putLong("movieId", movie.getMovieId());
        getNavController().navigate(R.id.action_homeFragment_to_detailFragment, extras);
    }
}
