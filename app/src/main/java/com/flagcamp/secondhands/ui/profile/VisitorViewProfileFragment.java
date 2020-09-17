package com.flagcamp.secondhands.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentVisitorViewProfileBinding;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class VisitorViewProfileFragment extends Fragment {

    private FragmentVisitorViewProfileBinding binding;

    public VisitorViewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visitor_view_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = VisitorViewProfileFragmentArgs.fromBundle(getArguments()).getUser();
        binding.visitorViewProfileNameTextView.setText(user.name);
        Picasso.get().load(user.photoUrl).into(binding.visitorViewProfilePhotoImageView);
        binding.visitorViewProfileEmailTextView.setText(user.email);
        binding.visitorViewProfileRatingScoreTextView.setText(user.rating);

        binding.visitorViewProfileChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // link to new Chat Page
            }
        });
    }
}