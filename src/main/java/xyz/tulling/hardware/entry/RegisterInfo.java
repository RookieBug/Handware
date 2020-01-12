package xyz.tulling.hardware.entry;

import lombok.Data;

@Data
public class RegisterInfo {
    private String carId;           // 卡号
    private String employerId;      // 员工号
    private String registerTime;    // 签到时间
    private String desc;            // 备注
    private int state;          // 状态（迟到1，未迟到0）
}
