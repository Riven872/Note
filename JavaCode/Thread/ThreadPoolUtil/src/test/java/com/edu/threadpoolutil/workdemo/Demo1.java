package com.edu.threadpoolutil.workdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.ArrayList;

@Slf4j
@SpringBootTest
public class Demo1 {
    @Test
    public void test1() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId("1");
        demoEntity.setType(201);

        ArrayList<DemoEntity> list = new ArrayList<>();

        boolean anyMatch = list.stream().anyMatch(item -> item.getId().equals("3"));
        log.info("anyMatch = [{}]", anyMatch);
    }

    @Test
    public void encrypt() throws Exception {
        String content = "{\"cardId\":\"\",\"interfaceCode\":\"78\",\"memberId\":\"\",\"orderNo\":\"ERP1066392591832887296\",\"rechargeState\":1,\"rechargeStateDesc\":\"人工处理\",\"unionid\":\"\",\"userCode\":\"ohmdTt1wJsJ8X7KC4WOp_g8i5h-M\",\"userType\":1}";
        String password = "a98a4da0604e41eeb24b51e38c921a8a";

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
        byte[] result = cipher.doFinal(byteContent);
        String resultStr = Base64.encodeBase64String(result);

        log.info("encrypt = [{}]", resultStr);
    }

    private static SecretKeySpec getSecretKey(final String password) {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            // AES 要求密钥长度为 128
            kg.init(128, secureRandom);
            // 生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");// 转换为AES专用密钥
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
