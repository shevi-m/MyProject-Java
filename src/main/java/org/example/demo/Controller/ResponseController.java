package org.example.demo.Controller;

import jakarta.validation.Valid;
import org.example.demo.Model.Response;
import org.example.demo.Service.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
//האם צריך את זה?
//@CrossOrigin
public class ResponseController {
    ResponseRepository responseRepository;

    @Autowired
    public ResponseController(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    //V
    @GetMapping("/getResponse/{id}")
    public ResponseEntity<Response> get(@PathVariable long id) {
        Response response = responseRepository.findById(id).orElse(null);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//V
    @GetMapping("/getResponses")
    public ResponseEntity<List<Response>> getAllResponses(){
        try{
            List<Response> responses=responseRepository.findAll();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//???
    @PostMapping("/addResponse")
    public ResponseEntity<Response> post(@Valid @RequestBody Response response) {
        try {
            Response newResponse = responseRepository.save(response);
            return new ResponseEntity<>(newResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
