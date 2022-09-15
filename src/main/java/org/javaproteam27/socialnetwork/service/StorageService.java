package org.javaproteam27.socialnetwork.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
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
import org.javaproteam27.socialnetwork.util.DropBox;
import org.javaproteam27.socialnetwork.util.YandexDisk;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final YandexDisk yandexDisk;
    private final DropBox dropBox;
    private String path = "https://dl.dropbox.com/s/ktq00qre5oqiatp";
    private String imageName;

    public StorageRs postStorage(MultipartFile image, String token) throws ServerException, IOException, DbxException {

        StorageRs response = new StorageRs();
        response.setError("string");
        response.setTimestamp(System.currentTimeMillis());

        if (image == null) {
            return response;
        }
        Person person = personRepository.findByEmail(jwtTokenProvider.getUsername(token));

        person.setPhoto(dropBoxUploadImages(image));
        StorageDataRs storageDataRs = new StorageDataRs();
        storageDataRs.setId(imageName);
        storageDataRs.setOwnerId(person.getId());
        storageDataRs.setFileName(image.getName());
        storageDataRs.setFileFormat(image.getContentType());
        personRepository.savePhoto(person);

        return response;
    }

    private String YandexMarketUploadImage(MultipartFile image) throws ServerException, IOException {

        RestClient yaDisk = yandexDisk.getRestClient();
        ProgressListener progressListener = yandexDisk.getProgressListener();

        imageName = UUID.randomUUID().toString();
        String imageFormat = image.getOriginalFilename().split("\\.")[1];
        String source = path + imageName + "." + imageFormat;

        Link imageLink = yaDisk.getUploadLink(source, true);
        File file = new File("src/main/resources/images/" + imageName + "." + imageFormat);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
        yaDisk.uploadFile(imageLink, true, file, progressListener);
        file.delete();
        System.out.println(source);
        return source;
    }

    public String dropBoxUploadImages(MultipartFile image) throws DbxException, IOException {
        imageName = "/" + UUID.randomUUID().toString() + "." + image.getOriginalFilename().split("\\.")[1];

        String token = DropBox.getRefreshToken();
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/javaproteams_27").build();
        DbxClientV2 client = new DbxClientV2(config, token);

        try (InputStream in = image.getInputStream()) {
            FileMetadata metadata = client.files().uploadBuilder(imageName)
                    .uploadAndFinish(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path + imageName + "?dl=1";
    }
}
