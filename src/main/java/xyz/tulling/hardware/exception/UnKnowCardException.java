package xyz.tulling.hardware.exception;


/**
 * 位置卡号异常
 */
public class UnKnowCardException extends Exception {
    String msg;

    public UnKnowCardException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
