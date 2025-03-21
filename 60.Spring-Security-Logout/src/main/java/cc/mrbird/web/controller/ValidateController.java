package cc.mrbird.web.controller;

import cc.mrbird.validate.code.ImageCode;
import cc.mrbird.validate.smscode.SmsCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ValidateController {

    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";
    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";

    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 36;
    private static final int CODE_LENGTH = 4;
    private static final int EXPIRE_IN = 60;

    // 使用SecurityContextRepository替代SessionStrategy
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode();

        // 构建完整验证码对象
        ImageCode codeInRedis = new ImageCode(imageCode.getImage(), imageCode.getCode(), imageCode.getExpireTime());

        // 使用安全上下文存储验证码
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new PreAuthenticatedAuthenticationToken(codeInRedis, null);
        context.setAuthentication(authentication);

        securityContextRepository.saveContext(context, request, response);

        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) {
        SmsCode smsCode = createSMSCode();

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new PreAuthenticatedAuthenticationToken(smsCode, null);
        context.setAuthentication(authentication);

        securityContextRepository.saveContext(context, request, response);

        System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
    }

    // 以下方法保持不变
    private SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code, 60);
    }

    private ImageCode createImageCode() {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        try {
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
            g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
            g.setColor(getRandColor(160, 200));
            for (int i = 0; i < 155; i++) {
                int x = ThreadLocalRandom.current().nextInt(IMAGE_WIDTH);
                int y = ThreadLocalRandom.current().nextInt(IMAGE_HEIGHT);
                int xl = ThreadLocalRandom.current().nextInt(12);
                int yl = ThreadLocalRandom.current().nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }

            StringBuilder sRand = new StringBuilder();
            for (int i = 0; i < CODE_LENGTH; i++) {
                String rand = String.valueOf(ThreadLocalRandom.current().nextInt(10));
                sRand.append(rand);
                g.setColor(new Color(20 + ThreadLocalRandom.current().nextInt(110),
                    20 + ThreadLocalRandom.current().nextInt(110),
                    20 + ThreadLocalRandom.current().nextInt(110)));
                g.drawString(rand, 13 * i + 6, 16);
            }

            return new ImageCode(image, sRand.toString(), EXPIRE_IN);
        } finally {
            g.dispose();
        }
    }

    private Color getRandColor(int fc, int bc) {
        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);
        int r = fc + ThreadLocalRandom.current().nextInt(bc - fc);
        int g = fc + ThreadLocalRandom.current().nextInt(bc - fc);
        int b = fc + ThreadLocalRandom.current().nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
