package xyz.tulling.hardware.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import xyz.tulling.hardware.entry.RegisterInfo;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static Logger logger = Logger.getLogger(Utils.class);

    private static InputStream is;
    private static SqlSessionFactory factory;
    private static SqlSession sqlSession;

    static {
        String resource = "mybatis-config.xml";
        try {
            is = Resources.getResourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession();
        } catch (IOException e) {
            logger.info("Mybatis-config 配置文件加载错误");
        }
    }

    /**
     * 根据JSON生成对应的签到信息对象。
     *
     * @param json 解析对象样式：
     *             {
     *             "card_id": "00000000001",
     *             "hardware_id": "00000000001"
     *             }
     * @return
     */
    public static RegisterInfo getRegisterInfo(String json) {
        JSONObject obj = JSONObject.parseObject(json);
        // 解析JSON信息
        String cardId = obj.getString(Constant.CARD_ID);
        String hardwareId = obj.getString(Constant.HARDWARE_ID);

        // 根据卡号查询到员工号
        String employeeId = "00000000001";
        // 设置签到时间和状态
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime laterTime = LocalDateTime.now();
        int state = 0;      // 默认没有迟到
        if (now.isAfter(laterTime)) {
            state = 1;      // 设置状态为迟到
        }
        // 生成签到时间字符串
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = df.format(now);

        // 生成RegisterInfo
        RegisterInfo info = new RegisterInfo();
        info.setCarId(cardId);
        info.setEmployerId(employeeId);
        info.setHardwareId(hardwareId);
        info.setRegisterTime(nowStr);
        info.setState(state);
        return info;
    }

    public static <T> T getMapper(Class<T> tClass) {
        return sqlSession.getMapper(tClass);
    }
}
