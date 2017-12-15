package cn.tianwenjie.publish.system.mapper;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.OutputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JschTest {
  private String host;
  private String username;
  private int port;
  private String pwd;

  @Before
  public void init() {
    host = "47.94.134.165";
    username = "root";
    port = 22;
    pwd = "twj646371601.";
  }

  /**
   * jsch学习
   *
   * @throws JSchException
   * @throws IOException
   */
  @Test
  public void jschTest() throws JSchException, IOException {
    JSch jsch = new JSch();
    //连接服务器
    Session session = jsch.getSession(host, username, port);
    //设置登陆主机的密码
    session.setPassword(pwd);
    //设置登陆超时时间
//    session.connect(30000);
    //设置第一次登陆的时候提示
//    session.setConfig("StrictHostKeyChecking", "no")x;
    //获得通道
    /*
    实现远程命令操作我们需要创建ChannelExec或ChannelShell对象
    实现文件上传下载我们需要实现ChannelSftp对象
     */
    Channel channel = session.openChannel("exec");
    //强转为shell通道
    ChannelExec channelExec = (ChannelExec) channel;

    channelExec.setCommand("pwd");

    channelExec.connect();

    System.out.println("------");
    System.out.println(channelExec.getInputStream());
    System.out.println("------");
  }
}
