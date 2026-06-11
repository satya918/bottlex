package com.bottelx.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

}
