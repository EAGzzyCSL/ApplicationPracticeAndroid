package com.bit.schoolcomment.event;


public class GetTokenEvent {
    public String token;
    public Class targetClass;

    public GetTokenEvent(String token, Class targetClass) {
        this.token = token;
        this.targetClass = targetClass;
    }
}
