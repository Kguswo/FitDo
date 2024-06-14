package com.fitdo.model.dto;

public class Post {

	private int postId;
    private String postUserId; // 외래키
    private int postGoalId;
	private String postContent;
	private int postLike;
	private String postImg;
	private String postRegDate;
	private User user;
	private int commentNum;

	public Post() {
	}

    public Post(int postId, String postUserId, int postGoalId, String postContent, int postLike, String postImg,
			String postRegDate, User user, int commentNum) {
		super();
		this.postId = postId;
		this.postUserId = postUserId;
		this.postGoalId = postGoalId;
		this.postContent = postContent;
		this.postLike = postLike;
		this.postImg = postImg;
		this.postRegDate = postRegDate;
		this.user = user;
		this.commentNum = commentNum;
	}

	public int getPostGoalId() {
		return postGoalId;
	}

	public void setPostGoalId(int postGoalId) {
		this.postGoalId = postGoalId;
	}

	public String getPostUserId() {
		return postUserId;
	}

	public void setPostUserId(String postUserId) {
		this.postUserId = postUserId;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public int getPostLike() {
		return postLike;
	}

	public void setPostLike(int postLike) {
		this.postLike = postLike;
	}

	public String getPostImg() {
		return postImg;
	}

	public void setPostImg(String postImg) {
		this.postImg = postImg;
	}

	public String getPostRegDate() {
		return postRegDate;
	}

	public void setPostRegDate(String postRegDate) {
		this.postRegDate = postRegDate;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

}
