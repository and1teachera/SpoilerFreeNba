package com.zlatenov.spoilerfreeapp.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * @author Angel Zlatenov
 */

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
