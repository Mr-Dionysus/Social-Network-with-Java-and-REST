package org.example.controllers;

import org.example.dtos.RoleDTO;
import org.example.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO createdRoleDTO = roleService.createRole(roleDTO.getRoleName(), roleDTO.getDescription());
            return new ResponseEntity<>(createdRoleDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") int id) {
        try {
            RoleDTO foundRoleDTO = roleService.getRoleById(id);
            return new ResponseEntity<>(foundRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<RoleDTO>> getAllRoles() {
        try {
            ArrayList<RoleDTO> listRolesDTO = (ArrayList<RoleDTO>) roleService.getAllRoles();

            return new ResponseEntity<>(listRolesDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRoleById(@PathVariable("id") int id, @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updatedRoleDTO = roleService.updateRoleById(id, roleDTO.getRoleName(), roleDTO.getDescription());
            return new ResponseEntity<>(updatedRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") int id) {
        try {
            roleService.deleteRoleById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
