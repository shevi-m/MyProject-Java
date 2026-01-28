package org.example.demo.Controller;


import org.example.demo.Model.Age;
import org.example.demo.Service.AgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/ages")
//האם צריך את זה?
//@CrossOrigin
public class AgeController {
    AgeRepository ageRepository;

    @Autowired
    public AgeController(AgeRepository ageRepository) {
        this.ageRepository = ageRepository;
    }
//V
    @GetMapping("/getAge/{id}")
    public ResponseEntity<Age> get(@PathVariable long id) {
        Age age = ageRepository.findById(id).get();
        if (age != null)
            return new ResponseEntity<>(age, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//V
    @GetMapping("/getAges")
    public ResponseEntity<List<Age>> getAllAges(){
        try{
            List<Age> ages=ageRepository.findAll();
            return new ResponseEntity<>(ages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//V
    @PostMapping("/addAge")
    public ResponseEntity<Age> post(@RequestBody Age age) {
        try {
            Age newAge = ageRepository.save(age);
            return new ResponseEntity<>(newAge, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
