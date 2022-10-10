package cn.lonelysnow.auth.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * @classname AccessDeniedHandlerImpl
 * @description 自定义授权失败异常(无权限的情况)
 * @date 2022/9/19 14:08
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        Map<String, Object> data = new HashMap<>();
        data.put("code", 100002);
        data.put("message", "无访问权限");
        data.put("data", "");
        data.put("timestamp", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        writer.write(new ObjectMapper().writeValueAsString(data));

        writer.flush();
        writer.close();
    }

}
