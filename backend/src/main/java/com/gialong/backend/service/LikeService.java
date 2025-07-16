package com.gialong.backend.service;

import com.gialong.backend.dto.LikeDTO;
import com.gialong.backend.model.Like;
import com.gialong.backend.model.User;
import com.gialong.backend.repository.LikeRepository;
import com.gialong.backend.repository.PostRepository;
import com.gialong.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // CRUD
    public Long countLikes(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    public Boolean isLiked(UUID postId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            // User is not logged in
            return null;
        }

        String email = authentication.getName();
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        // User is logged in but not found with email
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();

        return likeRepository.existsByUserIdAndPostId(user.getId(), postId);
    }

    public LikeDTO toggleLike(UUID postId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            // User is not logged in
            return null;
        }

        String email = authentication.getName();
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        // User is logged in but not found with email
        if(optionalUser.isEmpty()) return null;
        User user = optionalUser.get();

        Optional<Like> like = likeRepository.findByUserIdAndPostId(user.getId(), postId);
        LikeDTO likeDTO = new LikeDTO();
        if(like.isPresent()) {
            // unlike
            likeRepository.delete(like.get());
        }
        else {
            // like
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId)));
            likeRepository.save(newLike);
            mapToDTO(newLike, likeDTO);
        }
        return likeDTO;
    }

    // MAPPER
    private void mapToDTO(Like like, LikeDTO likeDTO) {
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setPostId(like.getPost().getId());
        likeDTO.setCreatedDate(like.getCreatedDate());
    }

    private Like mapToEntity(LikeDTO likeDTO, Like like) {
        like.setId(likeDTO.getId());
        like.setUser(userRepository.findById(likeDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + likeDTO.getUserId())));
        like.setPost(postRepository.findById(likeDTO.getPostId()).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + likeDTO.getPostId())));
        like.setCreatedDate(likeDTO.getCreatedDate());
        return like;
    }
}