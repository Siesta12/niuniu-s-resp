package com.nlt.myapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyApplicationTests {

    @Test
    void contextLoads() {
        String s = "/uploads/ca3f9e67-f2e2-414f-bd78-882f2838ff72_image.jpg";
        String[] a = s.split("/");
        System.out.println(a[a.length - 1]);
    }

}
