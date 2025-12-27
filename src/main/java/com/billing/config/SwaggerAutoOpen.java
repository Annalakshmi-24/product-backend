package com.billing.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component
public class SwaggerAutoOpen implements ApplicationRunner 
{
    @Override
    public void run(ApplicationArguments args) 
    {
        String url = "http://localhost:8080/api/swagger-ui/index.html";
        System.out.println("Swagger UI available at: " + url);

        try {
            if (Desktop.isDesktopSupported()) 
                {
                Desktop.getDesktop().browse(new URI(url));
                }
        } catch (Exception e) {
            System.out.println("Unable to open browser automatically");
        }
    }
}
