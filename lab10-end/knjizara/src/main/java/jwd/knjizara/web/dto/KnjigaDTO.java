package jwd.knjizara.web.dto;

public class KnjigaDTO {
	private Long id;
	private String naziv;
	private String pisac;
	private String ISBN;
	private Integer kolicina;
	private Double cena;
	
	private Long izdavacId;
	private String izdavacNaziv;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIzdavacId() {
		return izdavacId;
	}
	public void setIzdavacId(Long izdavacId) {
		this.izdavacId = izdavacId;
	}
	public String getIzdavacNaziv() {
		return izdavacNaziv;
	}
	public void setIzdavacNaziv(String izdavacNaziv) {
		this.izdavacNaziv = izdavacNaziv;
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
	public void setISBN(String iSBN) {
		ISBN = iSBN;
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
}
