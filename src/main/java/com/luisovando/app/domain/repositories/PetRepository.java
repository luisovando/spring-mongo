package com.luisovando.app.domain.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.luisovando.app.domain.models.Pet;

@EnableScan
public interface PetRepository extends CrudRepository<Pet, String> {
}
