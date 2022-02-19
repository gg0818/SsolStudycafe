package kr.co.studycafe.place;

import org.springframework.web.multipart.MultipartFile;

public class PlaceDTO {
	private int store_no;
	private String en_email;
	private String store_name;
	private String store_address;
	private String operating_hours;
	private double latitude;
	private double longitude;
	private String store_img;
	private String tel;
	private MultipartFile store_imgMF;
	private int score_avg;
	private int review_tot;
	
	// 리뷰 테이블 
	private int rev_number;
	private String rev_title;
	private String rev_content;
	private String rev_image;
	private String in_email;
	private int rev_score;
	private String rev_date;
	private MultipartFile revimgMF;
	
	
	public PlaceDTO() {}

	public int getStore_no() {
		return store_no;
	}

	public void setStore_no(int store_no) {
		this.store_no = store_no;
	}

	public String getEn_email() {
		return en_email;
	}

	public void setEn_email(String en_email) {
		this.en_email = en_email;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public String getOperating_hours() {
		return operating_hours;
	}

	public void setOperating_hours(String operating_hours) {
		this.operating_hours = operating_hours;
	}
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public String getStore_img() {
		return store_img;
	}

	public void setStore_img(String store_img) {
		this.store_img = store_img;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public MultipartFile getStore_imgMF() {
		return store_imgMF;
	}

	public void setStore_imgMF(MultipartFile store_imgMF) {
		this.store_imgMF = store_imgMF;
	}
	
	public MultipartFile getRevimgMF() {
		return revimgMF;
	}

	public void setRevimgMF(MultipartFile revimgMF) {
		this.revimgMF = revimgMF;
	}

	public int getScore_avg() {
		return score_avg;
	}

	public void setScore_avg(int score_agv) {
		this.score_avg = score_agv;
	}

	public int getReview_tot() {
		return review_tot;
	}

	public void setReview_tot(int review_tot) {
		this.review_tot = review_tot;
	}

	public int getRev_number() {
		return rev_number;
	}

	public void setRev_number(int rev_number) {
		this.rev_number = rev_number;
	}

	public String getRev_title() {
		return rev_title;
	}

	public void setRev_title(String rev_title) {
		this.rev_title = rev_title;
	}

	public String getRev_content() {
		return rev_content;
	}

	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}

	public String getRev_image() {
		return rev_image;
	}

	public void setRev_image(String rev_image) {
		this.rev_image = rev_image;
	}

	public String getIn_email() {
		return in_email;
	}

	public void setIn_email(String in_email) {
		this.in_email = in_email;
	}

	public int getRev_score() {
		return rev_score;
	}

	public void setRev_score(int rev_score) {
		this.rev_score = rev_score;
	}

	public String getRev_date() {
		return rev_date;
	}

	public void setRev_date(String rev_date) {
		this.rev_date = rev_date;
	}
	
}// class end 
