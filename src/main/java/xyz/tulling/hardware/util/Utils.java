package xyz.tulling.hardware.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import xyz.tulling.hardware.dao.SystemDao;
import xyz.tulling.hardware.entry.RegisterInfo;
import xyz.tulling.hardware.exception.UnKnowCardException;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    public static Logger logger = Logger.getLogger(Utils.class);


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
    public static RegisterInfo getRegisterInfo(String json) throws UnKnowCardException {
        SystemDao sysDao = getMapper(SystemDao.class);

        JSONObject obj = JSONObject.parseObject(json);
        // 解析JSON信息
        String cardId = obj.getString(Constant.CARD_ID);
        String hardwareId = obj.getString(Constant.HARDWARE_ID);

        // 根据卡号查询到员工号
        String employeeId = sysDao.selEmpIdByCardId(cardId);
        if (employeeId == null || "".equals(employeeId)) {
            throw new UnKnowCardException("未知卡号：" + cardId);
        }
        // 签到时间和状态
        LocalDateTime now = LocalDateTime.now();
        Date date = sysDao.selLaterTime();
        LocalDateTime dateTime = date2LocalDateTime(date);
        // 生成迟到时间
        LocalDateTime laterTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
        int state = 0;      // 默认没有迟到
        if (now.isAfter(laterTime)) {
            state = 1;      // 设置状态为迟到
        }

        // 生成签到时间字符串
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = df.format(now);

        // 生成RegisterInfo
        RegisterInfo info = new RegisterInfo();
        info.setCardId(cardId);
        info.setEmployeeId(employeeId);
        info.setHardwareId(hardwareId);
        info.setRegisterTime(nowStr);
        info.setState(state);
        info.setDesc(null);
        return info;
    }

    public static <T> T getMapper(Class<T> tClass) {
        return sqlSession.getMapper(tClass);
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    private static InputStream is = null;
    private static SqlSessionFactory factory = null;
    private static SqlSession sqlSession = null;

    static {
        String resource = "mybatis-config.xml";
        try {
            is = Resources.getResourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession(true);
        } catch (IOException e) {
            logger.info("Mybatis-config 配置文件加载错误");
        }
    }
}
