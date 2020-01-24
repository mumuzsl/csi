package com.cqjtu.csi.core.support;

/**
 * @author mumu
 * @date 2020/1/22
 */
public class CsiConst {

    /**
     * 获取用户目录路径
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    public static final String SUCCESS = "成功";

    public static final String FAILED = "失败";

    public static final String FACE_MATCH_FAILED = "人脸匹配失败";

    public static final String FACE_MATCH_SUCCESS = "人脸匹配成功";

    public static final String FACE_ADD_SUCCESS = "人脸注册成功";

    public static final String REGISTER_SUCCESS = "注册成功";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_FAILED = "登录失败";

    public static final String SUFFIX_FTL = ".ftl";

    public static final String CLASS_PATH = "classpath:";

    public static final String IMG_DIR = USER_HOME + "/img";

    public static final String SUFFIX_FACE = ".face";

    public static final String FACE_FILE_FORMAT = IMG_DIR + "/{0}" + SUFFIX_FACE;

    public static String toFacePath(String name) {
        return String.format(FACE_FILE_FORMAT, name);
    }
}
