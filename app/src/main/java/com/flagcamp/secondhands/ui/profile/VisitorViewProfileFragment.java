package com.flagcamp.secondhands.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentVisitorViewProfileBinding;
import com.flagcamp.secondhands.model.User;
import com.flagcamp.secondhands.ui.chat.ChatFragmentAdapter;
import com.flagcamp.secondhands.ui.chat.ChatRoomFragment;
import com.flagcamp.secondhands.ui.chat.ChatRoomFragmentDirections;
import com.flagcamp.secondhands.ui.chat.MessageListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class VisitorViewProfileFragment extends Fragment implements ChatFragmentAdapter.ChatFragmentInterface{

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
        binding.visitorViewProfileChatButton.setOnClickListener(v -> openMessageWindow(user));
    }

    @Override
    public void openMessageWindow(User user) {
        VisitorViewProfileFragmentDirections.ActionNavigationVisitorViewProfileToNavigationMessage direction = VisitorViewProfileFragmentDirections.actionNavigationVisitorViewProfileToNavigationMessage(user);
        NavHostFragment.findNavController(VisitorViewProfileFragment.this).navigate(direction);
    }

}