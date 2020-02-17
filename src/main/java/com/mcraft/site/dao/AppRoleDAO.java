package com.mcraft.site.dao;


import org.springframework.data.repository.CrudRepository;
import entity.AppRole;



 
public interface AppRoleDAO extends CrudRepository<AppRole, Long>{
	
	AppRole findByRoleName(String roleName);
	
}
