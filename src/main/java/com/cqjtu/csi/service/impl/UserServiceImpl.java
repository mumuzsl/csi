package com.cqjtu.csi.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.repository.BaseRepository;
import com.cqjtu.csi.repository.UserRepository;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.service.base.CrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserServiceImpl extends CrudService<User, Integer> implements UserService {

    private final UserRepository userRepository;

    protected UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void login(LoginParam loginParam) {


    }

    @Override
    public Optional<User> getByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    @Override
    public User getByLoginNameOfNonNull(String username) {
        return getByLoginName(username).orElseThrow(() -> new NotFoundException("The username dose not exist").setErrorData(username));
    }

    @Override
    public void register(UserParam userParam) {

        User user = userParam.convertTo();

        userRepository.save(user);

    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "User must not be null");

        return !StringUtils.isBlank(plainPassword) && BCrypt.checkpw(plainPassword, user.getPassword());
    }


}
