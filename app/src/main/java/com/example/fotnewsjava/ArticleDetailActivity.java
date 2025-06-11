package com.example.fotnewsjava;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView detailTitle, detailDate, detailContent;
    private ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail); // Use your layout here

        // Initialize views
        detailTitle = findViewById(R.id.detailTitle);
        detailDate = findViewById(R.id.detailDate);
        detailContent = findViewById(R.id.detailContent);
        detailImage = findViewById(R.id.detailImage);

        // Retrieve the data passed from the adapter
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String content = getIntent().getStringExtra("fullSummary");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Set the data to the views
        detailTitle.setText(title);
        detailDate.setText(date);
        detailContent.setText(content);

        // Load the image using Glide (if there is an image URL)
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(detailImage);
        } else {
            detailImage.setImageResource(R.drawable.placeholder_image); // Optional: Set a placeholder if image is not available
        }
    }
}
