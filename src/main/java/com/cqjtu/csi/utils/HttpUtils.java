package com.cqjtu.csi.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/2/18
 */
public class HttpUtils {
    private static final Map<Integer, String> msgMap = new HashMap<>(3);

    static {
        msgMap.put(400, "你可能需要完善你的请求！");
        msgMap.put(401, "你可能需要登录以获取更多内容！");
        msgMap.put(403, "你可能需要更高的权限才能完成操作！");
    }

    public static String getHttpStatusMsg(Integer httpStatus) {
        return msgMap.get(httpStatus);
    }

    private HttpUtils() { }
}
