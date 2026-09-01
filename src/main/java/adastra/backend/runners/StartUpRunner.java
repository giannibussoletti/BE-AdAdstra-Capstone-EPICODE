package adastra.backend.runners;

import adastra.backend.services.ScreeningTimeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartUpRunner implements CommandLineRunner {

    private ScreeningTimeService stService;


    @Override
    public void run(String... args) throws Exception {
        stService.sofDeleteOldScreeningTime();
        stService.hardDeleteOldScreeningTime();
    }
}