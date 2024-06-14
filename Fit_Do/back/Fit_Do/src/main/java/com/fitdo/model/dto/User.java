package com.fitdo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 DTO")
public class User {
	private int id;
	private String userid;
	private String password;
	private String birth;
	private String name;
	private String gender;
	private String profileImg; // 중복이름 방지
	private String orgprofileImg; // 원본 파일이름
	private String mbti;

	public User() {
	}

	public User(String userid, String password, String birth, String name, String gender, String profileImg,
			String orgprofileImg, String mbti) {
		this.id = id;
		this.userid = userid;
		this.password = password;
		this.birth = birth;
		this.name = name;
		this.gender = gender;
		this.profileImg = profileImg;
		this.orgprofileImg = orgprofileImg;
		this.mbti = mbti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getOrgprofileImg() {
		return orgprofileImg;
	}

	public void setOrgprofileImg(String orgprofileImg) {
		this.orgprofileImg = orgprofileImg;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password + ", birth=" + birth + ", name=" + name + ", gender="
				+ gender + ", profileImg=" + profileImg + ", orgprofileImg=" + orgprofileImg + ", mbti=" + mbti + "]";
	}
	
}
