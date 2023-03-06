package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.domain.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * 账号
 *
 * @author Rememorio
 */
@ApiModel("账号")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {

    @ApiModelProperty("账号id")
    private String accountId;
    @ApiModelProperty("账号密码")
    private String password;
    @ApiModelProperty("security角色")
    private String role;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    /**
     * 获取权限，这里就用简单的方法
     * 在spring security中，Authorities既可以是ROLE也可以是Authorities
     *
     * @return role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return accountId;
    }

    public void setUsername(String username) {
        this.accountId = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return role.split(Constant.Public.SEPARATOR);
    }

    public void setRole(String role) {
        this.role = role;
    }

}
