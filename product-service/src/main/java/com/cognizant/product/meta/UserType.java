package com.cognizant.product.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum UserType {

	Buyer,
	Seller;

	public static UserType of (String categorydata) {
		return Arrays.asList(values()).stream().filter(category -> category.toString().equalsIgnoreCase(categorydata)).findFirst().get();
	}


	private static List<UserType> somethingList =
            new ArrayList<>(EnumSet.allOf(UserType.class));
	
	public static boolean contains(String value) {
		try {
			return somethingList.contains(Enum.valueOf(UserType.class, value));
		} catch (Exception e) {
			return false;
		}
	}
}
