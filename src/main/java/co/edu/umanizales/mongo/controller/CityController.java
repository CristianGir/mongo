package co.edu.umanizales.mongo.controller;

import co.edu.umanizales.mongo.model.City;
import co.edu.umanizales.mongo.model.CityRepository;
import co.edu.umanizales.mongo.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class CityController {

    @Autowired
    private CityRepository cityRepo;

    @PostMapping("/addcity")
    public ResponseEntity<ResponseDTO> addCity(@RequestBody City city) {
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.save(city), null), HttpStatus.OK);
    }
    @GetMapping("/city/{id}")
    public ResponseEntity<ResponseDTO> getCityById(@PathVariable String id){
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.findById(id), null), HttpStatus.OK);
    }
    @GetMapping("/city")
    public ResponseEntity<ResponseDTO> getAllCity(){
        return new ResponseEntity<>(new ResponseDTO(200, cityRepo.findAll(), null), HttpStatus.OK);
    }
    @DeleteMapping("/city/{id}")
    public ResponseEntity<ResponseDTO> deleteCity(@PathVariable String id) {
        cityRepo.deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(200, "Ciudad eliminada", null), HttpStatus.OK);
    }
    @PutMapping("/update/city/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody String name) {
        Optional<City> cityOptional = cityRepo.findById(id);
        if (cityOptional.isPresent()) {
            City existingCity = cityOptional.get();
            existingCity.setName(name);
            cityRepo.save(existingCity);
            return new ResponseEntity<>(new ResponseDTO(200, existingCity, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(404, null, null), HttpStatus.OK);
        }
    }
}
