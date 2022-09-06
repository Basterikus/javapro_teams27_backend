package org.javaproteam27.socialnetwork.service;

import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;
import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.StorageDataRs;
import org.javaproteam27.socialnetwork.model.dto.response.StorageRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.javaproteam27.socialnetwork.util.YandexDisk;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final YandexDisk yandexDisk;
    private String path = "images/";

    public StorageRs postStorage(MultipartFile image, String token) {

        Person person = personRepository.findByEmail(jwtTokenProvider.getUsername(token));

        StorageRs response = new StorageRs();
        response.setError("string");
        response.setTimestamp(System.currentTimeMillis());

        StorageDataRs storageDataRs = new StorageDataRs();
        storageDataRs.setOwnerId(person.getId());
        storageDataRs.setFileName(image.getName());
        storageDataRs.setFileFormat(image.getContentType());

        return response;
    }

    private void uploadImage(MultipartFile image) throws WrongMethodException, ServerIOException, IOException {
        RestClient yaDisk = yandexDisk.getRestClient();
        ProgressListener progressListener = yandexDisk.getProgressListener();
        System.out.println(image.getName());
//        String finalPath = path + fileName;
//        Link newLink = yaDisk.getUploadLink(finalPath, true);
//        yaDisk.uploadFile(newLink, true, file, progressListener);
    }
}
