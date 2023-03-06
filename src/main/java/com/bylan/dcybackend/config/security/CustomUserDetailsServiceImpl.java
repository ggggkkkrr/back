package com.bylan.dcybackend.config.security;

import com.bylan.dcybackend.dao.AccountDAO;
import com.bylan.dcybackend.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义用户信息服务
 *
 * @author Rememorio
 */
@Component("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired
    AccountDAO accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        Account account = accountDao.findAccountByAccountId(username);
        if (account == null) {
            log.warn("User: {} not found", username);
            // 这里找不到必须抛异常
            throw new UsernameNotFoundException("User[" + username + "] was not found in database");
        }

        // 2. 设置角色
        // 创建角色集合对象
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : account.getRoles()) {
            // 创建角色对象
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
            // 对象添加到集合中
            grantedAuthorities.add(grantedAuthority);
        }

        User user = new User(username, account.getPassword(), grantedAuthorities);
        log.info("User: {} saved", user);
        return user;
    }

}
