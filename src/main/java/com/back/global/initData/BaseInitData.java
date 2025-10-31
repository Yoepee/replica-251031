package com.back.global.initData;

import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
//            self.work3();
//            self.work4();
        };
    }

    @Transactional
    public void work1() {
        if(postService.count() > 0) return;

        for (int i = 0; i < 5; i++){
            postService.write("제목%d".formatted(i+1), "내용%d".formatted(i+1));
        }
    }

    @Transactional
    public void work2() {
        postService.findAll()
                .forEach(post -> System.out.println("Post id:"+post.getId()));
    }

    @Transactional
    public void work3() {
        postService.findAll()
                .forEach(post -> System.out.println("Post id:"+post.getId()));
    }

    @Transactional
    public void work4() {
        postService.findAll()
                .forEach(post -> System.out.println("Post id:"+post.getId()));
    }
}
