package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {
        "sk.tuke.gamestudio.entity",
        "sk.tuke.gamestudio.game.numberlink.entity"
})
@EnableJpaRepositories(basePackages = {
        "sk.tuke.gamestudio.repository",
        "sk.tuke.gamestudio.game.numberlink.repository"
})
@ComponentScan(basePackages = {
        "sk.tuke.gamestudio.config",
        "sk.tuke.gamestudio.service.jpa",
        "sk.tuke.gamestudio.service.auth",
        "sk.tuke.gamestudio.game.numberlink.service",
        "sk.tuke.gamestudio.server"
})
public class SpringServer {
    public static void main(String[] args) {
        SpringApplication.run(SpringServer.class);
    }
}
