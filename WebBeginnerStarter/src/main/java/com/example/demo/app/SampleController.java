package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		
		String sql = "SELECT id, name, email "
				+ "FROM inquiry WHERE id = 1";
		
		String sql2 = "SELECT id, name, email "
				+ "FROM inquiry WHERE id = 2";

		Map<String, Object> map = jdbcTemplate.queryForMap(sql);

		Map<String, Object> map2 = jdbcTemplate.queryForMap(sql2);

		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));
		
		model.addAttribute("name2", map2.get("name"));
		model.addAttribute("email2", map2.get("email"));

		model.addAttribute("title", "Inquiry Form");
		return "test";
	}

}
