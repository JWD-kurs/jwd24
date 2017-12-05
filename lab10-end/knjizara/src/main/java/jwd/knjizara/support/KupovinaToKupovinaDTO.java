package jwd.knjizara.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.knjizara.model.Kupovina;
import jwd.knjizara.web.dto.KupovinaDTO;

@Component
public class KupovinaToKupovinaDTO implements Converter<Kupovina, KupovinaDTO> {

	@Override
	public KupovinaDTO convert(Kupovina arg0) {
		
		KupovinaDTO dto = new KupovinaDTO();
		dto.setId(arg0.getId());
		
		return dto;
	}

}
