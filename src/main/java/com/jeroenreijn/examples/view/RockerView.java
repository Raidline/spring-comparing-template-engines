package com.jeroenreijn.examples.view;

import com.fizzed.rocker.Rocker;
import com.jeroenreijn.examples.model.Presentation;
import com.jeroenreijn.examples.view.response.ReactiveResponseWriter;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RockerView extends AbstractView {
	
	private final ReactiveResponseWriter<Presentation> responseWriter;
	
	public RockerView(ReactiveResponseWriter<Presentation> responseWriter) {
		this.responseWriter = responseWriter;
	}
	
	@Override
	protected Mono<Void> renderInternal(Map<String, Object> model, MediaType mediaType, ServerWebExchange serverWebExchange) {
		final Flux<Presentation> presentations = (Flux<Presentation>) model.get("presentations");
		
		
		return responseWriter.write(serverWebExchange, presentations, res -> Rocker.template("index.rocker.html")
				.bind("presentations", model.get("presentations"))
				.bind("i18n", model.get("i18n"))
				.render()
				.toString());
	}
}
