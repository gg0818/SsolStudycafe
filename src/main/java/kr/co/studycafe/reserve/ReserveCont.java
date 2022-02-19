package kr.co.studycafe.reserve;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.util.SystemInfo;


@Controller
public class ReserveCont {
	private ReserveDAO dao = null;
	private CalendarUse cal = null;
	
	public ReserveCont() {
		dao = new ReserveDAO();
		System.out.println("====================reserveCont()생성");
	}
	
	@RequestMapping("/reserve/reserve.do")
	ModelAndView reserveView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/reserve/view");
		mav.addObject("storeList", dao.storeList());
		return mav;
	}
	
	@RequestMapping(value = "/calendarUse", method = RequestMethod.GET)
	@ResponseBody
	public Object calStart() throws Exception {
		cal = new CalendarUse();
		Map<String, String> map = new HashMap<String, String>();
		try {
			GregorianCalendar calS = new GregorianCalendar();
			
			//달력 지정 년, 월 초기 값
			map.put("nowYear", Integer.toString(calS.get(Calendar.YEAR)));
			map.put("nowMonth", Integer.toString(calS.get(Calendar.MONTH)+1));
			map.put("nowDate", Integer.toString(calS.get(Calendar.DATE)));
			
			calS.set(calS.get(Calendar.YEAR), calS.get(Calendar.MONTH), 1);
			map.put("start", Integer.toString(calS.get(Calendar.DAY_OF_WEEK)));
			map.put("calTot", Integer.toString(calS.getActualMaximum(Calendar.DATE)));
			
			map.put("week4", cal.weekData(4));
			map.put("week8", cal.weekData(8));
			map.put("week12", cal.weekData(12));
		} catch (Exception e) {
			System.out.println("calStart 실패 : "+e);
		}
		
		return map;
	}
	
	@RequestMapping(value = "/calendarUse", method = RequestMethod.POST)
	@ResponseBody
	public Object calView(int year, int month) throws Exception {
		cal = new CalendarUse();
		CalendarDTO caldto = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			GregorianCalendar calU = new GregorianCalendar();
			map.put("nowYear", Integer.toString(calU.get(Calendar.YEAR)));
			map.put("nowMonth", Integer.toString(calU.get(Calendar.MONTH)+1));
			map.put("nowDate", Integer.toString(calU.get(Calendar.DATE)));
			caldto = cal.calSet(year, month);
			map.put("start", Integer.toString(caldto.getStart()));
			map.put("calTot", Integer.toString(caldto.getCalTot()));
		} catch (Exception e) {
			System.out.println("calview 실패 : "+e);
		}
		
		return map;
	}
	
	@RequestMapping(value = "/reserve", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<StoreDTO> resAjax() throws Exception {//매장정보
		ArrayList<StoreDTO> storeList = null;
		try {
			storeList = dao.storeList(); 
		} catch (Exception e) {
			System.out.println("resAjax 실패 : "+e);
		}
		
		return storeList;
	}
	
	@RequestMapping(value = "/reserveState", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ReserveDTO> resListAjax(int store_no, String res_date) throws Exception{
		ArrayList<ReserveDTO> resList = null;
		try {
			resList = dao.resList(store_no, res_date);
		}catch (Exception e) {
			System.out.println("resListAjax 실패 : "+e);
		}
		
		return resList;
	}	
	
	@RequestMapping(value = "/resChk", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public int resChkAjax(@RequestBody List<ReserveDTO> updateDto) throws Exception{
		int cnt=0;
		try {			
			updateDto.toString();
			
			int store_no = updateDto.get(0).getStore_no();
			for(int i=0; i<updateDto.size(); i++) {
				String seat_code=updateDto.get(i).getSeat_code();
				String res_date=updateDto.get(i).getRes_date();
				String times=updateDto.get(i).getTimes();
				
				cnt += dao.resChk(store_no,seat_code,res_date,times);
			}
		}catch (Exception e) {
			System.out.println("resListAjax 실패 : "+e);
		}
		
		return cnt;
	}
	
	
	@RequestMapping(value = "/reserveIns", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public int resIns(@RequestBody List<ReserveDTO> reserveDTO) throws Exception{
		int cnt=0;
		try {			
			reserveDTO.toString();
			String in_email, seat_code, res_date, times, res_end_date;
			int store_no, total;
			for(int i=0; i<reserveDTO.size(); i++) {
				in_email=reserveDTO.get(i).getIn_email();
				store_no=reserveDTO.get(i).getStore_no();
				seat_code=reserveDTO.get(i).getSeat_code();
				res_date=reserveDTO.get(i).getRes_date();
				total = reserveDTO.get(i).getTotal();
				if(seat_code.indexOf("locker") != 0) {
					times=reserveDTO.get(i).getTimes();
					cnt = dao.resSeatIns(in_email,store_no,seat_code,res_date,times,total);
				}else {
					res_end_date=reserveDTO.get(i).getRes_end_date();
					cnt = dao.resLockerIns(in_email,store_no,seat_code,res_date,res_end_date,total);
				}
			}
		}catch (Exception e) {
			System.out.println("resListAjax 실패 : "+e);
		}
		
		return cnt;
	}
	
}
