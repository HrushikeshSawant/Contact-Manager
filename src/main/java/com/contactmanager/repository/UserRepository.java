package com.contactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
