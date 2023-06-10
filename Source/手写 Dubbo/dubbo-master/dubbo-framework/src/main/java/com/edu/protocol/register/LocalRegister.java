package com.edu.protocol.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 将需要暴露的服务进行本地注册
 */
public class LocalRegister {
    /**
     * 接口名-接口实现类的键值对
     */
    private static Map<String, Class> map = new HashMap<>();

    /**
     * 保存 map
     *
     * @param interfaceName
     * @param implClass
     */
    public static void registry(String interfaceName, Class implClass) {
        map.put(interfaceName, implClass);
    }

    /**
     * 根据接口名获得其实现类
     * @param interfaceName
     * @return
     */
    public static Class get(String interfaceName) {
        return map.get(interfaceName);
    }
}
