package main.config;

import main.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    private final UserRepository userRepository;

    public ScheduledConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelayString = "${scheduled.expired_time}")
    public void scheduleFixedDelayTask() {
        userRepository.clearAllCodes();
    }
}
