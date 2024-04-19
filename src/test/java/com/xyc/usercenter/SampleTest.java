// package com.xyc.usercenter;
//
// import com.baomidou.mybatisplus.core.toolkit.Assert;
// import com.xyc.usercenter.mapper.UserMapper;
// import com.xyc.usercenter.model.po.User;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import java.util.List;
//
// @SpringBootTest(classes = UserCenterApplication.class)
// public class SampleTest {
//
//     @Autowired
//     private UserMapper userMapper;
//
//     @Test
//     public void testSelect() {
//         System.out.println(("----- selectAll method test ------"));
//         // List<User> userList = userMapper.selectList(null);
//         Assert.isTrue(5 == userList.size(), "");
//         userList.forEach(System.out::println);
//     }
//
// }
