package org.example.mappers;

import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "users", ignore = true)
    RoleDTO roleToRoleDTO(Role role);

    Role roleDTOtoRole(RoleDTO roleDTO);
}
