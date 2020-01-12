package xyz.tulling;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import xyz.tulling.hardware.dao.SystemDao;
import xyz.tulling.hardware.entry.RegisterInfo;
import xyz.tulling.hardware.exception.UnKnowCardException;
import xyz.tulling.hardware.util.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class MainTest {

    @Test
    public void test() throws UnKnowCardException {
        String json = "{\n" +
                "    \"card_id\": \"00000000001\",\n" +
                "    \"hardware_id\": \"00000000001\"\n" +
                "}";
        RegisterInfo registerInfo = Utils.getRegisterInfo(json);
        System.out.println(registerInfo);
    }

    String resource = "mybatis-config.xml";

    @Test
    public void test1() throws IOException {
        SystemDao sysDao = Utils.getMapper(SystemDao.class);
        System.out.println(sysDao.selLaterTime());

    }
}
