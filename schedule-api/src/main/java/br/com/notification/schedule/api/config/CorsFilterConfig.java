package br.com.notification.schedule.api.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", exchange.getRequest().getHeaders().getOrigin());
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");

		if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
			exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS, PATCH");
			exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");

			exchange.getResponse().setStatusCode(HttpStatus.OK);
			return Mono.empty();
		} else {
			return chain.filter(exchange);
		}
	}
}
