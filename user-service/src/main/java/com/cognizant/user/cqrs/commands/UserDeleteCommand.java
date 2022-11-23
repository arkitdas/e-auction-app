package com.cognizant.user.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDeleteCommand extends BaseCommand {

	private String userId;
}
