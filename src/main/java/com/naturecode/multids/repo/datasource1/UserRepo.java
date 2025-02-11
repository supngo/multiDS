package com.naturecode.multids.repo.datasource1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naturecode.multids.model.datasource1.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> { 
    
}
