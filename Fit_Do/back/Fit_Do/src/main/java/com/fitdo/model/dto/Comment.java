package com.fitdo.model.dto;

public class Comment {

	private int commentId;
	private String commentContent;
	private String commentRegDate;
	private String commentUserId;
	private String commentPostId;
	private User user;

	public Comment() {
	}

	public Comment(int commentId, String commentContent, String commentRegDate, String commentUserId,
			String commentPostId, User user) {
		super();
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.commentRegDate = commentRegDate;
		this.commentUserId = commentUserId;
		this.commentPostId = commentPostId;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getCommentPostId() {
		return commentPostId;
	}

	public void setCommentPostId(String commentPostId) {
		this.commentPostId = commentPostId;
	}

	public String getCommentRegDate() {
		return commentRegDate;
	}

	public void setCommentRegDate(String commentRegDate) {
		this.commentRegDate = commentRegDate;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", commentContent=" + commentContent + ", commentRegDate="
				+ commentRegDate + ", commentUserId=" + commentUserId + ", commentPostId=" + commentPostId + "]";
	}

}
