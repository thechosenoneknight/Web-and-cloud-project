package com.example.springboot;

import com.example.springboot.entity.DormBuild;
import com.example.springboot.entity.DormRoom;
import com.example.springboot.mapper.DormBuildMapper;
import com.example.springboot.mapper.DormRoomMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class SpringbootApplicationTests {

    @Resource
    DormRoomMapper dormRoomMapper;

    @Test
    void contextLoads() {
        DormRoom dormRoom = new DormRoom();
        dormRoom.setDormBuildId(8);


    }

}
