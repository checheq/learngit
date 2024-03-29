package com.gx.service.Impl;

import com.gx.dao.IAccountdao;
import com.gx.domain.Account;
import com.gx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private IAccountdao iaccountdao;
    @Override
    public List<Account> findAll() {
        System.out.println("Service业务层：查询所有账户...");
        return iaccountdao.findAll();
    }

    @Override
    public void saveAccount(Account account) {

        System.out.println("Service业务层：保存帐户...");
        iaccountdao.saveAccount(account);
    }

}
