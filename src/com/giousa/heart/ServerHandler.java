package com.giousa.heart;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, String arg1) throws Exception {
		
		System.out.println("ServerReceived：服务端获取信息："+arg1);
;		
//		arg0.channel().writeAndFlush("服务端发送消息到客户端："+arg1);
		
		arg0.writeAndFlush("服务端发送消息到客户端："+arg1);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent){
			
			IdleStateEvent event = (IdleStateEvent) evt;
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
            System.out.println("state:"+event.state()+"  "+simpleDateFormat.format(new Date()));
			
            if(event.state() == IdleState.ALL_IDLE){
            	ChannelFuture future = ctx.writeAndFlush("时间超时，退出游戏！");
            	
            	future.addListener(new ChannelFutureListener() {
					
					@Override
					public void operationComplete(ChannelFuture arg0) throws Exception {
						ctx.channel().close();
						
					}
				});
            	
            }
			
		}else{
			super.userEventTriggered(ctx, evt);
		}
	}
	
	

	
}
