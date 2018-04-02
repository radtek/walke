package walke.base.tool;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密处理
 *
 * @author Administrator
 */
public class AESUtil {

    final private static String AES = "AES";

    final private static String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    private static Key getKey(String key) throws Exception {
        key = MD5Util.MD5Encode(key);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AESUtil.AES);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = AESUtil.getKey(key);
        Cipher cipher = Cipher.getInstance(AESUtil.CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = AESUtil.getKey(key);
        Cipher cipher = Cipher.getInstance(AESUtil.CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
