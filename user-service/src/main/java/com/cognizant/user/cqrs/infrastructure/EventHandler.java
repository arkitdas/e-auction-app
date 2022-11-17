package com.cognizant.user.cqrs.infrastructure;

import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;
import com.cognizant.user.exception.UserNotFoundException;

public interface EventHandler {
    void on(UserAddEvent event);
    void on(UserDeleteEvent event) throws UserNotFoundException;
}
