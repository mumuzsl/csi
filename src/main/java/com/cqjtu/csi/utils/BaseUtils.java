package com.cqjtu.csi.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.core.role.Role;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class BaseUtils {

    /**
     * Gets random uuid without dash.
     *
     * @return random uuid without dash
     */
    @NonNull
    public static String randomUUIDWithoutDash() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }

    @Deprecated
    public static Integer userStatus(String status) {
        if (Role.ADMIN.compare(status)) {
            return 1;
        } else if (Role.NORMAL.compare(status)) {
            return 2;
        } else {
//            throw new BadRequestException("未知权限种类");
            return 0;
        }
    }

    public static Map<String, Object> oneKeyValueMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private static boolean compareIntAndStr(Integer i, String s) {
        return String.valueOf(i).equals(s);
    }

    public static BaseResponse insertSucceed() {
        return BaseResponse.ok("插入成功");
    }

    public static BaseResponse deleteSucceed() {
        return BaseResponse.ok("删除成功");
    }

    public static BaseResponse updateSucceed() {
        return BaseResponse.ok("更新成功");
    }

    public static BaseResponse apiDisable() {return BaseResponse.to400("该api接口已废弃，不可用");}

    public static String toPath(String... name) {
        return StringUtils.join(name, "/");
    }

    public static String data2SHA256(String data) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(data);
    }

    public static String webStr(String s) {
        return webStr(s, CsiConst.DEFAULT_CHARSET);
    }

    public static String webStr(String s, String charset) {
        return StrUtil.str(StrUtil.bytes(s, charset), "ISO8859-1");
    }
}
