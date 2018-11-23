package com.luisovando.app.domain.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.luisovando.app.domain.models.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
	Pet findBy_id(ObjectId _id);
}
