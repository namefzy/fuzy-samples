package com.fuzy.example.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @ClassName ElapsedFilter
 * @Description TODO
 * @Author fuzy
 * @Date 2021/5/16 20:14
 * @Version 1.0
 */
@Component
public class ElapsedFilter implements GlobalFilter, Order {

    //基于slf4j.Logger实现日志输出
    private static final Logger logger = LoggerFactory.getLogger(ElapsedFilter.class);
    //起始时间属性名
    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());

        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
            if(startTime!=null){
                logger.info(exchange.getRequest().getRemoteAddress()+"|"+
                        exchange.getRequest().getPath()+
                        "|cost"+(System.currentTimeMillis()-startTime)+"ms");
            }
        }));

    }

    @Override
    public int value() {
        return 0;
    }
}
