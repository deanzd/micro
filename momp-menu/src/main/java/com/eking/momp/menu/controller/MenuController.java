package com.eking.momp.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eking.momp.menu.dto.MenuDTO;
import com.eking.momp.menu.service.MenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/menus")
@Api(tags = "菜单")
@RestController
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@GetMapping("")
	@ApiOperation("获取菜单树")
	public List<MenuDTO> listMenus() {
        return menuService.list();
	}

	@GetMapping("/test")
	public void testSeata() {
		menuService.testSeata();
	}
}
