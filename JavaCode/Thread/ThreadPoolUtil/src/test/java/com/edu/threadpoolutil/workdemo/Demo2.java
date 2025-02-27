package com.edu.threadpoolutil.workdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootTest
public class Demo2 {
    @Test
    public void testMap() {
        DemoEntity entity1 = new DemoEntity();
        entity1.setId("1");
        entity1.setType(1);

        DemoEntity entity2 = new DemoEntity();
        entity2.setId("2");
        entity2.setType(2);

        DemoEntity entity3 = new DemoEntity();
        entity3.setId("3");
        entity3.setType(3);

        ArrayList<DemoEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        list.stream().collect(Collectors.toMap(DemoEntity::getId, ))
    }
}
