package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.exceptions.RedundantentityException;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to create rest endpoints for saving and updating buildings.
 */
@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * This method receive a Building object to be saved and tries to save it.
     * @param building - building to the saved
     * @param httpResponse - the http response
     * @return
     */
    @PostMapping("admin/buildings")
    public String newBuilding(@RequestBody Building building,
                              HttpServletResponse httpResponse) throws RedundantentityException {
        Optional<Building> buildingOptional =  buildingRepository.findByName(building.getName());
        if (buildingOptional.isPresent()) {
            throw new RedundantentityException("The building already exists");
        }
        httpResponse.setStatus(201);
        buildingRepository.save(building);
        return "Saved successfully";
    }

    /**
     * This method receive a Building object to be updated and tries to update it.
     * @param building the new information for the about to be updated building
     * @param httpResponse the HttpResponse for this operation
     * @return an HttpResponse indicating the status of the operation
     */
    @PutMapping("admin/buildings")
    public String updateBuilding(@RequestBody Building building, HttpServletResponse httpResponse) {
        Optional<Building> buildingOptional =  buildingRepository.findByName(building.getName());
        if (buildingOptional.isEmpty()) {
            httpResponse.setStatus(400);
            return "building does not exist";
        }
        httpResponse.setStatus(201);
        buildingRepository.save(building);
        return "Updated successfully";
    }

    /**
     * This method retrieves all building entities if not specific building specified,
     * or return that specific if it is present.
     * @param name the name of the required building
     * @param httpResponse the generated httpResponse
     * @return an HttpResponse indicating the status of the operation
     */
    @GetMapping("buildings")
    public ResponseEntity readBuilding(@RequestParam Optional<String> name,
                                       HttpServletResponse httpResponse) {
        if (name.isEmpty()) {
            return ResponseEntity.accepted().body(buildingRepository.findAll());
        }

        Optional<Building> building = buildingRepository.findByName(name.get());
        if (building.isPresent()) {
            return ResponseEntity.accepted().body(building);
        }
        return ResponseEntity.badRequest().body("The entity does not exist");
    }

    /**
     * This method tries to delete the provided building.
     * @param names a list of names of the specified building
     * @return an HttpResponse indicating the status of the operation
     */
    @DeleteMapping("admin/buildings")
    public ResponseEntity deleteBuilding(@RequestParam List<String> names) {
        try {
            for (String name : names) {
                buildingRepository.deleteById(name);
            }
            return ResponseEntity.accepted().body("all deleted");
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Cannot delete non-existing building",1);
        }
    }
}
