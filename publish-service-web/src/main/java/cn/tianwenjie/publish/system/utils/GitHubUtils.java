package cn.tianwenjie.publish.system.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHReleaseBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author tianwj
 * @date 2018/2/7
 */
@Slf4j
public class GitHubUtils {
  /**
   * 可以设置默认token，用来不绑定token也可以拉取代码
   */
  private static final String TOKEN = "";
  private static final String GIT_ADDRESS = "https://github.com/";

  public static Set<String> getBranches(String token, String git) throws IOException {
    if (Strings.isNullOrEmpty(token)) {
      token = TOKEN;
    }
    GitHub gitHub = GitHub.connectUsingOAuth(token);
    String name = git.substring(GIT_ADDRESS.length(), git.indexOf(".git"));
    GHRepository ghRepository = gitHub.getRepository(name);
    return ghRepository.getBranches().keySet();
  }

  public static List<String> getTags(String token, String git) throws IOException {
    if (Strings.isNullOrEmpty(token)) {
      token = TOKEN;
    }
    GitHub gitHub = GitHub.connectUsingOAuth(token);
    String name = git.substring(GIT_ADDRESS.length(), git.indexOf(".git"));
    GHRepository ghRepository = gitHub.getRepository(name);
    List<GHTag> ghTags = ghRepository.listTags().asList();
    List<String> tags = Lists.newArrayList();
    ghTags.forEach(tag -> tags.add(tag.getName()));
    return tags;
  }

  public static void addTags(String token, String git, String branch, String tagName, String message) {

  }

  public static void main(String[] args) throws IOException {
    GitHub gitHub = GitHub.connectUsingOAuth("");
    GHRepository ghRepository = gitHub.getRepository("ShadowTwj/publish-demo");
    //    ghRepository.createRef("git/tags/tianwj","");
    GHReleaseBuilder release = ghRepository.createRelease("tianwj-test");
    GHRelease ghRelease = release.create();
    String targetCommitish = ghRelease.getTargetCommitish();
  }
}
