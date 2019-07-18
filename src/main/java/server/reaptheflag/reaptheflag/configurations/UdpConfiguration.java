package server.reaptheflag.reaptheflag.configurations;
/**
 * This configuration is bassically for the Udp service when starting up
 * */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import server.reaptheflag.reaptheflag.udpserver.UdpServer;
import server.reaptheflag.reaptheflag.udpserver.UdpStarter;

@Configuration
public class UdpConfiguration {

    private static int port = 9956;

    private UdpStarter udpStarter;

    private UdpServer udpServer;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private ApplicationContext context;
    @Bean
    public TaskExecutor UdpBeanExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public CommandLineRunner scheduleTheUdp(TaskExecutor UdpBeanExecutor) {
        return (String... args) -> {
            UdpBeanExecutor.execute(udpStarter);
        };
    }

    @Bean
    public UdpStarter udpStarter() {
        return new UdpStarter(udpServer);
    }

    @Autowired
    public void setUdpStarter(UdpStarter udpStarter) {
        this.udpStarter = udpStarter;
    }

    @Bean
    public UdpServer udpServer() {
        return new UdpServer(port);
    }

    @Autowired
    public void setUdpServer(UdpServer udpServer) {
        this.udpServer = udpServer;
    }
}
