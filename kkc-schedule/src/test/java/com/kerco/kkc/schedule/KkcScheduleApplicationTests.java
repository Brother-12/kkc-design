package com.kerco.kkc.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KkcScheduleApplicationTests {

    public static void main(String[] args){
        StringBuilder sb = new StringBuilder();
        sb.append("12");
        sb.append("3");
        String b = sb.toString();
        String a = "123";
        String c = new String("123");

        System.out.println(a.equals(b));
        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(a == c);
    }

    @Test
    void contextLoads() {
    }

}
