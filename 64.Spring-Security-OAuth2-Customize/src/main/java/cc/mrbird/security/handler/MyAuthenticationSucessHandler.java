package cc.mrbird.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisteredClientRepository registeredClientRepository;
    
    @Autowired
    private OAuth2TokenGenerator<?> tokenGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 1. 从请求头中获取 ClientId
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new OAuth2AuthenticationException("请求头中无client信息");
        }

        String[] tokens = this.extractAndDecodeHeader(header, request);
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 2. 通过 RegisteredClientRepository 获取 RegisteredClient
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

        // 3. 校验 ClientId和 ClientSecret的正确性
        if (registeredClient == null) {
            throw new OAuth2AuthenticationException("clientId:" + clientId + "对应的信息不存在");
        } else if (!StringUtils.equals(registeredClient.getClientSecret(), clientSecret)) {
            throw new OAuth2AuthenticationException("clientSecret不正确");
        }

        // 由于 OAuth2 API 有重大变化，这里需要根据实际情况重新实现token生成逻辑
        // 这里仅提供示例框架，实际代码需要根据新版本API详细调整
        HashMap<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("user_name", authentication.getName());
        
        // 返回登录成功信息
        log.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(additionalParameters));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
