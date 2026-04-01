package com.marketplace.SelfPraktik.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}