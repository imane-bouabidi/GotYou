package com.wora.gotYou.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenericService <CREATE_DTO,UPDATE_DTO,DTO,ID>{
    DTO save(CREATE_DTO dto);
    DTO update(UPDATE_DTO dto, ID id);
    List<DTO> findAll();
    void delete(ID id);
}
