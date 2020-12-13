package com.lottomatching;

import com.lottomatching.domain.News;
import com.lottomatching.domain.Role;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.repository.NewsRepository;
import com.lottomatching.repository.RoleRepository;
import com.lottomatching.repository.RoundRepository;
import com.lottomatching.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringBootMongodbSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongodbSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, NewsRepository newsRepository, RoundRepository roundRepository) {

        return args -> {

            // init roles
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }

            //init admin
            User userAdmin = userRepository.findByEmail("admin");
            if (userAdmin == null) {
                Role newAdminRole = roleRepository.findByRole("ADMIN");
                userAdmin = new User();
                userAdmin.setEmail("admin");
                userAdmin.setPassword("$2a$10$KXcCwpQzwA6DRY0e2Du2duju7b7hxDBljYi9tGwiLUFT0DeLnecQu"); // admin
                userAdmin.setEnabled(true);
                userAdmin.setRoles(new HashSet<>(Arrays.asList(newAdminRole)));
                userAdmin.setFullName("admin");
                userAdmin.setNote("This is admin");
                userRepository.save(userAdmin);
            }

            // init news
            List<News> newsList = newsRepository.findAll();
            if (newsList.isEmpty()) {
                News news = new News();
                news.setId(1);
                news.setMsg("This is News !!");
                newsRepository.save(news);
            }

            // init round
            List<Round> roundList = roundRepository.findAll();
            DecimalFormat df = new DecimalFormat("00");
            if (roundList.isEmpty()) {
                for (long i = 0; i < 100; i++) {
                    Round r = new Round();
                    r.setId(i);
                    r.setNumber(df.format(i));
                    r.setEnabled(false);
                    if ((i % 2) == 0) {
                        r.setName(r.getNumber() + " : โค้วต้า");
                    } else {
                        r.setName(r.getNumber() + " : เสรี");
                    }
                    roundRepository.save(r);
                }
            }


        };

    }
}
