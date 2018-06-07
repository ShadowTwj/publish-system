package cn.tianwenjie.publish.system.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RuntimeTest {

  @Test
  public void RuntimeTest() throws IOException {
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec("pwd");
    InputStream inputStream = process.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
    String buf;
    while ((buf = bufferedReader.readLine()) != null) {
      System.out.println(buf);
    }
  }

  @Test
  public void rootPathTest() {
    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
//                            .replace("/classes/", "").replace("file:", "");
    System.out.println(rootPath);

    System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getDefaultPort());
    System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getFile());
    System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

  }

}
