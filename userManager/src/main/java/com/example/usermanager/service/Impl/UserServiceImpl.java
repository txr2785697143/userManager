package com.example.usermanager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usermanager.entity.User;
import com.example.usermanager.mapper.UserMapper;
import com.example.usermanager.service.UserService;
import com.example.usermanager.util.ExportExcel;
import com.example.usermanager.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     *  分页条件查询用户名
     */
    @Override
    public R getUserList(String name, int current, int size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(current, size);
        if(name!=null && !name.isEmpty()){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");  // 指定根据id倒序
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        return R.ok().data("userPage", userPage);
    }

    /**
     * 新增用户
     */
    @Override
    public R addUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName());
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1 != null) {
            return R.error().setMessage("该用户名已存在");
        }
        user.setUpdateTime(GMT8Time());
        userMapper.insert(user);
        return R.ok().setMessage("新增成功！");
    }

    /**
     * 更新用户
     */
    @Override
    public R updateUser(User user) {
        // 根据用户名查询是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName());
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1 != null && user1.getId() != user.getId()) {
            return R.error().setMessage("该用户名已存在");
        }
        // 不重复就根据id来修改用户信息
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", user.getId());
        user.setUpdateTime(GMT8Time());
        userMapper.update(user, queryWrapper1);
        return R.ok().setMessage("更新成功！");
    }

    /**
     * 删除用户
     */
    @Override
    public R deleteUser(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return R.error().setMessage("没有该用户");
        }
        userMapper.deleteById(user.getId());
        return R.ok().setMessage("删除成功");
    }

    /**
     *  导出数据为excel
     */
    @Override
    public void exportExcel(OutputStream out, String excelTitle, String name, int current, int size) throws IOException {
        // 定义列标 就是一个Excel的每一个字段标题
        String[] rowsName = new String[]{"id", "用户名", "年龄", "性别", "家庭地址", "更新时间"};
        // 创建导出数据集合 后续会将dataList中的数据写到Excel
        List<Object[]> dataList = new ArrayList<>();

        // 从数据库查询用户列表（需要的可以自行添加参数）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(current, size);
        if(name!=null && !name.isEmpty() && !" ".equals(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");  // 指定根据id倒序
        List<User> userList = userMapper.selectPage(page, queryWrapper).getRecords();

        User user = null;
        // 将列表信息封装到一个Object数组
        // 我这里封装Object数组 是为了方便后续代码复用,不会将对象类型写死
        for (int i=0; i<userList.size(); i++){
            // 将数据库查到的每条数据 循环封装到Object[]
            user=userList.get(i);
            // 格式化时间
            // 将Date转换为Instant
            Instant instant = user.getUpdateTime().toInstant();
            // 获取系统默认时区
            ZoneId zoneId = ZoneId.systemDefault();
            // 将Instant转换为当前时区的ZonedDateTime
            ZonedDateTime zdt = instant.atZone(zoneId);
            // 减去8个小时
            ZonedDateTime zdtMinus8Hours = zdt.minusHours(8);
            // 格式化输出
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = zdtMinus8Hours.format(formatter);
            Object[] objs = new Object[]{user.getId(),user.getName(),user.getAge(),user.getGender(),user.getAddress(),formattedDateTime};
            //将转换好的数据 存入dataList
            dataList.add(objs);
        }
        // 创建ExportExcel工具类对象 通过构造方法赋值
        ExportExcel ex = new ExportExcel(excelTitle, rowsName, dataList);
        try {
            // 调用生成Excel的方法,将数据通过输出流写出
            ex.export(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    /**
     * 当前时间转东八区时间
     */
    private Date GMT8Time() {
        // 获取当前时间的Calendar实例
        Calendar calendar = Calendar.getInstance();
        // 将小时数增加8
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        // 将Calendar实例转换为Date对象
        return calendar.getTime();
    }
}
