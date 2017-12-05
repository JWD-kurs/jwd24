package jwd.knjizara.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jwd.knjizara.model.Knjiga;

@Repository
public interface KnjigaRepository 
	extends JpaRepository<Knjiga, Long> {

	Page<Knjiga> findByIzdavacId(Long mestoId, Pageable pageRequest);

	@Query("SELECT k FROM Knjiga k WHERE "
			+ "(:naziv IS NULL or k.naziv like :naziv ) AND "
			+ "(:pisac IS NULL or k.pisac like :pisac ) AND "
			+ "(:maxKolicina IS NULL OR k.kolicina <= :maxKolicina)"
			)
	Page<Knjiga> pretraga(
			@Param("naziv") String naziv, 
			@Param("pisac") String organizator, 
			@Param("maxKolicina") Integer maxKolicina,
			Pageable pageRequest);

}
