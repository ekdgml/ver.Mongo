package com.namoo.blog.dao;

import com.namoo.blog.domain.Blog;

public interface BlogDao {
	//
	String createBlog(Blog blog);
	Blog readBlog(String blogId);
	void updateBlog(Blog blog);
	void deleteBlog(String blogId);
}