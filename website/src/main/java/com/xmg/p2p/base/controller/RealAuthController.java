package com.xmg.p2p.base.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UploadUtil;

/**
 * Created by lanxw on 2017/7/27. 前台的实名认证控制器
 */
@Controller
public class RealAuthController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private ServletContext ctx;

	/**
	 * 实名认证首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("realAuth")
	public String realAuth(Model model) {
		// 拿到当前的userinfo对象.
		Userinfo current = userinfoService.getCurrent();
		// 判断userinfo是否已经实名认证
		if (current.getIsRealAuth()) {
			// 如果已经实名认证,拿到userinfo上的realAuthId查询对应的实名认证信息,跳转结果页面.realAuth_result.ftl
			model.addAttribute("realAuth", realAuthService.get(current.getRealAuthId()));
			return "realAuth_result";
		} else {
			// 如果没有实名认证,需要判断userinfo中的realAuthID是否为null
			if (current.getRealAuthId() == null) {
				// 如果为null,说明还没有提交实名认证的流程/实名认证的流程已经被拒绝,跳转到realAuth.ftl
				return "realAuth";
			} else {
				// 如果不为null,表示已经待审核的状态,跳转到待审核的页面.添加auditing=true的信息,跳转到realAuth_result.ftl
				model.addAttribute("auditing", true);
				return "realAuth_result";
			}
		}
	}

	/**
	 * 实名认证申请
	 * 
	 * @param realAuth
	 * @return
	 */
	@RequestMapping("realAuth_save")
	@ResponseBody
	public AjaxResult realAuthSave(RealAuth realAuth) {
		AjaxResult result = new AjaxResult();
		try {
			realAuthService.save(realAuth);
		} catch (Exception e) {
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 实名认证上传图片
	 * 
	 */
	@RequestMapping("uploadImg")
	@ResponseBody
	public String uploadImg(MultipartFile img) {
		String fileName = UploadUtil.upload(img, ctx.getRealPath("/upload"));
		return "/upload/" + fileName;
	}

}
