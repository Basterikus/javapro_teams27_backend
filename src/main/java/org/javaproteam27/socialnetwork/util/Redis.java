package org.javaproteam27.socialnetwork.util;

import liquibase.pro.packaged.E;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaproteam27.socialnetwork.config.RedisConfig;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.redisson.Redisson;
import org.redisson.RedissonReactive;
import org.redisson.api.RMap;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
@Slf4j
public class Redis {

    // Объект для работы с Redis
    private RedissonClient redisson;
    //хэшмеп для фоток
//    private RMap<String, String> usersPhoto;
    private ConcurrentHashMap<String, String> usersPhoto;
    private final String name = "USER_PHOTO";
    private final PersonRepository personRepository;
    private final DropBox dropBox;
    private final RedisConfig redisConfig;

    private void init() {
//        Config config = new Config();
//        config.useSingleServer().setAddress(redisConfig.getUrl());
//        try {
//            redisson = Redisson.create(config);
//        } catch (RedisConnectionException e) {
//            log.info("Не удалось подключиться к Redis");
//            log.info(e.getMessage());
//        }
        usersPhoto = new ConcurrentHashMap<>();
    }

    public void add(Integer id, String url) {
        String currentUrl = dropBox.getLinkImages(url);
        if (!usersPhoto.containsKey(String.valueOf(id))) {
            usersPhoto.put(String.valueOf(id), currentUrl);
        }
        else {
            usersPhoto.replace(String.valueOf(id), currentUrl);
        }
    }

    public String getUrl(Integer id) {
        if (redisConfig.isEnabled()) {
            return usersPhoto.get(String.valueOf(id));
        } else {
            return "https://i.imgur.com/RioIkGD.png";
        }
    }

    @Scheduled(initialDelay = 6000, fixedDelayString = "PT24H")
    @Async
    private void updateUrl() {
        if (redisson == null) {
            init();
        }
        personRepository.findAll().forEach(person ->
            add(person.getId(), person.getPhoto()));
    }

    public void shutdown() {
        redisson.shutdown();
    }

}
