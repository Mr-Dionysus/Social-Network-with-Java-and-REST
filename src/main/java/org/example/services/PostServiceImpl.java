package org.example.services;

import org.example.dtos.PostDTO;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.*;
import org.example.mappers.PostMapper;
import org.example.mappers.PostMapperImpl;
import org.example.repositories.PostRepository;
import org.example.repositories.UserRepository;
import org.example.validators.PostValidator;
import org.example.validators.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDTO createPost(String text, int userId) {
        PostValidator.text(text);
        UserValidator.userId(userId);

        User user = userRepository.findById(userId)
                                  .isPresent() ? userRepository.findById(userId)
                                                               .get() : null;
        Post post = new Post(text, user);
        Post createdPost = postRepository.save(post);
        PostValidator.createdPost(createdPost, text);
        PostDTO createdPostDTO = postMapper.postToPostDTO(createdPost);

        return createdPostDTO;
    }

    @Override
    public PostDTO getPostById(int postId) {
        PostValidator.postId(postId);

        Post foundPost = postRepository.findById(postId)
                                       .isPresent() ? postRepository.findById(postId)
                                                                    .get() : null;
        PostValidator.foundPost(foundPost, postId);
        PostDTO foundPostDTO = postMapper.postToPostDTO(foundPost);

        return foundPostDTO;
    }

    @Override
    public PostDTO updatePostById(int postId, String newText) {
        PostValidator.postId(postId);
        PostValidator.text(newText);

        Post post = postRepository.findById(postId)
                                  .isPresent() ? postRepository.findById(postId)
                                                               .get() : null;
        PostValidator.foundPost(post, postId);
        post.setText(newText);
        Post updatedPost = postRepository.save(post);
        PostValidator.foundPost(updatedPost, postId);
        PostDTO updatedPostDTO = postMapper.postToPostDTO(updatedPost);

        return updatedPostDTO;
    }

    @Override
    public void deletePostById(int postId) {
        PostValidator.postId(postId);

        if (this.getPostById(postId) == null) {
            throw new PostNotFoundException("Error while deleting the Post. Post with ID '" + postId + "' can't be found");
        }
        Post post = postRepository.findById(postId)
                                  .isPresent() ? postRepository.findById(postId)
                                                               .get() : null;
        PostValidator.foundPost(post, postId);
        User user = post.getAuthor();

        if (user != null) {
            user.getPosts()
                .remove(post);
            userRepository.save(user);
        }

        postRepository.deleteById(postId);

    }
}
