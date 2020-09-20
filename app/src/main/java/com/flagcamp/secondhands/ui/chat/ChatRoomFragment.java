package com.flagcamp.secondhands.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.CurrentUserSingleton;
import com.flagcamp.secondhands.databinding.FragmentChatRoomBinding;
import com.flagcamp.secondhands.model.ChatRoom;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomFragment extends Fragment implements ChatFragmentAdapter.ChatFragmentInterface {

    List<ChatRoom> chatRoomList = new ArrayList<>();
    private ChatFragmentAdapter chatFragmentAdapter;
    private FirebaseDatabase database;
    private FragmentChatRoomBinding binding;


    public ChatRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        CurrentUserSingleton currentUser = CurrentUserSingleton.getInstance();

        DatabaseReference friendshipRef = database.getReference("chatRooms/friendship/" + currentUser.getUserId());
        friendshipRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatRoomList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setSenderId(child.child("senderId").getValue().toString());
                    chatRoom.setSenderName(child.child("senderName").getValue().toString());
                    chatRoomList.add(chatRoom);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.fragmentChatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatFragmentAdapter = new ChatFragmentAdapter(chatRoomList, ChatRoomFragment.this);
        binding.fragmentChatRoomRecyclerView.setAdapter(chatFragmentAdapter);
        chatFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void openMessageWindow(User user) {
        ChatRoomFragmentDirections.ActionNavigationChatRoomToNavigationMessage direction = ChatRoomFragmentDirections.actionNavigationChatRoomToNavigationMessage(user);
        NavHostFragment.findNavController(ChatRoomFragment.this).navigate(direction);
    }
}