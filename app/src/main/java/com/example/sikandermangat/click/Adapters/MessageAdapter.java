package com.example.sikandermangat.click.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikandermangat.click.Model.ChatMessage;
import com.example.sikandermangat.click.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<ChatMessage> chatList;

    private Context context;


    public MessageAdapter(List<ChatMessage> chatList) {
        this.chatList = chatList;


    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChatMessage chatMessage=chatList.get(position);

        holder.message_user.setText(chatMessage.getMessageUser());
        holder.message_text.setText(chatMessage.getMessageText());
        holder.message_time.setText(String.valueOf(chatMessage.getMessageTime()));


    }

    @Override
    public int getItemCount() {
        return chatList.size();


    }


    public class MyViewHolder  extends RecyclerView.ViewHolder {

        TextView message_text,message_user,message_time;
        public MyViewHolder(View itemView) {

            super(itemView);
            message_text=itemView.findViewById(R.id.message_text);
            message_time=itemView.findViewById(R.id.message_time);
            message_user=itemView.findViewById(R.id.message_user);

        }
    }
}



