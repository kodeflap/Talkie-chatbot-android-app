package com.example.talkie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.talkie.adapter.ChatBotAdapter;
import com.example.talkie.api.RetrofitApi;
import com.example.talkie.model.ChatModel;
import com.example.talkie.model.MessageModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBotActivity extends AppCompatActivity {

    private RecyclerView chats;
    private EditText sendMessage;
    private FloatingActionButton send;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatModel> chatList;
    private ChatBotAdapter chatBotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        chats = findViewById(R.id.messages);
        sendMessage = findViewById(R.id.editText_message);
        send = findViewById(R.id.send);

        chatList = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(chatList,this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        chats.setLayoutManager(manager);
        chats.setAdapter(chatBotAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendMessage.getText().toString().isEmpty())
                {
                    Toast.makeText(ChatBotActivity.this,"Enter the Message",Toast.LENGTH_LONG).show();
                    return;
                }
               getResponse(sendMessage.getText().toString());
                sendMessage.setText("");
            }
        });


    }

    private void getResponse(String message)
    {
        chatList.add(new ChatModel(message,USER_KEY));
        chatBotAdapter.notifyDataSetChanged();
        String url = "api url="+message;
        String BASE_URL = "api url base here";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<MessageModel> messageModelCall = retrofitApi.getMessage(url);
        messageModelCall.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if(response.isSuccessful())
                {
                    MessageModel messageModel = response.body();
                    chatList.add(new ChatModel(messageModel.getCnt(),BOT_KEY));
                    chatBotAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                chatList.add(new ChatModel("Please revert your question",BOT_KEY));
                chatBotAdapter.notifyDataSetChanged();
            }
        });

    }
}