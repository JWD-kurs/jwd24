package jwd.knjizara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwd.knjizara.model.Knjiga;
import jwd.knjizara.model.Kupovina;
import jwd.knjizara.repository.KnjigaRepository;
import jwd.knjizara.repository.KupovinaRepository;
import jwd.knjizara.service.KupovinaService;

@Service
public class JpaKupovinaServiceImpl implements KupovinaService{
	
	@Autowired
	private KupovinaRepository kupovinaRepository;
	@Autowired
	private KnjigaRepository knjigaRepository;
	
	@Override
	public Kupovina buyABook(Long knjigaId) {
		
		if(knjigaId == null) {
			throw new IllegalArgumentException("Id of a book cannot be null!");
		}
		
		Knjiga knjiga = knjigaRepository.findOne(knjigaId);
		if(knjiga == null) {
			throw new IllegalArgumentException("There is no book with given id!");
		}
		
		if(knjiga.getKolicina() > 0) {
			
			Kupovina kupovina = new Kupovina();
			kupovina.setKnjiga(knjiga);
			
			knjiga.setKolicina(knjiga.getKolicina() - 1);
			
			kupovinaRepository.save(kupovina);
			knjigaRepository.save(knjiga);
			
			return kupovina;
		}
		
		return null;
		
	}
}
