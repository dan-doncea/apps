package com.iosapp.handler;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.model.Article;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ArticleHandler {
	
    private final ArticleService articleService;

    ArticleHandler(ArticleService articleService) {
        this.articleService = articleService;
    }
	
    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Article> flux = request
            .bodyToFlux(Article.class)
            .flatMap(article -> this.articleService.insert(article.getTitle(), article.getContent()));
        
        return defaultWriteResponse(flux);
    }
    
    public Mono<ServerResponse> getById(ServerRequest request) {
        return defaultReadResponse(this.articleService.getSingle(id(request)));
    }
    
    public Mono<ServerResponse> getAll(ServerRequest request) {
//    	request.bodyToMono(Article.class).subscribe(a -> System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()));
        return defaultReadResponse(this.articleService.getAll());
    }
    
    public Mono<ServerResponse> update(ServerRequest request) {
    	Flux<Article> id = request
            .bodyToFlux(Article.class)
            .flatMap(article -> this.articleService.update(id(request), article.getTitle(), article.getContent()));
        
        return defaultWriteResponse(id);
    }
    
    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(this.articleService.deleteSingle(id(request)));
    }
    
    private static Mono<ServerResponse> defaultReadResponse(Publisher<Article> articles) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)          
            .body(articles, Article.class);
    }
    
    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Article> articles) {
        return Mono
            .from(articles)
            .flatMap(p -> ServerResponse
                .created(URI.create("/articles/" + p.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build()
            );
    }
    
    private static String id(ServerRequest request) { // get parameter id from URL
        return request.pathVariable("id");
    }
}
