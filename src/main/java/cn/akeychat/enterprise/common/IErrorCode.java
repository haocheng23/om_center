package cn.akeychat.enterprise.common;

/**
 * @Description:    封装API的错误码
 * @Author:         haocheng
 * @Date:           2021-05-18 15:14:54
 * @Remark:         备注
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
