package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.core.CsiCrypto;
import com.cqjtu.csi.service.CryptoService;
import org.springframework.stereotype.Service;

/**
 * @author mumu
 * @date 2020/4/16
 */
@Service
public class CryptoServiceImpl implements CryptoService {

    @Override
    public String enCrypt(String data) {
        return CsiCrypto.enCrypt(data);
    }

    @Override
    public String deCrypt(String data) {
        return CsiCrypto.deCrypt(data);
    }
}
