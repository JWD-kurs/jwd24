package jwd.knjizara.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwd.knjizara.model.Knjiga;
import jwd.knjizara.model.Kupovina;
import jwd.knjizara.service.KnjigaService;
import jwd.knjizara.service.KupovinaService;
import jwd.knjizara.support.KnjigaDTOToKnjiga;
import jwd.knjizara.support.KnjigaToKnjigaDTO;
import jwd.knjizara.support.KupovinaToKupovinaDTO;
import jwd.knjizara.web.dto.KnjigaDTO;
import jwd.knjizara.web.dto.KupovinaDTO;

@RestController
@RequestMapping("/api/knjige")
public class ApiKnjigaController {
	@Autowired
	private KnjigaService knjigaService;
	@Autowired
	private KupovinaService kupovinaService;
	@Autowired
	private KnjigaToKnjigaDTO toKnjigaDTO;
	@Autowired
	private KupovinaToKupovinaDTO toKupovinaDTO;
	@Autowired
	private KnjigaDTOToKnjiga toKnjiga;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<KnjigaDTO>> get(
			@RequestParam(required=false) String naziv,
			@RequestParam(required=false) String pisac,
			@RequestParam(required=false) Integer maxKolicina,
			@RequestParam(defaultValue="0") int pageNum){
		
		Page<Knjiga> knjige;
		
		if(naziv != null || pisac != null || maxKolicina != null) {
			knjige = knjigaService.pretraga(naziv, pisac, maxKolicina, pageNum);
		}else{
			knjige = knjigaService.findAll(pageNum);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(knjige.getTotalPages()) );
		return  new ResponseEntity<>(
				toKnjigaDTO.convert(knjige.getContent()),
				headers,
				HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,
					value="/{id}")
	public ResponseEntity<KnjigaDTO> get(
			@PathVariable Long id){
		Knjiga knjiga = knjigaService.findOne(id);
		
		if(knjiga==null){
			return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				toKnjigaDTO.convert(knjiga),
				HttpStatus.OK);	
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<KnjigaDTO> add(
			@RequestBody KnjigaDTO novaKnjiga){
		
		Knjiga knjiga = toKnjiga.convert(novaKnjiga); 
		knjigaService.save(knjiga);
		
		return new ResponseEntity<>(toKnjigaDTO.convert(knjiga),
				HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}/kupovina")
	public ResponseEntity<KupovinaDTO> buy(@PathVariable Long id){
		
		Kupovina k = kupovinaService.buyABook(id);
		
		if(k == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(toKupovinaDTO.convert(k), HttpStatus.CREATED);
		}
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,
			value="/{id}")
	public ResponseEntity<KnjigaDTO> edit(
			@PathVariable Long id,
			@RequestBody KnjigaDTO izmenjen){
		
		if(!id.equals(izmenjen.getId())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Knjiga knjiga = toKnjiga.convert(izmenjen); 
		knjigaService.save(knjiga);
		
		return new ResponseEntity<>(toKnjigaDTO.convert(knjiga),
				HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,
			value="/{id}")
	public ResponseEntity<KnjigaDTO> delete(@PathVariable Long id){
		knjigaService.remove(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
