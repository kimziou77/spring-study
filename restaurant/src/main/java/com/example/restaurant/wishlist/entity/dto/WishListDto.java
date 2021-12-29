package com.example.restaurant.wishlist.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishListDto {
    private Integer index; // WishListEntity를 copy해온 후 index를 추가해줌.
    // DB가 변해도 변환과정을 통해 프론트엔드를 건드리지 않기 위함.

    private String title;                   //음식명, 장소명
    private String category;                //카테고리
    private String address;                 //주소
    private String roadAddress;             //도로명
    private String hoePageLink;             //홈페이지 주소
    private String imageLink;               //움식, 가게 이미지 주소
    private boolean isVisit;                //방문여부
    private int visitCount;                 //방문 카운트
    private LocalDateTime lastVisitDate;    //마지막 방문 일자

}

