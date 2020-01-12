package xyz.tulling.hardware.dao;

import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * 系统配置dao
 */
public interface SystemDao {

    /**
     * 获取设置的迟到时间
     *
     * @return
     */
    @Select("select later_time from sys_later_time")
    public Date selLaterTime();

    /**
     * 获取卡号对应的员工号
     * @param cardId
     * @return
     */
    @Select("select employee_id from employee_card where card_id = #{cardId}")
    public String selEmpIdByCardId(String cardId);
}
