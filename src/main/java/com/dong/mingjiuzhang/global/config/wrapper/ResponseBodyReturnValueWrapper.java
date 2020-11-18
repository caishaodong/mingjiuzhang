package com.dong.mingjiuzhang.global.config.wrapper;

import com.dong.mingjiuzhang.global.ResponseResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-10-14 10:28
 * @Description
 **/
@Component
public class ResponseBodyReturnValueWrapper implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        decorateHandlers(handlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(handlers);
    }

    /**
     * 获取让自定义处理器生效应处于的位置
     *
     * @param handlers
     */
    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            // 自定义处理器需要放置在RequestResponseBodyMethodProcessor之前
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ReturnValueWrapperHandler returnValueWrapperHandler = new ReturnValueWrapperHandler(handler);
                handlers.set(handlers.indexOf(handler), returnValueWrapperHandler);
                break;
            }
        }
    }

    /**
     * 返回值包装类
     */
    class ReturnValueWrapperHandler implements HandlerMethodReturnValueHandler {
        private final HandlerMethodReturnValueHandler delegate;

        public ReturnValueWrapperHandler(HandlerMethodReturnValueHandler delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean supportsReturnType(MethodParameter methodParameter) {
            return delegate.supportsReturnType(methodParameter);
        }

        @Override
        public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
                throws Exception {
            // 包装返回值
            if (!(returnValue instanceof ResponseResult)) {
                returnValue = new ResponseResult(200, "成功", returnValue);
            }
            // 使用父类去处理，然后返回
            delegate.handleReturnValue(returnValue, methodParameter, mavContainer, webRequest);
        }
    }
}

