package kr.co.studycafe.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.studycafe.event.EventDTO;
import net.utility.DBClose;
import net.utility.DBOpen;

public class MypageDAO {
	private DBOpen dbopen =null;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuilder sql = null;
	
	public MypageDAO() {
		System.out.println("==========MypageDAO() 시작");
		dbopen = new DBOpen();
	}

	
	public ArrayList<MypageDTO> seatlist(String uid){
		ArrayList<MypageDTO> seatlist = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT res_no, res_date, times, store_name, seat_code, prog ");
			sql.append(" FROM tb_seat_res JOIN tb_store_info ");
			sql.append(" ON tb_seat_res.store_no = tb_store_info.store_no ");
			sql.append(" WHERE in_email = ? ");
			sql.append(" ORDER BY res_no DESC ");
			
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, uid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				seatlist = new ArrayList<MypageDTO>();
				do {
					MypageDTO dto = new MypageDTO();
					dto.setRes_no(rs.getInt("res_no"));
					dto.setRes_date(rs.getString("res_date"));
					dto.setTimes(rs.getString("times"));
					dto.setStore_name(rs.getString("store_name"));
					dto.setSeat_code(rs.getString("seat_code"));
					dto.setProg(rs.getInt("prog"));
					seatlist.add(dto);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.println("seatlist 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		return seatlist;
	}// seatlist() end
	
	public ArrayList<MypageDTO> lockerlist(String uid){
		ArrayList<MypageDTO> lockerlist = null;
		try {
			con=dbopen.getConnection();
			sql=new StringBuilder();
			sql.append(" SELECT res_no, res_start_date, res_end_date, store_name, locker_code, prog ");
			sql.append(" FROM tb_locker_res JOIN tb_store_info ");
			sql.append(" ON tb_locker_res.store_no = tb_store_info.store_no ");
			sql.append(" WHERE in_email = ? ");
			sql.append(" ORDER BY res_no DESC ");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, uid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				lockerlist = new ArrayList<MypageDTO>();
				do {
					MypageDTO dto = new MypageDTO();
					dto.setRes_no(rs.getInt("res_no"));
					dto.setRes_date(rs.getString("res_start_date"));
					dto.setEnd_date(rs.getString("res_end_date"));
					dto.setStore_name(rs.getString("store_name"));
					dto.setSeat_code(rs.getString("locker_code"));
					dto.setProg(rs.getInt("prog"));
					lockerlist.add(dto);
				}while(rs.next());
			}
		}catch (Exception e) {
			System.out.println("lockerlist 실패 : "+e);
		}finally {
			DBClose.close(con, pstmt, rs);
		}
		return lockerlist;
	}// lockerlist() end
	
	public ArrayList<MypageDTO> couponlist(String uid) { //이벤트 당첨자 
		ArrayList<MypageDTO> couponlist = null;

		try {
			con = dbopen.getConnection();
			sql = new StringBuilder();
			sql.append(" SELECT e_title, e_money");
			sql.append(" from tb_event_prizewinner JOIN tb_event ");
			sql.append(" ON tb_event_prizewinner.e_number = tb_event.e_number ");
			sql.append(" WHERE in_email=? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				couponlist = new ArrayList<MypageDTO>();
				do {
					MypageDTO dto = new MypageDTO();
					dto.setE_title(rs.getString("e_title"));
					dto.setE_money(rs.getInt("e_money"));
					couponlist.add(dto); // couponlist에 모으기
				} while (rs.next());
			} // end
		} catch (Exception e) {
			System.out.println("이벤트 당첨자 목록 실패: " + e);
		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return couponlist;
	}

}

