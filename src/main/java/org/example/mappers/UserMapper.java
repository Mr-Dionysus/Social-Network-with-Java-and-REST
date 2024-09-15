package org.example.mappers;

import org.example.dtos.UserCredentialsDTO;
import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

    UserCredentialsDTO userToUserCredentialsDTO(User user);

    User userCredentialsDTOtoUser(UserCredentialsDTO userCredentialsDTO);
}
