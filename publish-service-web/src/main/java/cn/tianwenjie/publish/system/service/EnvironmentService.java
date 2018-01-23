package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Environment;
import cn.tianwenjie.publish.system.mapper.EnvironmentMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/1/23
 */
@Service
@Slf4j
public class EnvironmentService {
  @Resource
  private EnvironmentMapper environmentMapper;

  public List<Environment> getEnvironmentList() {
    return environmentMapper.findAll();
  }

  public int insertEnvironment(@NonNull Environment environment) {
    return environmentMapper.insertEnvironment(environment);
  }

  public int updateEnvironment(@NonNull Environment environment) {
    return environmentMapper.updateEnvironment(environment);
  }

  public int remove(@NonNull int id) {
    return environmentMapper.removeEnvironment(id);
  }
}
