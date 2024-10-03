package org.example.controllers;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
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

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        try {
            Role createdRole = roleService.createRole(roleDTO.getRoleName(), roleDTO.getDescription());
            roleDTO = roleMapper.roleToRoleDTO(createdRole);
            return new ResponseEntity<>(roleDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") int id) {
        try {
            Role foundUser = roleService.getRoleById(id);
            RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundUser);
            return new ResponseEntity<>(foundRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<RoleDTO>> getAllRoles() {
        try {
            ArrayList<Role> listRoles = (ArrayList<Role>) roleService.getAllRoles();
            ArrayList<RoleDTO> listRolesDTO = new ArrayList<>();

            for (Role role : listRoles) {
                listRolesDTO.add(roleMapper.roleToRoleDTO(role));
            }

            return new ResponseEntity<>(listRolesDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRoleById(@PathVariable("id") int id, @RequestBody RoleDTO updatedRoleDTO) {
        try {
            Role updatedRole = roleService.updateRoleById(id, updatedRoleDTO.getRoleName(), updatedRoleDTO.getDescription());
            updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);
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
