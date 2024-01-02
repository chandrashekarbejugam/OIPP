package com.codeinfinity.oipp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    String uid = firebaseUser.getUid();

    DatabaseReference db = FirebaseDatabase.getInstance().getReference("");


    public MessageAdapter(List<Message> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView senderText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            senderText = itemView.findViewById(R.id.senderText);
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String name = "";
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Message message = messages.get(position);
        holder.messageText.setText(message.getText());
        holder.senderText.setText(message.getSenderUID());

        // Set layout parameters based on the sender
        //  RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

//        if ("sender".equals(message.getSenderUID())) {
//            // Align sender's messages to the right
//            params.setMarginEnd(0);  // Reset margin for RTL support
//            params.setMarginStart(holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.sender_margin_start));
//            params.width = RecyclerView.LayoutParams.WRAP_CONTENT;
//            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
//            params.setMarginEnd(0);
//        } else {
//            // Align receiver's messages to the left
//            params.setMarginStart(0);
//            params.setMarginEnd(holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.receiver_margin_end));
//            params.width = RecyclerView.LayoutParams.WRAP_CONTENT;
//            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
//            params.setMarginStart(0);
//        }

       // holder.itemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}


