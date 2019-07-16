package server.reaptheflag.reaptheflag.udpserver;
/**
 * server initialization, use SO_BROADCAST udp channel
 * */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import server.reaptheflag.reaptheflag.udpserver.Handler.PacketHandler;

import java.net.InetAddress;

public class UdpServer {
    private static Logger LOGGER = LogManager.getLogger(UdpServer.class);
    private ApplicationContext context;
    private int port;
    public UdpServer(int port, ApplicationContext context) {
        this.port = port;
        this.context = context;
    }
    public void run() throws Exception {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap programBootStrap = new Bootstrap();
        programBootStrap.group(group).
                channel(NioDatagramChannel.class).
                option(ChannelOption.SO_BROADCAST, true).
                handler(new ChannelInitializer<NioDatagramChannel>() {

                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        ChannelPipeline pipe = nioDatagramChannel.pipeline();
                        pipe.addLast("decoder", new ByteArrayDecoder());
                        pipe.addLast("encoder", new ByteArrayEncoder());
                        pipe.addLast("clientHandler", (PacketHandler) context.getBean("basicPacketHandler"));
                    }
                }); // packet handler is the bootstrap of the processing program

        InetAddress address = InetAddress.getLocalHost();
        LOGGER.info("udp server initialized!" + address);
        programBootStrap.bind(address, port).sync().channel().closeFuture().await();
    }
}