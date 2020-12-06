/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lottomatching.repository;

import com.lottomatching.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author didin
 */
public interface UserRepository extends MongoRepository<User, String> {
    
    User findByEmail(String email);
    
}
