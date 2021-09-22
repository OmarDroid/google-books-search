package com.trainingkotlin.androidjavagooglebooks.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.trainingkotlin.androidjavagooglebooks.R;
import com.trainingkotlin.androidjavagooglebooks.models.Volume;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class BookSearchResultAdapter extends RecyclerView.Adapter<BookSearchResultAdapter.BookSearchResultHolder> {
    private List<Volume> results = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public BookSearchResultHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookSearchResultHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull BookSearchResultHolder holder, int position) {
        Volume volume = results.get(position);
        if (volume != null) {
            holder.tvTitle.setText(volume.getVolumeInfo().getTitle());
            holder.tvPubDate.setText(volume.getVolumeInfo().getPublishedDate());
            if (volume.getVolumeInfo().getAuthors() != null) {
                holder.tvAuthor.setText(String.join(", ", volume.getVolumeInfo().getAuthors()));
            }
            if (volume.getVolumeInfo().getImageLinks() != null) {
                String imgUrl = volume.getVolumeInfo().getImageLinks().getSmallThumbnail().replace("http://", "https://");
                Timber.i("Image Url %s", imgUrl);
                Glide.with(holder.itemView)
                        .load(imgUrl)
                        .into(holder.imageViewThumbBook);
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public List<Volume> getResults() {
        return results;
    }

    public void setResults(List<Volume> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class BookSearchResultHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvPubDate;
        private ImageView imageViewThumbBook;

        public BookSearchResultHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.book_title);
            tvAuthor = itemView.findViewById(R.id.book_author);
            tvPubDate = itemView.findViewById(R.id.book_pubdate);
            imageViewThumbBook = itemView.findViewById(R.id.book_thumb);
        }
    }
}
