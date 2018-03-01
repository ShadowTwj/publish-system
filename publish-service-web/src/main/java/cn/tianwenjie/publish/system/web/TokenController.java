package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.HttpResult;
import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.mapper.UserMapper;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tianwj
 * @date 2018/2/28
 */
@RestController
@RequestMapping(value = "token")
@Slf4j
@SuppressWarnings("Duplicates")
public class TokenController {
  @Resource
  private UserMapper userMapper;

  @RequestMapping(value = "init", method = RequestMethod.GET)
  public HttpResult initToken(@RequestParam String account) {
    HttpResult httpResult = new HttpResult();

    if (Strings.isNullOrEmpty(account)) {
      httpResult.error();
      httpResult.setMessage("缺少userName");
      return httpResult;
    }

    try {
      String token = userMapper.getTokenByAccount(account);
      httpResult.success();
      httpResult.setData(token);
    } catch (Exception e) {
      log.error("initToken get token error, account={}", account, e);
      httpResult.error();
      httpResult.setMessage("获取token失败");
    }

    return httpResult;
  }

  @RequestMapping(value = "update", method = RequestMethod.POST)
  public HttpResult updateToken(@RequestBody String data) {
    HttpResult httpResult = new HttpResult();

    if (Strings.isNullOrEmpty(data)) {
      httpResult.error();
      httpResult.setMessage("缺少数据");
      return httpResult;
    }

    try {
      String account = JSONObject.parseObject(data).getString("account");
      String token = JSONObject.parseObject(data).getString("token");
      int result = userMapper.updateToken(account, token);
      if (result == 1) {
        httpResult.success();
        httpResult.setMessage("绑定成功");
      } else {
        httpResult.warning();
        httpResult.setMessage("更新token失败");
      }
    } catch (Exception e) {
      log.error("updateToken error, data={}", data, e);
      httpResult.error();
      httpResult.setMessage("绑定token失败");
    }

    return httpResult;
  }
}
