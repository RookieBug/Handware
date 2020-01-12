package xyz.tulling.hardware.dao;


import org.apache.ibatis.annotations.Insert;
import xyz.tulling.hardware.entry.RegisterInfo;

public interface RegisterInfoDao {

    @Insert("insert into register_info values(default,'${cardId}','${employeeId}','${hardwareId}','${registerTime}','${desc}',${state})")
    Integer insRegisterInfo(RegisterInfo registerInfo);
}
