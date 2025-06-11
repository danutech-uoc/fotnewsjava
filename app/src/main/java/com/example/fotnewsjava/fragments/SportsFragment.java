package com.example.fotnewsjava.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fotnewsjava.ArticleDetailActivity;
import com.example.fotnewsjava.R;
import com.example.fotnewsjava.adapters.ArticleAdapter;
import com.example.fotnewsjava.models.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SportsFragment extends Fragment implements ArticleAdapter.OnReadMoreClickListener {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private List<Article> articleList;
    private List<Article> filteredList;
    private EditText searchBar;

    private static final String TAG = "SportsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);

        searchBar = view.findViewById(R.id.searchBarSports);
        recyclerView = view.findViewById(R.id.recyclerViewSports);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        articleList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new ArticleAdapter(filteredList, this);
        recyclerView.setAdapter(adapter);

        fetchArticlesFromFirebase();

        setupSearchListener();

        return view;
    }

    private void fetchArticlesFromFirebase() {
        DatabaseReference articlesRef = FirebaseDatabase.getInstance()
                .getReference("articles/sports");

        articlesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                articleList.clear();
                for (DataSnapshot articleSnapshot : snapshot.getChildren()) {
                    Article article = articleSnapshot.getValue(Article.class);
                    if (article != null) {
                        articleList.add(article);
                    }
                }
                filteredList.clear();
                filteredList.addAll(articleList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to fetch articles", error.toException());
            }
        });
    }

    private void setupSearchListener() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterArticles(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });
    }

    private void filterArticles(String query) {
        String lowerCaseQuery = query.toLowerCase();
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(articleList);
        } else {
            for (Article article : articleList) {
                if (article.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        article.getSummary().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(article);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReadMoreClick(Article article) {
        // Create an Intent to navigate to ArticleDetailActivity
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);

        // Passing the article data to ArticleDetailActivity
        intent.putExtra("title", article.getTitle());
        intent.putExtra("date", article.getDate());
        intent.putExtra("fullSummary", article.getFullSummary()); // Full summary instead of short summary
        intent.putExtra("imageUrl", article.getImageUrl());

        // Start ArticleDetailActivity
        startActivity(intent);
    }
}
