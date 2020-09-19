package com.flagcamp.secondhands.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.ItemChatBinding;
import com.flagcamp.secondhands.model.ChatRoom;
import com.flagcamp.secondhands.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ViewHolder> {

    User user;
    List<ChatRoom> chatRoomList;
    ChatFragmentInterface chatFragmentInterface;

    public ChatFragmentAdapter(User user, List<ChatRoom> chatRoomList, ChatFragmentInterface chatFragmentInterface) {
        this.chatRoomList = chatRoomList;
        this.user = user;
        this.chatFragmentInterface = chatFragmentInterface;
    }

    @NonNull
    @Override
    public ChatFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ViewHolder viewHolder = new ChatFragmentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatFragmentAdapter.ViewHolder holder, int position) {
        final ChatRoom chatRoom = chatRoomList.get(position);
        holder.senderName.setText(chatRoom.getUserName());

        holder.chatButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("userId", user);
            bundle.putString("senderId", chatRoomList.get(position).getUserId());
            MessageListFragment messageFragment = new MessageListFragment();
            messageFragment.setArguments(bundle);
            chatFragmentInterface.openMessageWindow(messageFragment);
        });
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public interface ChatFragmentInterface {
        void openMessageWindow(MessageListFragment message);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderName;
        FloatingActionButton chatButton;

        ViewHolder(View itemView) {
            super(itemView);
            ItemChatBinding binding = ItemChatBinding.bind(itemView);
            senderName = binding.itemChatText;
            chatButton = binding.itemChatButton;
        }
    }
}
