package jwd.knjizara;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jwd.knjizara.model.Knjiga;
import jwd.knjizara.model.Izdavac;
import jwd.knjizara.service.KnjigaService;
import jwd.knjizara.service.IzdavacService;

@Component
public class TestData {
	@Autowired
	private IzdavacService izdavacService;
	@Autowired
	private KnjigaService knjigaService;

	@PostConstruct
	public void init() {
		
		Izdavac i1 = new Izdavac();
		i1.setNaziv("Polet");
		i1.setEmail("polet@gmail.com");
		i1.setTelefon("0667612312");
		izdavacService.save(i1);
		
		Izdavac i2 = new Izdavac();
		i2.setNaziv("Delfi");
		i2.setEmail("delfi@gmail.com");
		i2.setTelefon("0661233211");
		izdavacService.save(i2);
		
		Knjiga k1 = new Knjiga();
		k1.setISBN("1100110011");
		k1.setNaziv("Faust");
		k1.setKolicina(10);
		k1.setPisac("Johan Volfgang Gete");
		k1.setIzdavac(i2);
		k1.setCena(1231.00);
		knjigaService.save(k1);
		
		Knjiga k2 = new Knjiga();
		k2.setISBN("12312312");
		k2.setNaziv("Tako je govorio Zaratustra");
		k2.setKolicina(15);
		k2.setPisac("Fridrih Nice");
		k2.setIzdavac(i2);
		k2.setCena(1250.00);
		knjigaService.save(k2);

	}
}