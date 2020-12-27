package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.User;

public interface EmailService {
    void sendRegistrationEmail(User user);

    void sendRestorePwdEmail(User user);
}
