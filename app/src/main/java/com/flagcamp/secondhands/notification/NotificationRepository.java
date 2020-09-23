package com.flagcamp.secondhands.notification;

import android.content.Context;
import android.util.Log;

import com.flagcamp.secondhands.CurrentUserSingleton;
import com.flagcamp.secondhands.model.Message;
import com.flagcamp.secondhands.network.Api;
import com.flagcamp.secondhands.network.RetrofitClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {
    private final Api api;
    private FirebaseFirestore database;
    private CurrentUserSingleton currentUser;

    public NotificationRepository(Context context) {
        api = RetrofitClient.newInstance(context).create(Api.class);
        currentUser = CurrentUserSingleton.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public void updateToken() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put(currentUser.getUserId(),refreshToken);
        database.document("chatRooms/tokens").set(map);
    }

    public void sendNotifications(String token, Message message) {
        NotificationSender sender = new NotificationSender(message, token);
        api.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().success != 1) {
                                Log.d("SendNotification", "Failed");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
    }
}
