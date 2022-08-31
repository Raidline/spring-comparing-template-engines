package com.jeroenreijn.examples.view;

import com.jeroenreijn.examples.model.Presentation;
import com.jeroenreijn.examples.view.response.ReactiveResponseWriter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.result.view.AbstractView;
import org.springframework.web.server.ServerWebExchange;
import org.trimou.Mustache;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Objects;

public class TrimouView extends AbstractView {
	private Mustache mustache;
	private final ReactiveResponseWriter<Presentation> responseWriter;

	protected TrimouView() {
		this.responseWriter = null;
	}
	
	public TrimouView(final Mustache mustache, ReactiveResponseWriter<Presentation> responseWriter) {
		this.mustache = mustache;
		this.responseWriter = responseWriter;
	}
	
	public void setMustache(final Mustache mustache) {
		this.mustache = mustache;
	}
	
	@Override
	protected Mono<Void> renderInternal(Map<String, Object> model, MediaType mediaType, ServerWebExchange serverWebExchange) {
		final Flux<Presentation> presentations = (Flux<Presentation>) model.get("presentations");
		final ServerHttpResponse response = serverWebExchange.getResponse();
		final DataBuffer dataBuffer = response.bufferFactory().allocateBuffer();
		final OutputStreamWriter writer = new OutputStreamWriter(dataBuffer.asOutputStream());
		Objects.requireNonNull(mustache, "mustache must not be null");
		return responseWriter.write(serverWebExchange, presentations, res -> {
			mustache.render(writer, res);
			
			return "";
		});
	}
}
