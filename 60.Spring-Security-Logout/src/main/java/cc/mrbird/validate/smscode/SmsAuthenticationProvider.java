package cc.mrbird.validate.smscode;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 短信验证码认证提供者
 * 实现Spring Security的AuthenticationProvider接口
 * 用于处理基于手机短信验证码的认证逻辑
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    // 使用Spring Security标准的UserDetailsService接口
    private UserDetailsService userDetailsService;

    /**
     * 构造函数注入用户详情服务
     * 允许框架自动注入
     */
    public SmsAuthenticationProvider() {
    }
    
    /**
     * 构造函数注入用户详情服务
     * @param userDetailsService 用户详情服务实现
     */
    public SmsAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 核心认证方法
     * @param authentication 待认证的Authentication对象
     * @return 包含用户权限的认证令牌
     * @throws AuthenticationException 认证失败时抛出异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 将传入的Authentication对象转换为短信认证令牌
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        // 从认证令牌中获取手机号码（即principal）
        String mobile = (String) authenticationToken.getPrincipal();

        // 通过用户详情服务加载用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);

        // 用户不存在时抛出认证异常
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("未找到与该手机号对应的用户");
        }

        // 创建已认证的令牌对象
        SmsAuthenticationToken authenticatedToken =
            new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());

        // 保留原始请求的详细信息（如IP地址、SessionID等）
        authenticatedToken.setDetails(authenticationToken.getDetails());

        return authenticatedToken;
    }

    /**
     * 设置UserDetailsService
     * @param userDetailsService 用户详情服务
     */
    public void setUserDetailService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 判断当前提供者是否支持指定的认证类型
     * @param authenticationType 待检查的认证类型Class
     * @return true表示支持该类型认证
     */
    @Override
    public boolean supports(Class<?> authenticationType) {
        return SmsAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}
