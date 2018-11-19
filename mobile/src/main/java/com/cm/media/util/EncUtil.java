package com.cm.media.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncUtil {
    public static String enc(String paramString) {
        if ((paramString != null) && (paramString.length() != 0)) {
            StringBuilder localStringBuffer = new StringBuilder();
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(paramString.getBytes());
                byte[] digestBytes = digest.digest();
                for (byte digestByte : digestBytes) {
                    if ((digestByte & 0xFF) < 16) {
                        localStringBuffer.append("0");
                        localStringBuffer.append(Integer.toHexString(digestByte & 0xFF));
                    }
                    localStringBuffer.append(Integer.toHexString(digestByte & 0xFF));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return localStringBuffer.toString();
        }
        throw new IllegalArgumentException("String to encript cannot be null or zero length");
    }
}
