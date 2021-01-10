package by.epamtraining.airlines;
/*
    12. Система Авиакомпания. Авиакомпания имеет список рейсов. Диспетчер формирует летную Бригаду
(пилоты, штурман, радист, стюардессы) на Рейс. Администратор управляет списком рейсов
     */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStarter {
    public static final int RECORDS_PER_PAGE = 5;

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }
}
