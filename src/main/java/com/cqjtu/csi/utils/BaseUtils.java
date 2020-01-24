package com.cqjtu.csi.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.cqjtu.csi.core.support.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.support.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.Optional;
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

    public static Integer userStatus(String status) {
        if (status.equals("1"))
            return 1;
        else if (status.equals("2"))
            return 2;
        else
            throw new BadRequestException("未知权限种类");
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

    public static String toPath(String... name) {
        return StringUtils.join(name, "/");
    }

    public static String data2SHA256(String data) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(data);
    }


}
