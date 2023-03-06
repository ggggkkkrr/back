package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Account;
import org.apache.ibatis.annotations.Param;

/**
 * 账号
 *
 * @author Rememorio
 */
public interface AccountDAO {
    /**
     * 查找账号
     *
     * @param accountId 账号id
     * @return 账号表
     */
    Account findAccountByAccountId(@Param("accountId") String accountId);

    /**
     * 获取账号的角色
     *
     * @param accountId 账号id
     * @return 账号的角色
     */
    String getRoleByAccountId(@Param("accountId") String accountId);
}
