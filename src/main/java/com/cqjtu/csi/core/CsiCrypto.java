package com.cqjtu.csi.core;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mumu
 * @date 2020/4/16
 */
@Component
public final class CsiCrypto {

    private static byte[] key;
    private static SymmetricCrypto symmetricCrypto;
    private static Sign sign;

    private CsiCrypto() {}

    static {
        update();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private static void update() {
        key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
    }

    public static String enCrypt(String data) {
        return symmetricCrypto.encryptHex(data);
    }

    public static String deCrypt(String data) {
        return symmetricCrypto.decryptStr(data);
    }

}
