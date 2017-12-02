package jwd.wafepa.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

import jwd.wafepa.model.Car;

@Repository
public class CarRepositoryImpl implements CarRepository {

	@PersistenceContext
	private EntityManager entityManager;
 
	@Override
	public List<Car> findCarByYear(Integer year){
	   StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("FIND_CAR_BY_YEAR");
	   
	   // Set the parameters of the stored procedure.
	   String p_year = "p_year";
	   storedProcedure.registerStoredProcedureParameter(p_year, Integer.class, ParameterMode.IN);
	   storedProcedure.setParameter(p_year, year);
 
	   // Call the stored procedure. 
	   List<Object[]> storedProcedureResults = storedProcedure.getResultList();
 
	   // Use Java 8's cool new functional programming paradigm to map the objects from the stored procedure results
	   return storedProcedureResults.stream().map(result -> new Car(
	         (BigInteger) result[0],
	         (String) result[1],
	         (Integer) result[2]
	   )).collect(Collectors.toList());
 
	}

}
