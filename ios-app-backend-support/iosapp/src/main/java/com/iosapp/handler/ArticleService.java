package com.iosapp.handler;

import java.util.NoSuchElementException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iosapp.model.Article;
import com.iosapp.repository.ArticleRepository;
import com.iosapp.utilities.ArticleCreatedEvent;
import com.iosapp.utilities.DefaultUserBuilderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleService {
	
	 private final ArticleRepository articleRepository;
	 private final ApplicationEventPublisher publisher;
	 private final DefaultUserBuilderService usrBuilderService;
	 
	    ArticleService(ArticleRepository articleRepository, ApplicationEventPublisher publisher,
	    		DefaultUserBuilderService usrBuilderService) {
	        this.articleRepository = articleRepository;
	        this.publisher = publisher;
	        this.usrBuilderService = usrBuilderService; 
	    }
	    
	    public Mono<Article> getSingle(String id) { 
	        return this.articleRepository.findById(id)
	        		.switchIfEmpty(Mono.error(new NoSuchElementException("No such element was found.")));
	    }
	    
	    public Flux<Article> getAll() { 
	        return this.articleRepository.findAll();
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')") 
	    public Mono<Article> insert(String title, String content) { 
	    	 return this.articleRepository
	    	            .save(new Article(title, content))
	    	            .doOnSuccess(profile -> {
					    	boolean currentUserIsSubscribed = usrBuilderService.findByUsername(
					    			SecurityContextHolder.getContext().getAuthentication().getName())
					    			.map(user -> user.isSubscribed())
					    			.block();
					    	if(currentUserIsSubscribed) {
						    	this.publisher.publishEvent(new ArticleCreatedEvent(profile));
					    	}
	    	            });
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    public Mono<Article> update(String id, String title, String content) { 
	        return this.articleRepository
	            .findById(id)
	            .map(article -> new Article(article.getId(), title, content))
	            .flatMap(this.articleRepository::save)
	            .switchIfEmpty(Mono.error(new NoSuchElementException("No such element was found.")));
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    public Mono<Article> deleteSingle(String id) { 
	        return this.articleRepository
	        	.findById(id)
	        	.flatMap(article -> this.articleRepository.deleteById(article.getId()).thenReturn(article))
	        	.switchIfEmpty(Mono.error(new NoSuchElementException("No such element was found.")));
	    }
}
