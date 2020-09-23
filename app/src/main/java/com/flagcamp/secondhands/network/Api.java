package com.flagcamp.secondhands.network;

import com.flagcamp.secondhands.notification.MyResponse;
import com.flagcamp.secondhands.notification.NotificationSender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: key=AAAAIquc6AY:APA91bG9We6WDhKSHHDEz0O1F7qTP_1n75rdXr8KKi7RGoBBJ4rrZMGTcl-Zq7y0No2N3C5q8y3kdnRSuhFhEkG2wG-jdFF32EQMApqQTwgV9lntwo_wZOCo7FvOTOX5d6WBLDobFqpq"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
