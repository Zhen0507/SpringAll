package cc.mrbird.validate.code;

import cc.mrbird.validate.code.ImageCode;
import cc.mrbird.validate.code.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    // 认证失败处理器（自动注入）
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 安全上下文存储库（替代旧版SessionStrategy）
    private final SecurityContextRepository securityContextRepository =
        new HttpSessionSecurityContextRepository();

    // 核心过滤方法
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 仅拦截POST类型的/login请求
        if (isLoginRequest(request)) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                // 验证失败时处理
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        // 验证通过，继续过滤器链
        filterChain.doFilter(request, response);
    }

    // 判断是否为登录请求
    private boolean isLoginRequest(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase("/login", request.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", request.getMethod());
    }

    // 验证码校验核心方法
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        // 创建请求响应包装器（适配SecurityContextRepository接口）
        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(
            servletWebRequest.getRequest(),
            servletWebRequest.getResponse()
        );

        // 从存储库加载安全上下文
        SecurityContext context = securityContextRepository.loadContext(holder);

        try {
            // 从上下文中提取验证码对象
            ImageCode codeInStorage = Optional.ofNullable(context)
                .map(SecurityContext::getAuthentication)  // 获取认证信息
                .filter(auth -> auth.getPrincipal() instanceof ImageCode)  // 验证主体类型
                .map(auth -> (ImageCode) auth.getPrincipal())  // 类型转换
                .orElse(null);  // 无验证码时返回null

            // 从请求参数获取用户输入的验证码
            String codeInRequest = ServletRequestUtils.getRequiredStringParameter(
                servletWebRequest.getRequest(),
                "imageCode"
            ).trim();

            // 执行验证逻辑
            validateCodeState(codeInStorage, codeInRequest);

            // 验证通过后清除安全上下文
            clearSecurityContext(holder);
        } catch (ServletRequestBindingException ex) {
            // 参数绑定异常处理
            throw new ValidateCodeException("验证码参数解析失败");
        }
    }

    // 验证码状态检查（分离业务逻辑）
    private void validateCodeState(ImageCode storedCode, String inputCode) {
        // 空值检查
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        // 存在性检查
        if (storedCode == null) {
            throw new ValidateCodeException("验证码未生成或已失效");
        }
        // 有效期检查
        if (storedCode.isExpire()) {
            throw new ValidateCodeException("验证码已过期");
        }
        // 匹配性检查
        if (!storedCode.getCode().equalsIgnoreCase(inputCode)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    // 清理安全上下文
    private void clearSecurityContext(HttpRequestResponseHolder holder) {
        // 创建空上下文
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        // 保存空上下文到存储库
        securityContextRepository.saveContext(emptyContext, holder.getRequest(), holder.getResponse());
    }
}
