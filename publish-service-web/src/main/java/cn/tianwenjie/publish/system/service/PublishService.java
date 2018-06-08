package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Environment;
import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.entity.PublishConf;
import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.entity.PublishLog;
import cn.tianwenjie.publish.system.mapper.EnvironmentMapper;
import cn.tianwenjie.publish.system.mapper.ProjectMapper;
import cn.tianwenjie.publish.system.mapper.PublishConfMapper;
import cn.tianwenjie.publish.system.mapper.PublishHistoryMapper;
import cn.tianwenjie.publish.system.mapper.PublishLogMapper;
import cn.tianwenjie.publish.system.utils.GitHubUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author tianwj
 * @date 2018/2/6
 */
@Service
@Slf4j
public class PublishService {
  @Resource
  private PublishHistoryMapper publishHistoryMapper;
  @Resource
  private UserService userService;
  @Resource
  private PublishConfMapper publishConfMapper;
  @Resource
  private EnvironmentMapper environmentMapper;
  @Resource
  private ProjectMapper projectMapper;
  @Resource
  private PublishLogMapper publishLogMapper;

  private static final String CODE_PATH = "/usr/local/project";
  private static final String DEPLOY_URL = "/usr/local/tomcat/webapps";

  @Async
  public void publish(@NonNull PublishHistory publishHistory) {
    //开始log
    PublishLog startPublishLog = PublishLog.builder()
                                           .projectId(publishHistory.getProjectId())
                                           .publishConfId(publishHistory.getPublishConfId())
                                           .publishHistoryId(publishHistory.getId())
                                           .stepName("开始")
                                           .stepOrder(1)
                                           .status(0)
                                           .remark(publishHistory.getRemark())
                                           .createUser(publishHistory.getCreateUser())
                                           .createTime(new Date())
                                           .updateUser(publishHistory.getUpdateUser())
                                           .updateTime(new Date())
                                           .build();
    publishLogMapper.insert(startPublishLog);

    //todo:修改耗时
    if (1 == pullProject(publishHistory)) {
      publishHistoryMapper.updateById(publishHistory.getId(), -1);
      return;
    }
    if (1 == compile(publishHistory)) {
      publishHistoryMapper.updateById(publishHistory.getId(), -1);
      return;
    }
    if (1 == deploy(publishHistory)) {
      publishHistoryMapper.updateById(publishHistory.getId(), -1);
      return;
    }
    //完成log
    Environment environment = environmentMapper.findById(publishHistory.getEnvironmentId());
    PublishConf publishConf = publishConfMapper.findById(publishHistory.getPublishConfId());
    String url = "http://"+ environment.getIp() + ":" + publishConf.getPorts() + "/" + publishConf.getTomcatContextPath().replaceAll("/", "");
    PublishLog finishPublishLog = PublishLog.builder()
                                            .projectId(publishHistory.getProjectId())
                                            .publishConfId(publishHistory.getPublishConfId())
                                            .publishHistoryId(publishHistory.getId())
                                            .stepName("完成")
                                            .stepOrder(5)
                                            .stepLog("<a href=\"" + url + "\" target=\"_blank\">" + "一键到" + url + "</a>")
                                            .status(0)
                                            .remark(publishHistory.getRemark())
                                            .createUser(publishHistory.getCreateUser())
                                            .createTime(new Date())
                                            .updateUser(publishHistory.getUpdateUser())
                                            .updateTime(new Date())
                                            .build();
    publishLogMapper.insert(finishPublishLog);

    publishHistoryMapper.updateById(publishHistory.getId(), 0);
  }

  /**
   * 获取代码
   */
  public int pullProject(PublishHistory publishHistory) {
    PublishLog publishLog = PublishLog.builder()
                                      .projectId(publishHistory.getProjectId())
                                      .publishConfId(publishHistory.getPublishConfId())
                                      .publishHistoryId(publishHistory.getId())
                                      .stepName("获取代码")
                                      .stepOrder(2)
                                      .remark(publishHistory.getRemark())
                                      .createUser(publishHistory.getCreateUser())
                                      .createTime(new Date())
                                      .updateUser(publishHistory.getUpdateUser())
                                      .updateTime(new Date())
                                      .build();
    List<String> resultList = Lists.newArrayList();
    try {
      Project project = projectMapper.findById(publishHistory.getProjectId());

      String rootPath = getRootPath();
      String applicationName = project.getUniqueName();
      String git = project.getGit();
      String gitName = git.substring(git.lastIndexOf("/") + 1, git.lastIndexOf("."));
      String branch = publishHistory.getBranch();
      boolean isTag = getTag(userService.getToken(publishHistory.getCreateUser()), git, branch);

      String shell = rootPath + "/shell/pull.sh";
      String cmd = shell + " " + rootPath + " " + gitName + " " + applicationName + " " + git + " " + branch + " " + CODE_PATH + " " + isTag;
      Runtime.getRuntime().exec("chmod +x " + shell);
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String result;
      while ((result = input.readLine()) != null) {
        resultList.add(result);
      }
      input.close();
      publishLog.setStatus(0);
    } catch (Exception e) {
      log.error("获取代码失败", e);
      publishLog.setStatus(1);
    } finally {
      publishLog.setStepLog(Joiner.on("<br/>").skipNulls().join(resultList));
      publishLogMapper.insert(publishLog);
    }

    return publishLog.getStatus();
  }

  /**
   * 判断分支是否是tag
   */
  private boolean getTag(String token, String git, String branch) throws IOException {
    List<String> tagList = GitHubUtils.getTags(token, git);
    return tagList.contains(branch);
  }

  /**
   * 获取rootPath
   */
  private String getRootPath() {
    return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath().replace("/classes/", "");
    //    return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath().replace("/test-classes/", "");
  }

  /**
   * 编译代码
   */
  public int compile(PublishHistory publishHistory) {
    PublishLog publishLog = PublishLog.builder()
                                      .projectId(publishHistory.getProjectId())
                                      .publishConfId(publishHistory.getPublishConfId())
                                      .publishHistoryId(publishHistory.getId())
                                      .stepName("编译代码")
                                      .stepOrder(3)
                                      .remark(publishHistory.getRemark())
                                      .createUser(publishHistory.getCreateUser())
                                      .createTime(new Date())
                                      .updateUser(publishHistory.getUpdateUser())
                                      .updateTime(new Date())
                                      .build();

    List<String> resultList = Lists.newArrayList();
    try {
      String applicationName = projectMapper.findById(publishHistory.getProjectId()).getUniqueName();
      String rootPath = getRootPath();
      //判断是否是模块
      String moduleName = projectMapper.findById(publishHistory.getProjectId()).getModule();
      boolean isModule = !StringUtils.isBlank(moduleName);
      String shell = rootPath + "/shell/compile.sh";
      String cmd = shell + " " + rootPath + " " + applicationName + " " + CODE_PATH + " " + isModule + " " + moduleName;

      Runtime.getRuntime().exec("chmod +x " + shell);
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String result;
      while ((result = input.readLine()) != null) {
        resultList.add(result);
      }
      input.close();
      publishLog.setStatus(0);
    } catch (Exception e) {
      log.error("编译代码失败", e);
      publishLog.setStatus(1);
    } finally {
      publishLog.setStepLog(Joiner.on("<br/>").skipNulls().join(resultList));
      publishLogMapper.insert(publishLog);
    }

    return publishLog.getStatus();
  }

  /**
   * 部署项目
   */
  public int deploy(PublishHistory publishHistory) {
    PublishConf publishConf = publishConfMapper.findById(publishHistory.getPublishConfId());

    PublishLog publishLog = PublishLog.builder()
                                      .projectId(publishHistory.getProjectId())
                                      .publishConfId(publishHistory.getPublishConfId())
                                      .publishHistoryId(publishHistory.getId())
                                      .stepName("部署项目")
                                      .stepOrder(4)
                                      .remark(publishHistory.getRemark())
                                      .createUser(publishHistory.getCreateUser())
                                      .createTime(new Date())
                                      .updateUser(publishHistory.getUpdateUser())
                                      .updateTime(new Date())
                                      .build();

    List<String> resultList = Lists.newArrayList();
    try {
      String rootPath = getRootPath();
      String contextPath = publishConf.getTomcatContextPath().replaceAll("/", "");
      Project project = projectMapper.findById(publishHistory.getProjectId());
      String applicationName = project.getUniqueName();
      String gitName = project.getGit().substring(project.getGit().lastIndexOf("/") + 1, project.getGit().lastIndexOf("."));

      //判断是否是模块
      String module;
      boolean isModule;
      if (StringUtils.isBlank(project.getModule())) {
        module = applicationName;
        isModule = false;
      } else {
        module = project.getModule();
        isModule = true;
      }

      Environment environment = environmentMapper.findById(publishHistory.getEnvironmentId());
      String userName = environment.getUserName();
      String ip = environment.getIp();
      String password = environment.getPassword();

      String shell = rootPath + "/shell/deploy.sh";
      String rsyncShell = rootPath + "/shell/rsync.sh";
      String cmd =
        shell + " " + rootPath + " " + contextPath + " " + applicationName + " " + gitName + " " + module + " " + isModule + " " + CODE_PATH + " " + userName +
          " " + ip + " " + DEPLOY_URL + " " + password;

      Runtime.getRuntime().exec("chmod +x " + shell);
      Runtime.getRuntime().exec("chmod +x " + rsyncShell);
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String result;
      while ((result = input.readLine()) != null) {
        resultList.add(result);
      }
      input.close();
      publishLog.setStatus(0);
    } catch (Exception e) {
      log.error("发布项目失败", e);
      publishLog.setStatus(1);
    } finally {
      publishLog.setStepLog(Joiner.on("<br/>").skipNulls().join(resultList));
      publishLogMapper.insert(publishLog);
    }

    return publishLog.getStatus();
  }

}
