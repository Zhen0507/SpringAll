package com.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.pojo.ResponseBo;
import com.springboot.pojo.User;
import com.springboot.util.SHA256Utils; // 使用更安全的加密算法

@Controller
public class LoginController {

    /**
     * 处理用户登录请求
     *
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return ResponseBo 包含登录结果的响应对象，成功返回ok，失败返回错误信息
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(String username, String password) {
        // 使用SHA-256对密码进行加密
        password = SHA256Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            // 尝试登录
            subject.login(token);
            // 强制生成新的Session ID，防止Session固定攻击
            subject.getSession().stop();
            subject.getSession(true);
            return ResponseBo.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            // 处理用户名或密码错误的情况
            return ResponseBo.error("用户名或密码错误");
        } catch (AuthenticationException e) {
            // 处理认证失败的情况
            return ResponseBo.error("认证失败！");
        } catch (Exception e) {
            // 处理其他异常情况
            return ResponseBo.error("系统异常，请稍后重试");
        }
    }

    /**
     * 重定向到首页
     *
     * @return String 重定向到"/index"路径
     */
    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    /**
     * 显示首页
     *
     * @param model 用于传递数据到视图的Model对象
     * @return String 返回视图名称"index"
     */
    @RequestMapping("/index")
    public String index(Model model) {
        // 获取当前登录用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 将用户信息添加到Model中
        model.addAttribute("user", user);
        return "index";
    }
}
