package kr.co.studycafe.place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.utility.DBClose;
import net.utility.DBOpen;

public class PlaceDAO {
	private DBOpen dbopen =null;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuilder sql = null;
	
	public PlaceDAO() {
		dbopen = new DBOpen();
	}
	
	public ArrayList<PlaceDTO> list(String keyword){
		ArrayList<PlaceDTO> list = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			keyword=keyword.trim();
			System.out.println(keyword);
			if(keyword.length() == 0) { //검색하지 않은 경우
				sql.append(" SELECT store_no, en_email, store_name, store_address, operating_hours, store_img, tel, ");
				sql.append(" latitude, longitude, IFNULL(ROUND(avg(rev_score)),0) AS score_avg, COUNT(rev_score) AS review_tot ");
				sql.append(" FROM( ");
				sql.append(" 	SELECT ST.store_no, ST.en_email, ST.store_name, ST.store_address, ST.operating_hours, "); 
				sql.append("		ST.store_img, ST.tel, ST.latitude, ST.longitude, RE.rev_score ");
				sql.append("		FROM tb_store_info ST LEFT JOIN tb_review RE ");
				sql.append("		on ST.store_no = RE.store_no ");
				sql.append("	)tb_STRE ");
				sql.append(" group by store_no ");
			}else {
				sql.append(" SELECT store_no, en_email, store_name, store_address, operating_hours, store_img, tel, ");
				sql.append(" latitude, longitude, IFNULL(ROUND(avg(rev_score)),0) AS score_avg, COUNT(rev_score) AS review_tot ");
				sql.append(" FROM( ");
				sql.append(" 	SELECT ST.store_no, ST.en_email, ST.store_name, ST.store_address, ST.operating_hours, "); 
				sql.append("		ST.store_img, ST.tel, ST.latitude, ST.longitude, RE.rev_score ");
				sql.append("		FROM tb_store_info ST LEFT JOIN tb_review RE ");
				sql.append("		on ST.store_no = RE.store_no ");
				sql.append("	)tb_STRE ");
				sql.append(" WHERE store_name LIKE '%"+keyword+"%' ");
				sql.append(" group by store_no ");
			}
			
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<PlaceDTO>();
				do {
					PlaceDTO dto = new PlaceDTO();
					dto.setStore_no(rs.getInt("store_no"));
					dto.setEn_email(rs.getString("en_email"));
					dto.setStore_name(rs.getString("store_name"));
					dto.setStore_address(rs.getString("store_address"));
					dto.setOperating_hours(rs.getString("operating_hours"));
					dto.setStore_img(rs.getString("store_img"));
					dto.setTel(rs.getString("tel"));
					dto.setLatitude(rs.getDouble("latitude"));
					dto.setLongitude(rs.getDouble("longitude"));
					dto.setScore_avg(rs.getInt("score_avg"));
					dto.setReview_tot(rs.getInt("review_tot"));
					
					list.add(dto);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.println("placeDAO list 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		return list;
	}// list() end
	
	public ArrayList<PlaceDTO> revlist(int store_no){
		ArrayList<PlaceDTO> revlist = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT rev_number, store_no, rev_title, rev_content, rev_image, in_email, rev_score, rev_date ");
			sql.append(" FROM tb_review ");
			sql.append(" WHERE store_no = ? ");
			sql.append(" ORDER BY rev_number DESC ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, store_no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				revlist = new ArrayList<PlaceDTO>();
				do {
					PlaceDTO dto = new PlaceDTO();
					dto.setStore_no(rs.getInt("store_no"));
					dto.setRev_number(rs.getInt("rev_number"));
					dto.setRev_title(rs.getString("rev_title"));
					dto.setRev_content(rs.getString("rev_content"));
					dto.setRev_image(rs.getString("rev_image"));
					dto.setIn_email(rs.getString("in_email"));
					dto.setRev_score(rs.getInt("rev_score"));
					dto.setRev_date(rs.getString("rev_date"));
					revlist.add(dto);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.println("revlist 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		return revlist;
	}// revlist() end
	
	public int write(PlaceDTO dto) {
		int cnt=0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" INSERT INTO tb_review(store_no, rev_title, rev_content, rev_image, in_email, rev_score, rev_date) ");
			sql.append(" VALUES(?, ?, ?, ?, ?, ?, now()); ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, dto.getStore_no());
			pstmt.setString(2, dto.getRev_title());
			pstmt.setString(3, dto.getRev_content());
			pstmt.setString(4, dto.getRev_image());
			pstmt.setString(5, dto.getIn_email());
			pstmt.setInt(6, dto.getRev_score());
			
			cnt=pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("place write 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt);
		}
		return cnt;
	}// write()
	
	public PlaceDTO read(int rev_number) {
		PlaceDTO dto = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT rev_number, store_no, rev_title, rev_content, rev_image, in_email, rev_score, rev_date ");
			sql.append(" FROM tb_review ");
			sql.append(" WHERE rev_number=? ");
			sql.append(" ORDER BY rev_number DESC ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, rev_number);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto = new PlaceDTO();
				dto.setRev_number(rs.getInt("rev_number"));
				dto.setStore_no(rs.getInt("store_no"));
				dto.setRev_title(rs.getString("rev_title"));
				dto.setRev_content(rs.getString("rev_content"));
				dto.setRev_image(rs.getString("rev_image"));
				dto.setIn_email(rs.getString("in_email"));
				dto.setRev_score(rs.getInt("rev_score"));
				dto.setRev_date(rs.getString("rev_date"));
			}
			
		}catch (Exception e) {
			System.out.println("place read 실패 : " + e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return dto;
	}
	
	public int modify(PlaceDTO dto) {
		int cnt=0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" UPDATE tb_review SET rev_title=?, rev_content=?, rev_image=? ");
			sql.append(" WHERE rev_number = ? ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getRev_title());
			pstmt.setString(2, dto.getRev_content());
			pstmt.setString(3, dto.getRev_image());
			pstmt.setInt(4, dto.getRev_number());
			cnt=pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("place modify 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt);
		}
		
		return cnt;
		
	}
	
	public int delete(int rev_number) {
		int cnt=0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" DELETE FROM tb_review ");
			sql.append(" WHERE rev_number = ? ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, rev_number);
			cnt=pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("place delete 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt);
		}
		
		return cnt;
		
	}
	
	
}

