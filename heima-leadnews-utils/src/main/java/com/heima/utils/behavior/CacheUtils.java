package com.heima.utils.behavior;

public class CacheUtils {
    public static String getCacheKey(String userId, String type, String action,String articleId) {
        return String.join(":",userId, type, action, articleId);
    }
}
