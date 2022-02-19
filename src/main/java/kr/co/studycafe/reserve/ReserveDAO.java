package kr.co.studycafe.reserve;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.utility.DBClose;
import net.utility.DBOpen;


public class ReserveDAO {
	private DBOpen dbopen = null;
	private Connection con = null;
	private PreparedStatement pstmt=null;
	private ResultSet rs = null;
	private StringBuilder sql = null;
	private SimpleDateFormat sdformat = null;
	
	public ReserveDAO() {
		System.out.println("==================ReserveDAO() 시작");
		dbopen = new DBOpen();
	}
	
	public ArrayList<StoreDTO> storeList(){
		ArrayList<StoreDTO> storeList = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT store_no, store_name, store_address, room_count, desk_count, locker_count ");
			sql.append(" FROM tb_store_info ");
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				storeList=new ArrayList<StoreDTO>();
				do {
					StoreDTO dto = new StoreDTO();
					dto.setStore_no(rs.getInt("store_no"));
					dto.setStore_name(rs.getString("store_name"));
					dto.setStore_address(rs.getString("store_address"));
					dto.setRoom_count(rs.getInt("room_count"));
					dto.setDesk_count(rs.getInt("desk_count"));
					dto.setLocker_count(rs.getInt("locker_count"));
					
					storeList.add(dto);
				}while(rs.next());
				
			}
			
		}catch (Exception e){
			System.out.println("reserve view 실패 : " + e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return storeList;
	}
	
	public ArrayList<ReserveDTO> resList(int store_no, String res_date){// 예약내역 - 매장번호, 선택날짜, (좌석,룸) 
		ArrayList<ReserveDTO> resList =  new ArrayList<ReserveDTO>();
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT store_no, seat_code, times, res_date ");
			sql.append(" FROM tb_seat_res ");
			sql.append(" WHERE store_no=? AND pay_prog='Y' AND res_date=? ");
			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, store_no);
			pstmt.setString(2, res_date);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					ReserveDTO dto = new ReserveDTO();
					dto.setStore_no(rs.getInt("store_no"));
					dto.setSeat_code(rs.getString("seat_code"));
					dto.setTimes(rs.getString("times"));
					dto.setRes_date(rs.getString("res_date"));
					resList.add(dto);
				}while(rs.next());
			}
			
			sql=new StringBuilder();
			sql.append(" SELECT store_no, locker_code, res_start_date, res_end_date ");
			sql.append(" FROM tb_locker_res ");
			sql.append(" WHERE store_no=? AND pay_prog='Y' AND date_format(res_end_date,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d') ");
			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, store_no);
			pstmt.setString(2, res_date);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				//resList = new ArrayList<ReserveDTO>();
				do {
					ReserveDTO dto = new ReserveDTO();
					dto.setStore_no(rs.getInt("store_no"));
					dto.setLocker_code(rs.getString("locker_code"));
					dto.setRes_start_date(rs.getString("res_start_date"));
					dto.setRes_end_date(rs.getString("res_end_date"));
					resList.add(dto);
				}while(rs.next());
			}
			
		}catch (Exception e) {
			System.out.println("resList 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		
		return resList;
	}
	
	
	//예약내역 확인 
	public int resChk(int store_no, String seat_code, String res_date, String times) {
		int cnt = 0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			if(times != null) { //사물함 아닐때 
				sql.append(" SELECT seat_code, res_date, times ");
				sql.append(" FROM tb_seat_res ");
				sql.append(" WHERE store_no=? AND seat_code=? AND res_date=? AND pay_prog='Y' AND times LIKE '%"+times+"%' ");
			}else {
				sql.append(" SELECT locker_code, res_start_date ");
				sql.append(" FROM tb_locker_res ");
				sql.append(" WHERE store_no=? AND locker_code=? AND res_start_date=? AND pay_prog='Y' ");
			}

			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1, store_no);
			pstmt.setString(2, seat_code);
			pstmt.setString(3, res_date);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				cnt++;
			}
		}catch (Exception e) {
			System.out.println("resChk 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		return cnt;
	}
	
	//좌석 예약 
	public int resSeatIns(String in_email, int store_no, String seat_code, String res_date, String times, int total) {
		int cnt=0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" INSERT INTO tb_seat_res(in_email,store_no,seat_code,res_date,times,total) ");
			sql.append(" VALUES (?,?,?,?,?,?) ");
			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, in_email);
			pstmt.setInt(2, store_no);
			pstmt.setString(3, seat_code);
			pstmt.setString(4, res_date);
			pstmt.setString(5, times);
			pstmt.setInt(6, total);
			
			cnt=pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("resIns실패 : "+e);
		}finally {
			DBClose.close(con, pstmt);
		}
		return cnt;
	}
	
	//사물함 예약 
	public int resLockerIns(String in_email, int store_no, String seat_code, String res_start_date, String res_end_date, int total) {
		int cnt=0;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" INSERT INTO tb_locker_res(in_email,store_no,locker_code,res_start_date,res_end_date,total) ");
			sql.append(" VALUES (?,?,?,?,?,?) ");
			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, in_email);
			pstmt.setInt(2, store_no);
			pstmt.setString(3, seat_code);
			pstmt.setString(4, res_start_date);
			pstmt.setString(5, res_end_date);
			pstmt.setInt(6, total);
			
			cnt=pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("resLockerIns실패 : "+e);
		}finally {
			DBClose.close(con, pstmt);
		}
		return cnt;
	}
	
	
}
