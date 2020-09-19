package com.flagcamp.secondhands.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.databinding.FragmentMessageListBinding;
import com.flagcamp.secondhands.model.Message;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageListFragment extends Fragment{

    private FragmentMessageListBinding binding;
    List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private User user;
    private DatabaseReference myRef, userRef;
    private FirebaseDatabase database;

    private FirebaseAuth auth;
    private String senderId;
    private String messageId="";
    private ValueEventListener val;

    public MessageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessageListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        senderId = getArguments().getString("userId");
        user = (User) getArguments().getSerializable("createdById");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages/" + senderId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot val: dataSnapshot.getChildren()) {
                    Message message = val.getValue(Message.class);
                    messageList.add(message);

                    binding.messageListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    messageAdapter = new MessageAdapter(user, senderId, messageList);
                    binding.messageListRecyclerview.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                    binding.messageListRecyclerview.smoothScrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.chatboxSendButton.setOnClickListener(v -> {
            if (!binding.chatboxEdittext.getText().toString().isEmpty()) {
                String messageId = myRef.push().getKey();
                Message message = new Message(messageId, user.name, binding.chatboxEdittext.getText().toString(), user.userId, user.photoUrl);
                myRef.child(messageId).setValue(message);
                binding.chatboxEdittext.setText("");
            }
        });

        return view;
    }

}