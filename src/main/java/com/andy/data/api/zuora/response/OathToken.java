package com.andy.data.api.zuora.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

@Data
public class OathToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private Integer expiresIn;  // seconds

    public boolean isExpired(Date createDate) {
        Date now = new Date();
        return (now.getTime() - createDate.getTime() > expiresIn*1000);
    }
}
//{"access_token":"9a74c6d235a44d7eafb2c26e7ac8e9d8","token_type":"bearer","expires_in":3598,"scope":"entity.9e3a75d9-1857-c688-011f-51040c4a1d9b platform.write service.events.read service.events.write service.genesis.read service.genesis.write service.notifications.read service.notifications.write service.usage.delete service.usage.update service.usage.write tenant.33405 user.2c92c0f97209c41d01720fb2b24b110e","jti":"9a74c6d235a44d7eafb2c26e7ac8e9d8"}
