package cn.lonelysnow.auth.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LonelySnow
 * @classname AuthenticationEntryPointImpl
 * @description 自定义认证失败
 * @date 2022/9/19 14:10
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        Map<String, Object> data = new HashMap<>();
        data.put("code", 100002);
        data.put("message", "error");
        data.put("data", "认证失败");
        data.put("timestamp", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        writer.write(new ObjectMapper().writeValueAsString(data));

        writer.flush();
        writer.close();

    }

}
