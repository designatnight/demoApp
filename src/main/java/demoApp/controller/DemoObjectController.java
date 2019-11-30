package demoApp.controller;

import demoApp.domain.DemoObject;
import demoApp.service.DemoObjectService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/demo")
public class DemoObjectController {

    @Autowired
    DemoObjectService demoObjectService;

    public DemoObjectController(DemoObjectService demoObjectService) {
        this.demoObjectService = demoObjectService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Name must be unique")
    })
    public DemoObject createDemoObject(@RequestBody DemoObject demoObject) {
        return demoObjectService.createDemoObject(demoObject);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Name must be unique")
    })
    public DemoObject updateDemoObject(@RequestBody DemoObject demoObject) {
        return demoObjectService.updateDemoObject(demoObject);
    }

    @DeleteMapping("/{id}")
    public void deleteDemoObject(@PathVariable UUID id) {
        demoObjectService.deleteDemoObject(id);
    }

    @GetMapping("/{id}")
    public DemoObject getDemoObject(@PathVariable UUID id) {
        return demoObjectService.getDemoObjectById(id);
    }

    @GetMapping("/all")
    public List getAllDemoObjects() {
        return demoObjectService.getAllDemoObjects();
    }

}
