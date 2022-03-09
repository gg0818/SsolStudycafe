package kr.co.studycafe.mypage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.co.studycafe.event.EventDAO;
import kr.co.studycafe.event.EventDTO;
import kr.co.studycafe.join.JoinDAO;
import kr.co.studycafe.question.StoreDAO;
import kr.co.studycafe.question.StoreDTO;

@Controller
public class MypageCont {

	private MypageDAO dao = null;
	JoinDAO joindao = new JoinDAO();
	EventDAO eventdao = new EventDAO();
	StoreDAO storedao = new StoreDAO();

	public MypageCont() {
		dao = new MypageDAO();
		System.out.println("-----MypageCont()객체 생성됨");
	}

	@RequestMapping("/mypage.do")
	public ModelAndView mypageList(@SessionAttribute(name = "uid", required = false) String uid) {
		ModelAndView mav = new ModelAndView();
		String userType = joindao.userType(uid);
		if (userType.equals("en")) {
			mav.setViewName("/mypage/mypage_en");
			ArrayList<StoreDTO> list=storedao.storeInfolist(uid);
			ArrayList<EventDTO> eventlist = eventdao.eventInfolist(uid);
			
			mav.addObject("userInfo", joindao.userInfo_en(uid));
			mav.addObject("eventlist", eventlist);
			mav.addObject("storelist", list);
			
		} else {
			System.out.println(userType);
			mav.setViewName("/mypage/mypage_in");
			
			mav.addObject("userInfo", joindao.userInfo_in(uid));
			mav.addObject("seatlist", dao.seatlist(uid));
			mav.addObject("lockerlist", dao.lockerlist(uid));
			mav.addObject("couponlist", dao.couponlist(uid));
		}return mav; 	
	}

	@RequestMapping("/mypage/place_add.do")
	public String placeAdd() {
		return "/mypage/placeForm"; 
	}

	@RequestMapping(value = "/mypage/place_add.do", method = RequestMethod.POST)
	public String placeAddProc(@SessionAttribute(name = "uid", required = false) String uid, StoreDTO dto,
			HttpServletRequest req) {
		//ModelAndView mav = new ModelAndView();
		dto.setEn_email(uid);

		// 임시
		dto.setLatitude(37.56947854892087);
		dto.setLongitude(126.98597441116262);
		dto.setStore_img("image1.png");

		System.out.println(dto);
		System.out.println("결과 : " + storedao.write(dto));
		//mav.setViewName("/mypage/mypage_en");
		//mav.addObject("userInfo", joindao.userInfo_en(uid));
		return "redirect:/mypage.do"; 
	}

}