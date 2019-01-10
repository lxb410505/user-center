package com.hypersmart.bpmModel;

import com.hypersmart.usercenter.mapper.SysLogsMapper;
import com.hypersmart.usercenter.model.SysLogs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpmModelApplicationTests {

    @Resource
    SysLogsMapper sysLogsDao;

    @Test
    public void contextLoads() {

        List<SysLogs> sysLogsList = sysLogsDao.selectAll();

        System.out.println(sysLogsList.size());

    }

}
