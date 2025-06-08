package com.example.fotnewsjava.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat; // Import this for ContextCompat

import com.example.fotnewsjava.LoginActivity;
import com.example.fotnewsjava.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText etUsername, etEmail;
    private Button btnEdit, btnSave, btnSignOut;

    private boolean isEditing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnSave = view.findViewById(R.id.btnSave);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        btnEdit.setOnClickListener(v -> toggleEdit(true));

        btnSave.setOnClickListener(v -> {
            toggleEdit(false);
            // Save locally and remotely
            saveProfileData();
        });

        btnSignOut.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Sign Out")
                    .setMessage("Are you sure you want to sign out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut(); // Sign out Firebase user
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        toggleEdit(false);

        // Load saved data from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        etUsername.setText(prefs.getString("username", "Danushka Lakshan"));
        etEmail.setText(prefs.getString("email", "danushka3600@gmail.com"));

        return view;
    }

    private void toggleEdit(boolean enable) {
        isEditing = enable;

        // Enable or disable editing mode
        etUsername.setEnabled(enable);
        etEmail.setEnabled(enable);

        etUsername.setFocusable(enable);
        etUsername.setFocusableInTouchMode(enable);
        etUsername.setCursorVisible(enable); // Make cursor visible when editing

        etEmail.setFocusable(enable);
        etEmail.setFocusableInTouchMode(enable);
        etEmail.setCursorVisible(enable); // Make cursor visible when editing

        // Change background color when in editing mode
        if (enable) {
            etUsername.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.editTextFocused)); // Custom color
            etEmail.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.editTextFocused)); // Custom color
        } else {
            etUsername.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.editTextDefault)); // Default color
            etEmail.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.editTextDefault)); // Default color
        }

        // Enable or disable buttons based on editing mode
        btnSave.setEnabled(enable);
        btnEdit.setEnabled(!enable);
    }

    private void saveProfileData() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Save locally
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("username", username)
                .putString("email", email)
                .apply();

        // Save remotely in Firebase Realtime Database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            Map<String, Object> updates = new HashMap<>();
            updates.put("username", username);
            updates.put("email", email);

            userRef.updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save profile: " + task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_LONG).show();
        }
    }
}
