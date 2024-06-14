package com.fitdo.model.dto;

public class Follow {
	private int followId;
	private String fromUserId;
	private String toUserId;
	private String profileImg;
	
	public Follow()	{
	}
	
	public Follow(int followId, String fromUserId, String toUserId, String profileImg) {
		super();
		this.followId = followId;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.profileImg = profileImg;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	@Override
	public String toString() {
		return "Follow [followId=" + followId + ", fromUserId=" + fromUserId + ", toUserId=" + toUserId
				+ ", profileImg=" + profileImg + "]";
	}
}
