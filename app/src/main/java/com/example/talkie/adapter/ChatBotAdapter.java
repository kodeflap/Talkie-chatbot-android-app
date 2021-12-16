package com.example.talkie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkie.R;
import com.example.talkie.model.ChatModel;

import java.util.ArrayList;

public class ChatBotAdapter extends RecyclerView.Adapter {


    public ChatBotAdapter(ArrayList<ChatModel> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    private ArrayList<ChatModel> chatList;
    private Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
       switch (viewType)
       {
           case 0: view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_item,parent,false);
                    return new UserViewHolder(view);
           case 1: view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message_item,parent,false);
                    return new BotViewHolder(view);
       }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       ChatModel chatModel = chatList.get(position);
       switch (chatModel.getSender())
       {
           case "user": ((UserViewHolder)holder).user_M.setText(chatModel.getMessage());
                 break;
           case "bot" :((BotViewHolder)holder).bot_M.setText(chatModel.getMessage());
                 break;

       }

    }

    @Override
    public int getItemViewType(int position) {
        switch (chatList.get(position).getSender())
        {
            case "user":return 0;
            case "bot":return 1;
            default:return -1;
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView user_M;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_M = itemView.findViewById(R.id.user_msg);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder
    {
        TextView bot_M;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            bot_M = itemView.findViewById(R.id.bot_msg);
        }
    }
}
