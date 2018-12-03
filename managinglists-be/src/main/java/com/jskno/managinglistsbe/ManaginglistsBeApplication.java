package com.jskno.managinglistsbe;

import com.jskno.managinglistsbe.uploadfile.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ManaginglistsBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManaginglistsBeApplication.class, args);
    }
}
