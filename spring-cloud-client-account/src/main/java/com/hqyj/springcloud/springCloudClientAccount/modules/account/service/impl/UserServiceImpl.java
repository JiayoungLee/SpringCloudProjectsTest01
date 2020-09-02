package com.hqyj.springcloud.springCloudClientAccount.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.dao.UserDao;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.entity.City;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.entity.User;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.service.TestFeignClient;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.service.UserService;
import com.hqyj.springcloud.springCloudClientAccount.modules.common.vo.Result;
import com.hqyj.springcloud.springCloudClientAccount.modules.common.vo.SearchVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * author  Jayoung
 * createDate  2020/8/20 0020 9:02
 * version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestFeignClient testFeignClient;

    /**
     * ‐当 TestFeignClient 有了实现类后，在注入时，idea 会报错
     * Could not autowire. There is more than one bean of 'xxx' type
     * ‐意思是注入的 bean 类型不止一个，这并不影响项目运行，相当于一个警告
     测试：http://127.0.0.1:8762/api/user/1
     正确的情况，将返回带 cities 的 user 对象；
     若将 UserFeignClient 接口的地址写错，将进行容错处理，返回空 cities 的 user
     对象；
     Zuul
     Zuul 简介
     通过上面的学习，分布式架构基本成型；
     * ‐解决方案：
     * 1、给不同的实现标注名字，此处使用 Qulifier 注解标注，根据 bean 名称注入
     * 2、在需要指定的类型处添加 @Primary 注解；
     */

    @Override
    @Transactional
    public Result<User> insertUser(User user) {
//        User userTmp = userDao.getUserByUserName(user.getUserName());
//        if (userTmp != null) {
//            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
//        }
//
//
//        user.setCreateDate(LocalDateTime.now());
//        user.setPassword(MD5Util.getMD5(user.getPassword()));
//        userDao.insertUser(user);
//
//        //要先插入之后才有userId可用
//        userRoleDao.deleteUserRoleByUserId(user.getUserId());
//        List<Role> roles = user.getRoles();
//        if (roles != null && !roles.isEmpty()) {
//            //遍历采用的是第二种方式：jdk8的新方式
////            for (Role role: roles){
////                userRoleDao.insertUserRole(user.getUserId(),role.getRoleId());
////            }
//
//            roles.stream().forEach(item -> {
//                userRoleDao.insertUserRole(user.getUserId(), item.getRoleId());
//            });
//        }

        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "insert success！");
    }

    //    Login
    @Override
    public Result<User> getUserByUserNameAndPassword(User user) {
//        //获取subject（shiro与应用层交互的组件）
//        Subject subject = SecurityUtils.getSubject();
//        //封装令牌Token，后面好和Myrealm里面的认证器比对
//        UsernamePasswordToken usernamePasswordToken =
//                new UsernamePasswordToken(user.getAccountName(),
//                        MD5Util.getMD5(user.getPassword()));
//        usernamePasswordToken.setRememberMe(user.getRememberMe());
//
//        try {
//            //调用login方法，进入Myrealm里面的认证方法 --->MyRealm类
//            subject.login(usernamePasswordToken);
//            subject.checkRoles();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result<>(
//                    Result.ResultStatus.FAILED.status, "UserName or Password is Error");
//        }
//
//        Session session = subject.getSession();
//        session.setAttribute("user",(User)subject.getPrincipal());

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Login Successfully!!!");
    }

    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {
//        User userTmp = userDao.getUserByUserName(user.getUserName());
//        if (userTmp != null && userTmp.getUserId() != user.getUserId()) {
//            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
//        }
//        userDao.updateUser(user);
//
//
//        userRoleDao.deleteUserRoleByUserId(user.getUserId());
//        List<Role> roles = user.getRoles();
//        if (roles != null && !roles.isEmpty()) {
//            roles.stream().forEach(item -> {
//                userRoleDao.insertUserRole(user.getUserId(), item.getRoleId());
//            });
//        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Update Success!!", user);
    }

    @Override
    @Transactional
    public Result<Object> deleteUser(int userId) {
//        userDao.deleteUser(userId);
//        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<>(Result.ResultStatus.SUCCESS.status, "Delete Success!!");
    }

    @Override
    //@HystrixCommand(fallbackMethod = "getUserByUserIdFallbackMethod")
    public User getUserByUserId(int userId) {
        User user = userDao.getUserByUserId(userId);
        if (user == null){
            return null;
        }

        //这里调用另外一个微服务的接口，拿到数据进行包装
//        List<City> cities = Optional.ofNullable(restTemplate
//                .getForObject("http://CLIENT-TEST/api/cities/{countryId}"
//                ,List.class,522))
//                .orElse(Collections.emptyList());
        List<City> cities = testFeignClient.getCitiesByCountryId(522);
        user.setCities(cities);
        return user;
    }

//    public User getUserByUserIdFallbackMethod(int userId){
//        User user = userDao.getUserByUserId(userId);
//        user.setCities(new ArrayList<City>());
//        return user;
//    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
//        if (file.isEmpty()) {
//            return new Result<String>(
//                    Result.ResultStatus.FAILED.status, "Please Select Img");
//        }
//        String relativePath = "";
//        String destFilePath = "";
//        try {
//            String osName = System.getProperty("os.name");
//            osName = osName.toLowerCase();
//            if (osName.startsWith("win")) {
//                destFilePath = resourceConfigBean.getLocationPathForWindows()
//                        + file.getOriginalFilename();
//            } else {
//                destFilePath = resourceConfigBean.getLocationPathForLinux()
//                        + file.getOriginalFilename();
//            }
//
//            relativePath = resourceConfigBean.getRelativePath()
//                    + file.getOriginalFilename();
//
//            File destFile = new File(destFilePath);
//            file.transferTo(destFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Result<String>(Result.ResultStatus.FAILED.status, "Upload Failed!!");
//        }
        return new Result<String>(Result.ResultStatus.SUCCESS.status, "Upload Success!!","");
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
//        User userTmp = userDao.getUserByUserName(user.getUserName());
//        if (userTmp != null && userTmp.getUserId() != user.getUserId()) {
//            return new Result<User>(Result.ResultStatus.FAILED.status, "User Name already exists!");
//        }
//        userDao.updateUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Update Success!!", user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//
//        Session session = subject.getSession();
//        session.setAttribute("user",null);
    }
}
