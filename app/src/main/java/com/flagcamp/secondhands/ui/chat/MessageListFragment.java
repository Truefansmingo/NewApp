package com.flagcamp.secondhands.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentMessageListBinding;
import com.flagcamp.secondhands.model.Message;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MessageListFragment extends Fragment implements View.OnClickListener {

    private FragmentMessageListBinding binding;
    FirebaseUser user;
    FirebaseFirestore database;
    Query query;
    private MessageListAdapter adapter;

    public MessageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessageListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // login fragment
        }

        database = FirebaseFirestore.getInstance();
        query = database.collection("messages").orderBy("messageTime");
        adapter = new MessageListAdapter(query);
        binding.messageListRecyclerview.setAdapter(adapter);
        binding.messageListRecyclerview.setLayoutManager((new LinearLayoutManager(requireContext())));
        binding.chatboxSendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chatbox_send_button) {
            String message = binding.chatboxEdittext.getText().toString();
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(getActivity(), "Message is empty", Toast.LENGTH_LONG).show();
                return;
            }
            database.collection("messages").add(new Message(user.getDisplayName(), message, user.getUid()));
            binding.chatboxEdittext.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }
}