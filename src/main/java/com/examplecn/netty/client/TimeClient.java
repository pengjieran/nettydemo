package com.examplecn.netty.client;

import com.examplecn.netty.client.handler.TimeClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	
	private Bootstrap bootstrap;
	
	private EventLoopGroup workGroup;
	
	public static void main(String[] args) {
		
		TimeClient timeClient = new TimeClient();
		timeClient.init();
		while (true) {
			
			try {
				timeClient.connect("127.0.0.1", 8080);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				timeClient.close();
			}
		}
	}
	
	public void init() {
		
		workGroup = new NioEventLoopGroup();
		
		bootstrap = new Bootstrap();
		bootstrap.group(workGroup);
		bootstrap.channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				
				ch.pipeline().addLast(new TimeClientHandler());
			}
		});
	}
	
	public void close() {
		
		workGroup.shutdownGracefully();
	}
	
	public void connect(String host, int port) throws InterruptedException {
		
		ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
		channelFuture.channel().closeFuture().sync();
	}

}