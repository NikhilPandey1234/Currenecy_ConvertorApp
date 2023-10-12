package com.currncyconversions.SCurrencyConversion.repositories;

import com.currncyconversions.SCurrencyConversion.entity.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRatesRepositories extends JpaRepository<CurrencyRates,Long> {
}
