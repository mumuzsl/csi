package com.cqjtu.csi.core;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.cqjtu.csi.exception.FaceClientException;
import com.cqjtu.csi.utils.BaseUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * 调用百度人脸识别api，用于人脸登录、注册。
 *
 * @author mumu
 * @date 2020/1/23
 */
public class FaceClient {

    private FaceClient() {}

    // 常用百度api返回的json属性
    private static final String FACE_TOKEN = "face_token";
    private static final String USER_ID = "user_id";
    private static final String USER_LIST = "user_list";
    private static final String SCORE = "score";
    private static final String ERR_MSG = "error_msg";
    private static final String ERR_CODE = "error_code";
    private static final String RESULT = "result";

    //  百度api错误信息头
    private static final String ERROR_MSG_HEADER = "百度api错误:";

    //设置APPID/AK/SK
    private static final String APP_ID = "18334413";
    private static final String API_KEY = "AH3gNZ64HPyey69dVa768u10";
    private static final String SECRET_KEY = "LkhLPpkpnuwzQttNdLMoy6vOgsAbKyBl";
    private static final AipFace CLENT = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    private static final HashMap<String, String> OPTIONS = new HashMap<>();
    private static final Map<String, String> MSG_MAP = new HashMap<>();
    private static final Float DEFAULT_SCORE = 92f;
    private static final String GROUP = "user";
    private static final String IMAGE_TYPE = "BASE64";

    static {
        OPTIONS.put("liveness_control", "NORMAL");

        MSG_MAP.put("222202", "图片中没有人脸");
        MSG_MAP.put("222203", "无法解析人脸");
    }

    public FaceClient newInstance() {
        return new FaceClient();
    }

    /**
     * 在人脸库中进行搜索，取结果列表中的第一个结果。
     *
     * @param base64 图片数据经过Base64编码后的字符串
     * @return
     */
    public static JSONObject search(String base64) {
        JSONObject jsonObject = CLENT.search(base64, IMAGE_TYPE, GROUP, OPTIONS);
        return Optional.ofNullable(jsonObject)
                .map(object -> check(object))
                .map(integer -> jsonObject.has(RESULT))
                .map(b -> b ? jsonObject.getJSONObject(RESULT) : null)
                .map(object -> object.getJSONArray(USER_LIST))
                .map(array -> array.getJSONObject(0))
                .orElseThrow(() -> new FaceClientException("没有匹配到结果，你可能没有人脸注册。").setErrorData(jsonObject));
    }

    public static boolean verify(JSONObject object, float score) {
        return getScore(object) - score >= 0;
    }

    public static boolean verify(JSONObject object) {
        return verify(object, DEFAULT_SCORE);
    }

    public static float getScore(JSONObject object) {
        return Float.parseFloat(getString(object, SCORE));
    }

    public static Integer getUseId(JSONObject object) {
        return Integer.parseInt(getString(object, USER_ID));
    }

    public static String getString(JSONObject object, String field) {
        return String.valueOf(
                Optional.ofNullable(object)
                        .map(o -> o.has(field))
                        .map(b -> b ? object.get(field) : null)
                        .orElseThrow(() -> new FaceClientException("无法从json中获取" + field)));
    }

    /**
     * 使用百度api进行人脸图片比较
     *
     * @param imgName 原人脸图片名
     * @param imgStr  需要比较的人脸图片的Base64编码的字符串
     */
    public static boolean match(String imgName, String imgStr) {
        BufferedInputStream inputStream = FileUtil.getInputStream(BaseUtils.toPath(CsiConst.IMG_DIR, ""));

        MatchRequest req1 = new MatchRequest(Base64.encode(inputStream), IMAGE_TYPE);
        MatchRequest req2 = new MatchRequest(imgStr, IMAGE_TYPE);

        List<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);

        JSONObject res = CLENT.match(requests);
        System.out.println(res.toString(2));

        return false;
    }

    @Deprecated
    public static void addFace(String base64, String name) {
        String path = BaseUtils.toPath(CsiConst.toFacePath(name));
        System.out.println(path);
        FileUtil.writeString(base64, path, "UTF-8");
    }

    /**
     * 向百度api添加人脸数据，即完成人脸注册。
     *
     * @param id     将用户id用于百度人脸库的中的user_id
     * @param base64 图片数据经过Base64编码后的字符串
     * @return 百度api返回的face_token
     */
    public static String addFace(Integer id, String base64) {
        JSONObject res = CLENT.addUser(base64, IMAGE_TYPE, GROUP, String.valueOf(id), OPTIONS);
        System.out.println(res.toString());
        return getString(check(res), FACE_TOKEN);
    }

    public static Optional getValue(JSONObject jsonObject, String field) {
        return Optional.ofNullable(jsonObject)
                .map(jsonObject1 -> jsonObject1.has(field))
                .map(b -> b ? jsonObject.get(field) : null);
    }

    /**
     * 检查百度api是否出错，出错则抛出BaseException异常，该异常携带出错信息。
     *
     * @param jsonObject 百度api返回的json
     */
    private static JSONObject check(JSONObject jsonObject) {
        return Optional.ofNullable(jsonObject)
                .map(object -> object.has(ERR_CODE))
                .map(b -> b ? jsonObject.getInt(ERR_CODE) : -1)
                .map(integer -> integer != 0 ? null : jsonObject)
                .orElseThrow(() ->
                        new FaceClientException(ERROR_MSG_HEADER + MSG_MAP.get(getString(jsonObject, ERR_CODE)))
                                .setErrorData(jsonObject));
    }
}
