package com.best2team.facebook_clone_be.utils;

public class SocketUtil {
    public static String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
}
