package com.example.usermanager.controller;

import com.example.usermanager.entity.User;
import com.example.usermanager.service.UserService;
import com.example.usermanager.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public R getUserByName(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int size) {
        log.info("查询用户信息……");
        return userService.getUserList(name, current, size);
    }

    @PostMapping("/addUser")
    public R addUser(@RequestBody User user) {
        log.info("新增用户信息……");
        return userService.addUser(user);
    }

    @PutMapping("/updateUser")
    public R updateUser(@RequestBody User user) {
        log.info("更新用户信息……");
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser")
    public R deleteUser(@RequestParam String name) {
        log.info("删除用户信息……");
        return userService.deleteUser(name);
    }

    @GetMapping("/exportExcel/{name}/{current}/{size}/{excelTitle}")
    public Object exportExcel(
            HttpServletResponse response,
            @PathVariable String excelTitle,
            @PathVariable String name,
            @PathVariable int current,
            @PathVariable int size
    ) throws IOException {
        log.info("下载用户信息Excel表...");
        // 创建导出文件名称 前台传递过来的标题名（excelTitle）
        String fileName = excelTitle+".xls";
        // 设置返回的消息头和返回值类型 并设置编码 不设置编码文件名为中文的话 不会显示
        // 当设置成如下返回值时，浏览器才会执行下载文件动作
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");
        // 创建输出流，调用service中exportTest方法，参数：输出流 标题名
        userService.exportExcel(response.getOutputStream(), excelTitle, name, current, size);
        return null;
    }
}
