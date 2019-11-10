package com.trainingspringboot.shoppingcart.utils.filter;

import static com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant.TRACE_ID_HEADER;

import com.trainingspringboot.shoppingcart.utils.annotation.ServiceOperation;
import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MdcInitHandler implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		MDC.put(ShoppingCartConstant.TRACE_ID,
				Optional.ofNullable(request.getHeader(TRACE_ID_HEADER)).orElse(UUID.randomUUID().toString()));
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			ServiceOperation serviceOperationAnnotation = handlerMethod.getMethodAnnotation(ServiceOperation.class);
			MDC.put(ShoppingCartConstant.OPERATION, serviceOperationAnnotation.value());
			return true;
		}
		return true;
	}

}
