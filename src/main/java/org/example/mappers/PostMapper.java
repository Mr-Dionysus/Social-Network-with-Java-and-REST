package org.example.mappers;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "author", ignore = true)
    PostDTO postToPostDTO(Post post);
}
