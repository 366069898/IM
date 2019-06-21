package com.ccex.handler;


import com.ccex.common.TPMSConsts;
import com.ccex.service.MsgDecoder;
import com.ccex.util.JT808ProtocolUtils;
import com.ccex.vo.PackageData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    private final MsgDecoder decoder;
    private JT808ProtocolUtils protocolUtils = new JT808ProtocolUtils();

    public FirstServerHandler() {
        this.decoder = new MsgDecoder();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端连接" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端断开" + ctx.channel().remoteAddress());
        ctx.channel().close();
        // ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        if (buf.readableBytes() <= 0) {
            // ReferenceCountUtil.safeRelease(msg);
            return;
        }

        byte[] bs = new byte[buf.readableBytes()];
        buf.readBytes(bs);

        bs = this.protocolUtils.doEscape4Receive(bs, 0, bs.length);
        // 字节数据转换为针对于808消息结构的实体类
        PackageData pkg = this.decoder.bytes2PackageData(bs);

        //处理客户端消息
        this.processClientMsg(pkg);

    }

    private void processClientMsg(PackageData packageData){
        if(packageData.getMsgHeader().getMsgId() == TPMSConsts.cmd_common_resp){
            System.err.println(TPMSConsts.cmd_common_resp);
        }
    }

//    private boolean checkLogin(LoginRequestPacket loginRequestPacket){
//        return true;
//    }

}
