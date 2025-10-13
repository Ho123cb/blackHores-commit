package com.heima.appGateway.filter;


import com.heima.appGateway.utils.common.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 实现鉴权功能
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //1. 判断是否包含login，包含直接放行
        String path = request.getURI().getPath();
        if(path.contains("login")){
            return chain.filter(exchange);
        }

        //2. 判断是否包含token
        String token = request.getHeaders().getFirst("token");
        if(StringUtils.isBlank(token)){
            //返回错误信息
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setComplete();
        }

        //3.判断token是否有效
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            //是否是过期
            int result = AppJwtUtil.verifyToken(claimsBody);
            if(result == 1 || result  == 2){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //4.放行
        return chain.filter(exchange);
    }
}
