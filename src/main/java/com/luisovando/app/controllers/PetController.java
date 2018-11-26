package com.luisovando.app.controllers;

import java.util.List;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luisovando.app.domain.models.Pet;
import com.luisovando.app.domain.repositories.PetRepository;

@RestController
@RequestMapping("/pets")
public class PetController {

  @Autowired
  private PetRepository repository;

  @GetMapping(value = "")
  public List<Pet> index() {
    return this.repository.findAll();
  }

  @GetMapping(value = "/{id}")
  public Pet show(@PathVariable("id") ObjectId petId) {
    return this.repository.findBy_id(petId);
  }

  @PostMapping(value = "")
  public Pet store(@Valid @RequestBody Pet pet) {
    pet.set_id(ObjectId.get());
    this.repository.save(pet);
    return pet;
  }

  @PutMapping(value = "/{id}")
  public void update(@PathVariable("id") ObjectId petId, @Valid @RequestBody Pet pet) {
    pet.set_id(petId);
    this.repository.save(pet);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") ObjectId petId) {
    this.repository.delete(this.repository.findBy_id(petId));
  }
}
