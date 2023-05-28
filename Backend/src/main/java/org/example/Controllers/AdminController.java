package org.example.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.Admin;
import org.example.Model.Cinema;
import org.example.Services.AdminService;
import org.example.Services.CinemaHallService;
import org.example.Services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping(value = "/admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") int id){
        return new ResponseEntity<>(adminService.getAdmin(id), HttpStatus.OK);
    }

    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody ObjectNode json){
        if (!json.has("email") || !json.has("password"))
            throw new IllegalArgumentException("Wrong values");

            Admin admin = adminService.updateAdmin(id, json.get("email").asText(), json.get("password").asText());
            return new ResponseEntity<>(admin, HttpStatus.OK);

    }


}
