package org.example.controllers;

import org.example.dtos.RoleDTO;
import org.example.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles/users")
public class UsersRolesController {

    @Autowired
    private RoleService roleService;

    @PutMapping("/{userId/{roleId")
    public ResponseEntity<RoleDTO> assignRoleToUser(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId) {
        try {
            roleService.assignRoleToUser(userId, roleId);
            RoleDTO updatedRoleDTO = roleService.getRoleById(roleId);

            return new ResponseEntity<>(updatedRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
