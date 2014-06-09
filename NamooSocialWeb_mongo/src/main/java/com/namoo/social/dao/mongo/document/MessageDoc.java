package com.namoo.social.dao.mongo.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.Message;

@Document(collection="messages")
public class MessageDoc {
	//
	@Id
	private String id;
	private String contents;
	private UserDoc writer;
	private Date regDate;
	
	//-----------------------------------------------------------
	
	public MessageDoc() {
		//
	}
	
	public MessageDoc(Message message) {
		//
		this.id = message.getId();
		this.contents = message.getContents();
		this.writer = new UserDoc(message.getWriter());
		this.regDate = new Date();
	}
	//-----------------------------------------------------------
	
	public Message createDomain() {
		//
		Message message = new Message();
		message.setContents(contents);
		message.setId(id);
		message.setRegDate(regDate);
		message.setWriter(writer.createDomain());
		
		return message;
	}
	//-----------------------------------------------------------
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public UserDoc getWriter() {
		return writer;
	}
	public void setWriter(UserDoc writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
