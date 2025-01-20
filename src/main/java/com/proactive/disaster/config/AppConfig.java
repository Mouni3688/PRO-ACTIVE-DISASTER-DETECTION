package com.proactive.disaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dpxwi47vf",
                        "api_key", "633116791365118",
                        "api_secret", "tlZqGGX9FjHHe32FdAmoaaCyy-Q")
        );
    }
}
