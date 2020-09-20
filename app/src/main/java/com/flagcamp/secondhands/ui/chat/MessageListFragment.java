package com.flagcamp.secondhands.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.CurrentUserSingleton;
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
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public MessageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessageListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        CurrentUserSingleton currentUser = CurrentUserSingleton.getInstance();
        User sender = MessageListFragmentArgs.fromBundle(getArguments()).getUser();

        database = FirebaseDatabase.getInstance();
        String chatRoomName = currentUser.getUserId() + ',' + sender.userId;
        if (Integer.parseInt(currentUser.getUserId()) > Integer.parseInt(sender.userId)) {
            chatRoomName = sender.userId + ',' + currentUser.getUserId();
        }
        
        myRef = database.getReference("chatRooms/chatRoom/" + chatRoomName + '/');
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Message message = child.getValue(Message.class);
                    messageList.add(message);

                    binding.messageListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    messageAdapter = new MessageAdapter(sender, messageList);
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
                Message message = new Message(messageId, sender.name, binding.chatboxEdittext.getText().toString(), sender.userId, sender.photoUrl);
                myRef.child(messageId).setValue(message);
                binding.chatboxEdittext.setText("");
            }
        });

        return view;
    }

}