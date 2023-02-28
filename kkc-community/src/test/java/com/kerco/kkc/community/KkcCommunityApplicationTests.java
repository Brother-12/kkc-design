package com.kerco.kkc.community;

import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.to.User;
import com.kerco.kkc.community.feign.MemberFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KkcCommunityApplicationTests {

    static ThreadLocal<Integer> threadLocal;
    static {
        threadLocal = ThreadLocal.withInitial(() -> 1);
    }

    public static void main(String[] args) {
        Integer integer = threadLocal.get();
        Integer integer2 = threadLocal.get();
        System.out.println(integer);
        System.out.println(integer2);

    }

    @Autowired
    private MemberFeign memberFeign;

    @Test
    void contextLoads() {
        CommonResult<UserTo> userById = memberFeign.getUserByIdToWrite(100000L);
        System.out.println(userById);
    }

}
