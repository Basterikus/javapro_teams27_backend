package org.javaproteam27.socialnetwork.util;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.json.Link;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class YandexDisk {
    private final static String yandexUser = "javaproteams27@yandex.ru";
    private final static String yandexToken = "y0_AgAAAABkHWYcAAhcJAAAAADNMMSQdTIhTbQjRfCCNvyw30mWw_U7WIY";

//    @Scheduled(fixedRateString = "PT12H")
//    @Async
    public void uploadLogs() {
        try {
            log.info("Started saving logs to remote storage");
            RestClient yaDisk = getRestClient();
            ProgressListener progressListener = getProgressListener();
            logsUpload(progressListener, yaDisk);
            archivedLogsUpload(progressListener, yaDisk);
            log.info("Logs successfully uploaded to remote storage");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void archivedLogsUpload (ProgressListener progressListener, RestClient yaDisk) throws ServerException,
            IOException {
        var archivedFiles = new File("src/main/resources/logs/archived").listFiles();
        if (archivedFiles != null) {
            for (File file : archivedFiles) {
                var fileName = file.getName();
                String path = "logs/archived/" + fileName;
                Link newLink = yaDisk.getUploadLink(path, true);
                yaDisk.uploadFile(newLink, true, file, progressListener);
            }
        }
    }

    private void logsUpload (ProgressListener progressListener, RestClient yaDisk) throws ServerException,
            IOException {
        Link infoLoggerLink = yaDisk.getUploadLink("logs/info-logger.log", true);
        File infoLoggerFile = new File("src/main/resources/logs/info-logger.log");
        yaDisk.uploadFile(infoLoggerLink, true, infoLoggerFile, progressListener);

        Link debugLoggerLink = yaDisk.getUploadLink("logs/debug-logger.log", true);
        File debugLoggerFile = new File("src/main/resources/logs/debug-logger.log");
        yaDisk.uploadFile(debugLoggerLink, true, debugLoggerFile, progressListener);
    }

    private RestClient getRestClient() {
        Credentials credentials = new Credentials(yandexUser, yandexToken);
        return new RestClient(credentials);
    }

    private ProgressListener getProgressListener() {
        return new ProgressListener() {
            @Override
            public void updateProgress(long l, long l1) {

            }

            @Override
            public boolean hasCancelled() {
                return false;
            }
        };
    }
}
