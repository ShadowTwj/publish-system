package cn.tianwenjie.publish.system.mapper;

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
    Process process = runtime.exec("ls /Users/tianwj");
    InputStream inputStream = process.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
    String buf = null;
    while ((buf = bufferedReader.readLine()) != null) {
      System.out.println(buf);
    }
  }

}
