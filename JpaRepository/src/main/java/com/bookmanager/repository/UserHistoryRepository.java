package com.bookmanager.repository;

import com.bookmanager.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory,Long> {
    List<UserHistory> findByUserId(Long userId);
}
