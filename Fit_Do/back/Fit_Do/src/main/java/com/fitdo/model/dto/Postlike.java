package com.fitdo.model.dto;

public class Postlike {
	private int postlikeId;
	private String postlikeUserId;
	private int postlikePostId;
	private String postlikeUserImg;
	
	public Postlike() {
	}
	
	public Postlike(int postlikeId, String postlikeUserId, int postlikePostId, String postlikeUserImg) {
		super();
		this.postlikeId = postlikeId;
		this.postlikeUserId = postlikeUserId;
		this.postlikePostId = postlikePostId;
		this.postlikeUserImg = postlikeUserImg;
	}

	public int getPostlikeId() {
		return postlikeId;
	}

	public void setPostlikeId(int postlikeId) {
		this.postlikeId = postlikeId;
	}

	public String getPostlikeUserId() {
		return postlikeUserId;
	}

	public void setPostlikeUserId(String postlikeUserId) {
		this.postlikeUserId = postlikeUserId;
	}

	public int getPostlikePostId() {
		return postlikePostId;
	}

	public void setPostlikePostId(int postlikePostId) {
		this.postlikePostId = postlikePostId;
	}

	public String getPostlikeUserImg() {
		return postlikeUserImg;
	}

	public void setPostlikeUserImg(String postlikeUserImg) {
		this.postlikeUserImg = postlikeUserImg;
	}

	@Override
	public String toString() {
		return "Postlike [postlikeId=" + postlikeId + ", postlikeUserId=" + postlikeUserId + ", postlikePostId="
				+ postlikePostId + ", postlikeUserImg=" + postlikeUserImg + "]";
	}

}
