package cn.lonelysnow.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author LonelySnow
 * @classname AuthorFilter
 * @description 认证拦截过滤器
 * @date 2022/9/2 16:41
 */
@Component
public class AuthorFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器核心方法
     * @author LonelySnow
     * @param exchange
     * @param chain
     * @result reactor.core.publisher.Mono<java.lang.Void>
     * @date 2022/9/2 17:30
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求头
        ServerHttpRequest request = exchange.getRequest();

        return null;
    }

    /**
     * 过滤器优先级，数值越小，优先级越高
     * @author LonelySnow
     * @param
     * @result int
     * @date 2022/9/2 17:29
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
