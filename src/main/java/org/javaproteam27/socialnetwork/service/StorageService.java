package org.javaproteam27.socialnetwork.service;

import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final YandexDisk yandexDisk;
    private String path = "images/";
    private String imageName;

    public StorageRs postStorage(MultipartFile image, String token) throws ServerException, IOException {

        Person person = personRepository.findByEmail(jwtTokenProvider.getUsername(token));

        StorageRs response = new StorageRs();
        response.setError("string");
        response.setTimestamp(System.currentTimeMillis());
        person.setPhoto(uploadImage(image));
        StorageDataRs storageDataRs = new StorageDataRs();
        storageDataRs.setId(imageName);
        storageDataRs.setOwnerId(person.getId());
        storageDataRs.setFileName(image.getName());
        storageDataRs.setFileFormat(image.getContentType());

        return response;
    }

    private String uploadImage(MultipartFile image) throws ServerException, IOException {

        RestClient yaDisk = yandexDisk.getRestClient();
        ProgressListener progressListener = yandexDisk.getProgressListener();

        imageName = UUID.randomUUID().toString();
        String imageFormat = image.getOriginalFilename().split("\\.")[1];
        String source = path + imageName + "." + imageFormat;

        Link imageLink = yaDisk.getUploadLink(source, true);
        File file = new File("src/main/resources/images/" + imageName + "." + imageFormat);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
        yaDisk.uploadFile(imageLink, true, file , progressListener);
        file.delete();
        return source;
    }
}
