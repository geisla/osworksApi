package com.algaworks.osworks.domain.ropository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.domain.model.OrdemServicoDomainModel;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServicoDomainModel, Long>{
	
}
