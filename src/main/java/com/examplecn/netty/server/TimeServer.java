package com.examplecn.netty.server;

import com.examplecn.netty.server.handler.TimeServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

private int port;
	
	public TimeServer(int port) {
		
		this.port = port;
	}
	
	public void run() throws InterruptedException {
		
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(mainGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
			
			protected void initChannel(SocketChannel channel) throws Exception {
				
				channel.pipeline().addLast(new TimeServerHandler());
			};
		}).option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_KEEPALIVE, true);
		ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
		channelFuture.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) {
		
		try {
			new TimeServer(8080).run();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
}
