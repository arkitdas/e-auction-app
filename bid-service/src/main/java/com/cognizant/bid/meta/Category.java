package com.cognizant.bid.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum Category {

	Painting,
	Sculptor,
	Ornament;

	public static Category of (String categorydata) {
		return Arrays.asList(values()).stream().filter(category -> category.toString().equalsIgnoreCase(categorydata)).findFirst().get();
	}


	private static List<Category> somethingList =
            new ArrayList<>(EnumSet.allOf(Category.class));
	
	public static boolean contains(String value) {
		try {
			return somethingList.contains(Enum.valueOf(Category.class, value));
		} catch (Exception e) {
			return false;
		}
	}
}
