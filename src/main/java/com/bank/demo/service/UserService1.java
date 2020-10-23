package com.bank.demo.service;
//package com.example.demo.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.common.CommonConstants;
//import com.example.demo.model.Company;
//import com.example.demo.model.CompanyBean;
//import com.example.demo.model.Response;
//import com.example.demo.model.User;
//import com.example.demo.model.UserBean;
//import com.example.demo.repository.AccountRepo;
//import com.example.demo.repository.UserRepo;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//@Transactional
//public class UserService1 {
//
//	@Autowired
//	private UserRepo userRepo;
//	@Autowired
//	private AccountRepo cRepo;
//
//	private static final  ObjectMapper om = new ObjectMapper();
//
//
//	public List<UserBean> listUser() {
//
//		List<User> user =  userRepo.findAll();
//		List<CompanyBean> clist = new ArrayList<>();
//		List<UserBean> ulist = new ArrayList<>();
//
//		
//		for(User u : user) {
//			
//			UserBean ub = new UserBean();
//			
//			ub.setName(u.getName());
//			ub.setEmail(u.getEmail());
//			ub.setCity(u.getCity());
//			
//			List<Company> comp = u.getCompany();
//			for(Company c :comp) {
//				CompanyBean cb = new CompanyBean();
//				cb.setCity(c.getCity());
//				cb.setName(c.getName());
//				clist.add(cb);
//			}
//			ub.setCompany(clist);
//			ulist.add(ub);
//		}
//		return  ulist;
//		
//
//	}
//
//	public List<User> getUserForCompany(String company) {
//
//
//		return userRepo.findByCompanyName(company);
//	}
//
//
//	public List<User> addCompany(UserBean userBean, String u) {
//		List<CompanyBean> company = userBean.getCompany();
//		List<Company> comp = new ArrayList<>();
//		User user = userRepo.findByName(u);
//		for(CompanyBean companybean : company) {
//			Company c = new Company();
//			c.setName(companybean.getName());
//			c.setCity(companybean.getCity());
//			c.setUser(user);
//			comp.add(c);
//		}
//		user.setCompany(comp);
//		userRepo.save(user);
//		return this.userRepo.findAll();
//	}
//
//	public String addCompanyMapper(String userBean, String u) throws JsonProcessingException {
//		Response res = new Response();
//		try {
//			UserBean ub = om.readValue(userBean, UserBean.class);
//			List<CompanyBean> company = ub.getCompany();
//			User user = this.userRepo.findByName(u);
//			List<Company> comp = new ArrayList<>();
//
//			if(user != null ) {
//
//				for(CompanyBean cb : company) {
//					Company c = new Company();
//					c.setName(cb.getName());
//					c.setCity(cb.getCity());
//					c.setUser(user);
//					comp.add(c);
//				}
//				user.setCompany(comp);
//				this.userRepo.save(user);
//				res.setStatus(CommonConstants.SUCCESS);
//				res.setMessage("Company added successfully");
//				
//			} else {
//				res.setStatus(CommonConstants.FAIL);
//				res.setMessage("User Not Found");
//			}
//
//
//		} catch(Exception e) {
//			res.setMessage("Exception Occurs");
//			res.setStatus("FAIL");
//			e.printStackTrace();
//		}
//		
//		String message = om.writeValueAsString(res);
//
//		return message;
//	}
//	public Response delete(String company) {
//		Response res = new Response();
//		try {
//			Company c = this.cRepo.findByName(company);
//			if(c != null) {
//				this.cRepo.deleteByName(company);
//				res.setStatus("SUCCESS");
//				res.setMessage("deleted sucessfully");
//			} else {
//				res.setStatus(CommonConstants.FAIL);
//				res.setMessage("Company Not Found");
//			}
//
//		} catch(Exception e) {
//			res.setMessage("Exception Occurs");
//			res.setStatus("FAIL");
//			e.printStackTrace();
//		}
//
//		return res;
//	}
//
//
//
//}
