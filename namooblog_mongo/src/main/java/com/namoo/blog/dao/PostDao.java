package com.namoo.blog.dao;

import java.util.List;

import com.namoo.blog.domain.Comment;
import com.namoo.blog.domain.Post;

public interface PostDao {
	//
	int createPost(Post post);
	Post readPost(int postId);
	List<Post> readPostsBySubject(String subject);
	List<Post> readPostsByBlogId(int blogId);
	void updatePost(Post post);
	void deletePost(int postId);
	
	int createComment(int postId, Comment comment);
	void deleteComment(int commentId);
}
