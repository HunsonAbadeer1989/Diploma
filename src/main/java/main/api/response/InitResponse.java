package main.api.response;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class InitResponse {

    @Value("${general_blog.title}")
    private String title;
    @Value("${general_blog.subtitle}")
    private String subtitle;
    @Value("${general_blog.phone}")
    private String phone;
    @Value("${general_blog.email}")
    private String email;
    @Value("${general_blog.copyright}")
    private String copyright;
    @Value("${general_blog.copyrightFrom}")
    private String copyrightFrom;

}
