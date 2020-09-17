package com.flagcamp.secondhands.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.databinding.FragmentProfileBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            binding.profileNameTextView.setText(user.getDisplayName());
            Picasso.get().load(user.getPhotoUrl()).into(binding.profilePhotoImageView);
            binding.profileUserIdTextView.setText(user.getUid());
            binding.profileEmailTextView.setText(user.getEmail());
//        binding.profileRatingScoreTextView.setText(getRating(user.getUid)); // 后端给getRating()

            binding.profileMessageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // link to Message List Page
                }
            });

            binding.profileOrderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // link to Order Management page
                }
            });

            binding.profileLogoutButton.setOnClickListener(v -> FirebaseAuth.getInstance().signOut());
        }

    }

}