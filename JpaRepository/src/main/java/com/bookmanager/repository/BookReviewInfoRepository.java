package com.bookmanager.repository;

import com.bookmanager.domain.BookReviewinfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewInfoRepository extends JpaRepository<BookReviewinfo,Long> {
}
