package cn.zm.trip.web.controller;

import cn.zm.trip.web.dao.HotelDao;
import cn.zm.trip.web.domain.*;
import cn.zm.trip.web.service.ViewPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "hotel")
public class HotelController {
	@Autowired
	private HotelDao hotelDao;
	@Autowired
	private ViewPointService viewPointService;

	/**
	 * 跳转首页
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		//实例化hotel examle
		HotelExample example = new HotelExample();
		example.setOrderByClause("hid desc");
		String prefix = "/static/upload/hotel/";
		List<Hotel> hotels = hotelDao.selectByExample(example);
		for (Hotel hotel : hotels) {
			//图片名
			String suffix = hotel.getImgUrl();
			//全路径
			hotel.setImgUrl(prefix + suffix);
		}
		//传送景点
		model.addAttribute("hotels", hotels);
		return "proscenium/hotel/index";
	}

	/**
	 * 跳转首页
	 */
	@RequestMapping(value = "content", method = RequestMethod.GET)
	public String content(Integer hid, Model model) {

		//封装留言信息
		List<Words> lw_list = viewPointService.findByWords();
		model.addAttribute("lw_list",lw_list);

		//封装回复信息
		List<Reply> lr_list = viewPointService.findByReply();
		model.addAttribute("lr_list",lr_list);

		Hotel hotel = hotelDao.selectByPrimaryKey(hid);
		model.addAttribute("hotel", hotel);

		return "proscenium/hotel/content";
	}

}