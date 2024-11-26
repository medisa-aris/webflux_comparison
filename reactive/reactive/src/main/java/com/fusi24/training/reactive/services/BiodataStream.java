package com.fusi24.training.reactive.services;

import org.springframework.stereotype.Component;

import com.fusi24.training.reactive.model.Biodata;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
@Slf4j
public class BiodataStream {
	private final Sinks.Many<Biodata> sink = Sinks.many().multicast().onBackpressureBuffer();

    public Flux<Biodata> getFlux() {
        return sink.asFlux();
    }

    public void emit(Biodata biodata) {
        sink.tryEmitNext(biodata);
    }
}
