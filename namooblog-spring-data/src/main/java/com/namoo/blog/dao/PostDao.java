package com.namoo.blog.dao;

import java.util.List;

import com.namoo.blog.domain.Post;

public interface PostDao {
	//
	String createPost(String blogId, Post post);
	Post readPost(String postId);
	List<Post> readPostsByBlogId(String blogId);
	void updatePost(Post post);
	void deletePost(String postId);
	
}
