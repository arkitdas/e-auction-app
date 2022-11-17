package com.cognizant.user.cqrs.aggreagate;

import com.cognizant.cqrs.core.aggregate.AggregateRoot;
import com.cognizant.user.cqrs.commands.UserAddCommand;
import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAggregate extends AggregateRoot {

	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;
	
	private String email;
	
	private String userType;
	
	private boolean active;
	

	public UserAggregate(UserAddCommand command) {
		raiseEvent(UserAddEvent.builder()
			.userId(command.getId())
			.firstName(command.getFirstName())
			.lastName(command.getLastName())
			.address(command.getAddress())
			.city(command.getCity())
			.state(command.getState())
			.pin(command.getPin())
			.phone(command.getPhone())
			.email(command.getEmail())
			.userType(command.getUserType())
			.build()
		);
	}

	public void apply(UserAddEvent event) {
		this.userId = event.getUserId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.address = event.getAddress();
		this.city = event.getCity();
		this.state = event.getState();
		this.pin = event.getPin();
		this.phone = event.getPhone();
		this.email = event.getEmail();
		this.userType = event.getUserType();
		this.active = true;
	}
	
	public void delete() {
		if (!this.active) {
            throw new IllegalStateException("User has already been deleted!");
        }
		raiseEvent(UserDeleteEvent.builder()
			.id(this.id)
			.userId(this.id)
			.build()
		);
	}
	
	public void apply(UserDeleteEvent event) {
		this.id = event.getId();
		this.active = false;
	}
}
