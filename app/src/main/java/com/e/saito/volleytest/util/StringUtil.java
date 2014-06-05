package com.e.saito.volleytest.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by e.saito on 2014/06/05.
 */
public class StringUtil {
    private static boolean checkCharacterCode(String str, String encoding) {
        if (str == null) {
            return true;
        }

        try {
            byte[] bytes = str.getBytes(encoding);
            return str.equals(new String(bytes, encoding));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("エンコード名称が正しくありません。", ex);
        }
    }

    public static boolean isWindows31j(String str) {
        return checkCharacterCode(str, "Windows-31j");
    }

    public static boolean isSJIS(String str) {
        return checkCharacterCode(str, "SJIS");
    }

    public static boolean isEUC(String str) {
        return checkCharacterCode(str, "euc-jp");
    }

    public static boolean isUTF8(String str) {
        return checkCharacterCode(str, "UTF-8");
    }
}
