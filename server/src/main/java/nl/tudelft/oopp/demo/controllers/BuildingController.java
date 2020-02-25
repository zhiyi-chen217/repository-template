package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @PostMapping("buildings")
    public String newBuilding(@RequestBody Building building, HttpServletResponse httpResponse) {
        Optional<Building> buildingOptional =  buildingRepository.findByName(building.getName());
        if(buildingOptional.isPresent()){
            httpResponse.setStatus(400);
            return "building already exists";
        }
        httpResponse.setStatus(201);
        buildingRepository.save(building);
        return "Saved successfully";
    }
}
