package org.example.mappers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "posts", ignore = true)
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

    UserCredentialsDTO userToUserCredentialsDTO(User user);

    User userCredentialsDTOtoUser(UserCredentialsDTO userCredentialsDTO);
}
