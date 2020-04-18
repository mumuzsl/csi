package com.cqjtu.csi.service;

import org.springframework.stereotype.Service;

/**
 * @author mumu
 * @date 2020/4/16
 */
@Service
public interface CryptoService {

    String enCrypt(String data);

    String deCrypt(String data);

}
