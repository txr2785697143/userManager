package com.example.usermanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usermanager.entity.User;
import com.example.usermanager.vo.R;
import java.io.IOException;
import java.io.OutputStream;

public interface UserService extends IService<User> {

    R getUserList(String name, int current, int size);

    R addUser(User user);

    R updateUser(User user);

    R deleteUser(String name);

    void exportExcel(OutputStream out, String excelTitle, String name, int current, int size) throws IOException;
}
