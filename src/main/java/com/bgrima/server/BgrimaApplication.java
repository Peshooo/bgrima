package com.bgrima.server;

import com.bgrima.server.model.Word;
import com.bgrima.server.service.Dictionary;
import com.bgrima.server.service.utils.WordUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BgrimaApplication {

    public static void main(String[] args) {
        Dictionary.loadWords();
        SpringApplication.run(BgrimaApplication.class, args);
    }

}
