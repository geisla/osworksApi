package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.api.model.OrdemServicoRepresentationModel;
import com.algaworks.osworks.domain.model.OrdemServicoDomainModel;
import com.algaworks.osworks.domain.ropository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel criar(@Valid @RequestBody OrdemServicoDomainModel ordemServico) {
				
		return toModelMapper(gestaoOrdemServicoService.criarOrdem(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoRepresentationModel> listar() {
		return toListModelMapper(ordemServicoRepository.findAll()); 
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar (@PathVariable Long ordemServicoId) {
		Optional<OrdemServicoDomainModel> ordemServicoDomainModel = ordemServicoRepository.findById(ordemServicoId);
		if (ordemServicoDomainModel.isPresent()) {
			
			/* Poderia ser feito da seguinte maneira sem o ModelMapper:
			 * 
			 * OrdemServicoRepresentationModel repModel = new OrdemServicoRepresentationModel();
			 * repModel.setId(ordemServico.get().getId());
			   repModel.setPreco(ordemServico.get().getPreco()); */
			
			/* Já com o ModelMapper, porém é uma boa prática isolar em um método privado
			 * 
			 * OrdemServicoRepresentationModel representationModel = modelMapper.map(ordemServico.get(), OrdemServicoRepresentationModel.class);
			   return ResponseEntity.ok(representationModel);*/
			
			OrdemServicoRepresentationModel representationModel = toModelMapper(ordemServicoDomainModel.get());
			
			return ResponseEntity.ok(representationModel);
			
			
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private OrdemServicoRepresentationModel toModelMapper (OrdemServicoDomainModel ordemServicoDomainModel) {
		return modelMapper.map(ordemServicoDomainModel, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> toListModelMapper(List<OrdemServicoDomainModel> ordensServicoDomainModel) { 
		return ordensServicoDomainModel.stream()
				.map(ordemServicoRepresentationModel -> toModelMapper(ordemServicoRepresentationModel))
				.collect(Collectors.toList());
	}

}
