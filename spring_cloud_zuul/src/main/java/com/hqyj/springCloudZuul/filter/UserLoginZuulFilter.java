package com.hqyj.springCloudZuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * author  Jayoung
 * createDate  2020/9/2 0002 10:14
 * version 1.0
 */
@Component
public class UserLoginZuulFilter extends ZuulFilter {

    //过滤器类型
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器优先级
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否执行该过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器业务逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //token是自定义的
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token) || !"jayoung".equalsIgnoreCase(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            requestContext.setResponseBody("Token is null or error!");
        }
            return null;
    }
}
