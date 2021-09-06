package mezzari.torres.lucas.dietbox_challenge.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.util.DateUtils;
import mezzari.torres.lucas.dietbox_challenge.util.StringUtils;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final int VIEW_TYPE_MOVIE = 2;

    private final Context context;
    private final LayoutInflater inflater;
    private List<Movie> items;
    private boolean isLoading;

    private OnEndReachedListener onEndReachedListener;
    private OnMovieClickedListener onMovieClickedListener;

    public MovieAdapter(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        this.context = recyclerView.getContext();
        this.inflater = LayoutInflater.from(context);
        this.isLoading = true;
        this.items = new ArrayList<>();
        setupScrollListener(recyclerView);
    }

    private void setupScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getLayoutManager() == null
                        || !(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
                    return;
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = getItemCount();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if (
                        !isLoading
                                && !items.isEmpty()
                                && totalItemCount > 0
                                && lastVisiblePosition >= totalItemCount - 1
                                && onEndReachedListener != null
                ) {
                    recyclerView.post(onEndReachedListener::onEndReached);
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_MOVIE:
                return new MovieViewHolder(inflater.inflate(R.layout.row_movie, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(inflater.inflate(R.layout.row_empty_list, parent, false));
            default:
                return new LoadingViewHolder(inflater.inflate(R.layout.row_loader, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType != VIEW_TYPE_MOVIE) return;
        MovieViewHolder viewHolder = (MovieViewHolder) holder;
        Movie movie = this.items.get(position);
        viewHolder.tvMovieTitle.setText(movie.getTitle());

        String releaseDate = DateUtils.formatDate(movie.getReleaseDate(), "dd/MM/yyyy");
        String releaseLabel = context.getString(R.string.label_release);
        String release = String.format("%s %s", releaseLabel, releaseDate);
        viewHolder.tvRelease.setText(StringUtils.formatWord(release, releaseLabel, new StyleSpan(Typeface.BOLD)));

        viewHolder.rbVotes.setActivated(false);
        viewHolder.rbVotes.setProgress(Math.round(movie.getScore()));
        viewHolder.rbVotes.setMax(10);

        Glide
                .with(context)
                .load(BuildConfig.IMAGE_BASE_URL + movie.getPoster())
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.ivMovieCover);
        viewHolder.itemView.setOnClickListener((view) -> {
            if (onMovieClickedListener == null) return;
            onMovieClickedListener.onMovieClicked(movie);
        });
    }

    @Override
    public int getItemCount() {
        if (items.isEmpty()) {
            return 1;
        }
        if (isLoading) {
            return items.size() + 1;
        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.isEmpty()) {
            return VIEW_TYPE_EMPTY;
        }
        if (isLoading && position == items.size()) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_MOVIE;
    }

    public void setMovies(List<Movie> movies) {
        this.items = new ArrayList<>(movies);
        this.isLoading = false;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {
        int size = this.items.size();
        this.items.addAll(movies);
        notifyItemInserted(size);
    }

    public void setLoading(boolean isLoading) {
        if (this.isLoading == isLoading) return;
        this.isLoading = isLoading;
        if (this.isLoading) {
            notifyItemInserted(this.items.size() + 1);
            return;
        }
        notifyItemRemoved(this.items.size());
    }

    public void setOnEndReachedListener(OnEndReachedListener onEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener;
    }

    public void setOnMovieClickedListener(OnMovieClickedListener onMovieClickedListener) {
        this.onMovieClickedListener = onMovieClickedListener;
    }

    public static final class MovieViewHolder extends RecyclerView.ViewHolder {

        final TextView tvMovieTitle;
        final ImageView ivMovieCover;
        final TextView tvRelease;
        final RatingBar rbVotes;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            this.ivMovieCover = itemView.findViewById(R.id.ivMovieCover);
            this.tvRelease = itemView.findViewById(R.id.tvRelease);
            this.rbVotes = itemView.findViewById(R.id.rbVotes);
        }
    }

    public static final class EmptyViewHolder extends RecyclerView.ViewHolder {
        final TextView tvEmptyList;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvEmptyList = itemView.findViewById(R.id.tvEmptyList);
        }
    }

    public static final class LoadingViewHolder extends RecyclerView.ViewHolder {
        final ProgressBar pbLoader;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pbLoader = itemView.findViewById(R.id.pbLoader);
        }
    }

    public interface OnEndReachedListener {
        void onEndReached();
    }

    public interface OnMovieClickedListener {
        void onMovieClicked(Movie movie);
    }
}
