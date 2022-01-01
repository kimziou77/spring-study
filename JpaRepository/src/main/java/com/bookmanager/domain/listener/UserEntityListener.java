package com.bookmanager.domain.listener;

import com.bookmanager.domain.User;
import com.bookmanager.domain.UserHistory;
import com.bookmanager.repository.UserHistoryRepository;
import com.bookmanager.support.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

public class UserEntityListener {

    @PreUpdate
    public void preUpdate(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) o;
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistoryRepository.save(userHistory);

    }

}
