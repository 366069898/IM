package com.ccex.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.Arrays;

@Data
public class PackageData {

    /**
     * 16byte 消息头
     */
    protected MsgHeader msgHeader;

    // 消息体字节数组
    @JSONField(serialize=false)
    protected byte[] msgBodyBytes;

    /**
     * 校验码 1byte
     */
    protected int checkSum;

    @JSONField(serialize=false)
    protected Channel channel;


    @Data
    public static class MsgHeader {
        // 消息ID byte[0-2]
        protected int msgId;

        // 消息总长度 byte[2-4]
        protected int msgBodyLength;

        // 消息体属性 btye[4-6]
        protected int msgBodyAttribute;

//        protected int

        /////// ========消息体属性
        // byte[2-3]
        protected int msgBodyPropsField;
        // 数据加密方式
        protected int encryptionType;
        // 是否分包,true==>有消息包封装项
        protected boolean hasSubPackage;
        // 保留位[14-15]
        protected String reservedBit;
        /////// ========消息体属性

        // 终端手机号
        protected String terminalPhone;
        // 流水号
        protected int flowId;

        //////// =====消息包封装项
        // byte[12-15]
        protected int packageInfoField;
        // 消息包总数(word(16))
        protected long totalSubPackage;
        // 包序号(word(16))这次发送的这个消息包是分包中的第几个消息包, 从 1 开始
        protected long subPackageSeq;
        //////// =====消息包封装项

    }
}
