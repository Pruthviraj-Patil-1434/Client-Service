package com.example.clientservicedemo.repo;

import com.example.clientservicedemo.entity.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientUserRepo extends JpaRepository<ClientUser, Long> {

           public boolean existsByEmail(String email);
        Optional<ClientUser> findByEmail(String email);
        Optional<ClientUser> findByClientCode(String clientCode);
}
