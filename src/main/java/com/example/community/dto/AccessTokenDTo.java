package com.example.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTo {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;

}
