package com.namoo.social.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.Message;

@Document(collection="timelines")
public class TimeLineDoc {
	//
	@Id
	private String id;
	@Indexed
	private String ownerId;
	private MessageDoc message;
	
	
	//--------------------------------------------------------------------
	
	public TimeLineDoc() {
		//
	}

	public TimeLineDoc(String ownerId, Message message) {
		//
		this.ownerId = ownerId;
		this.message = new MessageDoc(message);
	}
	
	//--------------------------------------------------------------------
	
	public Message createDomain() {
		//
		Message msgDom = new Message();
		msgDom.setContents(message.getContents());
		msgDom.setId(message.getId());
		msgDom.setRegDate(message.getRegDate());
		msgDom.setWriter(message.getWriter().createDomain());
		
		return msgDom;
	}
	
	//--------------------------------------------------------------------
	// getter, setter
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
