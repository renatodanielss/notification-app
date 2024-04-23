package br.com.notification.schedule.api.config;

import br.com.notification.schedule.api.config.annotation.SortRequestParam;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SortHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SortRequestParam.class) != null;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String[] sorts = webRequest.getParameterValues("sort");
        if (sorts != null && sorts.length > 0) {
            return Sort.by(Arrays.stream(sorts)
                    .map(s -> {
                        String[] sortSplit = s.split(":");
                        String sortField = sortSplit[0];
                        Direction direction = sortSplit.length > 1 && "desc".equalsIgnoreCase(sortSplit[1]) ?
                                Direction.DESC : Direction.ASC;
                        return new Order(direction, sortField);
                    })
                    .collect(Collectors.toList()));
        }
        return Sort.unsorted();
    }
}
