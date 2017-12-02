package jwd.wafepa.repository;

import java.util.List;

import jwd.wafepa.model.Car;

public interface CarRepository {
	List<Car> findCarByYear(Integer year);
}
