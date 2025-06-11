package com.example.fotnewsjava.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fotnewsjava.ArticleDetailActivity;
import com.example.fotnewsjava.R;
import com.example.fotnewsjava.models.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public interface OnReadMoreClickListener {
        void onReadMoreClick(Article article);
    }

    private List<Article> articleList;
    private OnReadMoreClickListener readMoreClickListener;

    public ArticleAdapter(List<Article> articleList, OnReadMoreClickListener listener) {
        this.articleList = articleList;
        this.readMoreClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false); // your card layout XML name
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.articleTitle.setText(article.getTitle());
        holder.articleSummary.setText(article.getSummary()); // Short summary
        holder.articleDate.setText(article.getDate());

        String imageUrl = article.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.articleImage);
        } else {
            holder.articleImage.setImageDrawable(null); // Optionally set a default image or clear image
        }

        // Open detailed view on "Read More" click
        holder.readMore.setOnClickListener(v -> {
            // Pass data to ArticleDetailActivity
            Intent intent = new Intent(holder.itemView.getContext(), ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("date", article.getDate());
            intent.putExtra("fullSummary", article.getFullSummary());
            intent.putExtra("imageUrl", article.getImageUrl());

            // Start the new activity
            holder.itemView.getContext().startActivity(intent);
        });
    }





    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleTitle, articleSummary, articleDate, readMore, fullSummary;
        ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleSummary = itemView.findViewById(R.id.articleSummary);
            articleDate = itemView.findViewById(R.id.articleDate);
            articleImage = itemView.findViewById(R.id.articleImage);
            readMore = itemView.findViewById(R.id.readMore);
            fullSummary = itemView.findViewById(R.id.fullSummary); // Added for full summary
        }
    }
}
