package com.mockproject.service.impl;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.repository.StatsRepo;
import com.mockproject.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService  {

	@Autowired
	private StatsRepo repo;
	
	@Override
	public String[][] getTotalPriceLast6Month() {
		String result[][] = new String[2][6];
		YearMonth currentTime = YearMonth.now(); 	
		for (int i = 0; i < 6; i++) {
			String month = String.valueOf(currentTime.minusMonths((long)i).getMonthValue());
			String year = String.valueOf(currentTime.getYear());
			result[0][5-i] = month + "-" + year;
			result[1][5-i] = repo.getToTalPricePerMonth(month, year);
		}
		return result;
	}

}
