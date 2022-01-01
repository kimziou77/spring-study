package com.bookmanager.domain.listener;

import com.bookmanager.domain.User;
import com.bookmanager.domain.UserHistory;
import com.bookmanager.repository.UserHistoryRepository;
import com.bookmanager.support.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

public class UserEntityListener {
//    @Autowired  // 빈 주입 , 최근엔 필드 인젝션을 권장하진 않음..
//    private UserHistoryRepository userHistoryRepository;
    // - support/BeanUtils 유틸클래스를 통해 가져올 수 있따.
    
    @PreUpdate
    public void preUpdate(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) o;
        UserHistory userHistory = new UserHistory();
//        userHistory.setId(1L);
        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistoryRepository.save(userHistory);

    }

}
