package com.examplecn.netty.client.handler;

import java.util.Date;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf byteBuf = (ByteBuf) msg;
		long currentTime = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
		String formatDateTime = DateUtil.formatDateTime(new Date(currentTime));
		System.out.println("当前时间是：" + formatDateTime);
		ctx.close();
		byteBuf.release();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		cause.printStackTrace();
		ctx.close();
	}

}