package com.vk.flowable.shiro;

import com.vk.flowable.domain.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class AdminSecurityUtils {

	public static final String HASH_ALGORITHM = "SHA-1";//指定散列算法为MD5,还有别的算法如：SHA256、SHA1、SHA512
	public static final int HASH_INTERATIONS = 10;      //散列迭代次数 md5(md5(pwd)): new Md5Hash(pwd, salt, 2).toString()

    //加密：输入明文得到密文
    public static void encryptPassword(User user) {

        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = randomNumberGenerator.nextBytes().toHex();
        user.setSalt(salt);
        String newPassword = new SimpleHash(
                HASH_ALGORITHM,
                user.getPassword(),
                ByteSource.Util.bytes(salt),
                HASH_INTERATIONS).toHex();
        user.setPassword(newPassword);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("123456");
        encryptPassword(user);
        System.out.println(user.getPassword());
    }
}
