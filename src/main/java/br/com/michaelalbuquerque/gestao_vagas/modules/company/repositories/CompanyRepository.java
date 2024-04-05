package br.com.michaelalbuquerque.gestao_vagas.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michaelalbuquerque.gestao_vagas.modules.company.entities.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
   Optional<CompanyEntity> findByUsernameOrEmail(String usname, String email);

   Optional<CompanyEntity> findByUsername(String username);

    
} 
