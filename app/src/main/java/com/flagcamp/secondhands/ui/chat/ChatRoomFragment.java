package com.flagcamp.secondhands.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentChatRoomBinding;
import com.flagcamp.secondhands.model.ChatRoom;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomFragment extends Fragment implements ChatFragmentAdapter.ChatFragmentInterface {

    List<ChatRoom> chatRoomList = new ArrayList<>();
    private View view;
    private User user;
    private ChatFragmentAdapter chatFragmentAdapter;
    private StorageReference storageRef;
    private DatabaseReference myRef;
    private FirebaseAuth auth;
//    private String userId;
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
        view = binding.getRoot();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        user.userId = currentUser.getUid();

        if (user != null) {
            myRef = database.getReference("userProfiles/" + user.userId);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("ChatRoomFragment", "Failed to read value.", databaseError.toException());
                }
            });
        }

        myRef = database.getReference("chatRoom");
        storageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatRoomList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setUserId(child.child("userId").getValue().toString());
                    chatRoom.setUserName(child.child("userName").getValue().toString());
                    chatRoom.setCreatedById(child.child("createdById").getValue().toString());
                    chatRoom.setCreatedByName(child.child("createdByName").getValue().toString());
                    if (chatRoom.getCreatedById().equals(user.userId)) {
                        chatRoomList.add(chatRoom);
                    }
                }

                binding.fragmentChatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                chatFragmentAdapter = new ChatFragmentAdapter(user, chatRoomList, ChatRoomFragment.this);
                binding.fragmentChatRoomRecyclerView.setAdapter(chatFragmentAdapter);
                chatFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void openMessageWindow(MessageListFragment messageFragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, messageFragment,"Message Fragment").addToBackStack(null);
        fragmentTransaction.commit();
    }
}