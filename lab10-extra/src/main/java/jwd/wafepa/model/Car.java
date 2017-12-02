package jwd.wafepa.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "car")
public class Car {
	 
    private Long id;
    private String model;
    private Integer year;
 
    public Car(BigInteger id, String model, Integer year) {
    	this.id = Long.getLong(id.toString());
        this.model = model;
        this.year = year;
    }
    
    public Car(String model, Integer year) {
        this.model = model;
        this.year = year;
    }
 
    public Car() {
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    (name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }
 
    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }
 
    @Column(name = "YEAR")
    public Integer getYear() {
        return year;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
