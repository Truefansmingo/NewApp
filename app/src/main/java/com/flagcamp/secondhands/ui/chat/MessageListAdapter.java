package com.flagcamp.secondhands.ui.chat;

import android.content.Context;
import android.text.Layout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.model.Message;
import com.flagcamp.secondhands.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Query;

public class MessageListAdapter extends FirestoreRecyclerAdapter<Message, RecyclerView.ViewHolder> {

    private final int MESSAGE_IN_VIEW_TYPE  = 1;
    private final int MESSAGE_OUT_VIEW_TYPE = 2;
    FirebaseUser user;

    public MessageListAdapter(Query query) {
        super(new FirestoreRecyclerOptions.Builder<Message>()
        .setQuery(query, Message.class)
        .build());

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getMessageUserId().equals(user.getUid())){
            return MESSAGE_OUT_VIEW_TYPE;
        }
        return MESSAGE_IN_VIEW_TYPE;
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, Message message) {
        switch (viewHolder.getItemViewType()) {
            case MESSAGE_IN_VIEW_TYPE:
                ReceivedMessageViewHolder holder = (ReceivedMessageViewHolder) viewHolder;
                holder.bind(message);
                // 通过message.getMessageUserId()从后端拿到对应user的photoUrl
//                Picasso.get().load(photoUrl).into(holder.imgProfile);
                break;
            case MESSAGE_OUT_VIEW_TYPE:
                ((SentMessageViewHolder) viewHolder).bind(message);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == MESSAGE_IN_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }
}