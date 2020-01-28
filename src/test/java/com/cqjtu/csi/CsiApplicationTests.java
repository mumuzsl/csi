package com.cqjtu.csi;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.repository.base.BaseRepository;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.service.impl.DeptServiceImpl;
import com.cqjtu.csi.utils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CsiApplicationTests {

    @Test
    void contextLoads() {
        Date from = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        System.out.println(from);
    }

    @Test
    void aa() {

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zhao");

        Object o = JSON.toJSON(map);

        Dept d = BeanUtils.transformFrom(o, Dept.class);

        System.out.println(d.toString());
    }

    @Test
    void bb() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhao");

        Dept dept = BeanUtil.mapToBean(map, Dept.class, true);

        System.out.println(dept.toString());
    }

    @Test
    void cc() {

        DeptServiceImpl deptService = new DeptServiceImpl(null);

        Map<String, String> map = new HashMap<>();
        map.put("name", "人事部");

        Page d = deptService.search(map, null);

        d.get().forEach(o -> {
            System.out.println(o.toString());
        });

    }
}
