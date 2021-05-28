package com.fuzy.example.demo;

import com.fuzy.example.domain.User;
import com.fuzy.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/20 17:31
 */
public class MybatisDemo {

    @Test
    public void test1() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        List<User> users = sqlSession.selectList("com.fuzy.example.mapper.UserMapper.selectUserList");

        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
        in.close();
    }

    @Test
    public void test2()throws IOException{
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
// 2.加载解析配置文件并获取SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
// 3.根据SqlSessionFactory对象获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
// 4.通过SqlSession中提供的 API方法来操作数据库
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.selectUserList();
        for (User user : list) {
            System.out.println(user);
        }
// 5.关闭会话
        sqlSession.close();
    }
}
