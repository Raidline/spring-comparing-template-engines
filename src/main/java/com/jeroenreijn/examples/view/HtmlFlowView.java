package com.jeroenreijn.examples.view;

import com.jeroenreijn.examples.model.Presentation;
import com.jeroenreijn.examples.view.response.ReactiveResponseWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class HtmlFlowView extends AbstractView {
	
	private final ReactiveResponseWriter<Presentation> responseWriter;
	
	public HtmlFlowView(ReactiveResponseWriter<Presentation> responseWriter) {
		this.responseWriter = responseWriter;
	}
	
	@NotNull
	@Override
	protected Mono<Void> renderInternal(Map<String, Object> model, MediaType mediaType, @NotNull ServerWebExchange serverWebExchange) {
		
		final Flux<Presentation> presentations = (Flux<Presentation>) model.get("presentations");
		//TODO replace with my new version
		return Mono.empty();
		//return responseWriter.write(serverWebExchange, presentations, presents -> HtmlFlowIndexView.view.render(presents));
	}
}
