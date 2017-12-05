package jwd.knjizara.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Knjiga {
	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String naziv;
	@Column
	private String pisac;
	@Column
	private String ISBN;
	@Column
	private Integer kolicina;
	@Column
	private Double cena;
	@ManyToOne(fetch=FetchType.EAGER)
	private Izdavac izdavac;
	@OneToMany(mappedBy="knjiga",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<Kupovina> kupovine = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getPisac() {
		return pisac;
	}
	public void setPisac(String pisac) {
		this.pisac = pisac;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}	
	public Integer getKolicina() {
		return kolicina;
	}
	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}
	public Double getCena() {
		return cena;
	}
	public void setCena(Double cena) {
		this.cena = cena;
	}
	public Izdavac getIzdavac() {
		return izdavac;
	}
	public void setIzdavac(Izdavac izdavac) {
		this.izdavac = izdavac;
		if(izdavac!=null && !izdavac.getKnjige().contains(this)){
			izdavac.getKnjige().add(this);
		}
	}
	public List<Kupovina> getKupovine() {
		return kupovine;
	}
	public void setKupovine(List<Kupovina> kupovine) {
		this.kupovine = kupovine;
	}
	public void addKupovina(Kupovina kupovina){
		this.kupovine.add(kupovina);
		
		if(!this.equals(kupovina.getKnjiga())){
			kupovina.setKnjiga(this);
		}
	}
}
