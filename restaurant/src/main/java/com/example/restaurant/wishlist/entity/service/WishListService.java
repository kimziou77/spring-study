package com.example.restaurant.wishlist.entity.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishlist.entity.dto.WishListDto;
import com.example.restaurant.wishlist.entity.entity.WishListEntity;
import com.example.restaurant.wishlist.entity.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){
        // 지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal()>0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>","");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            // 이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);
            if(searchImageRes.getTotal()>0) {
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                // 결과를 리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHoePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());
                return result;
            }
        }
        return new WishListDto();
    }

    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);
        var saveEntity = wishListRepository.save(entity);
        return entityToDto(saveEntity);
    }

    private WishListEntity dtoToEntity(WishListDto wishListDto){
        var entity = new WishListEntity();
        entity.setIndex(wishListDto.getIndex());
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setAddress(wishListDto.getAddress());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setHoePageLink(wishListDto.getHoePageLink());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        return entity;
    }
    private WishListDto entityToDto(WishListEntity wishListEntity){
        var dto = new WishListDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setCategory(dto.getCategory());
        dto.setAddress(dto.getAddress());
        dto.setRoadAddress(dto.getRoadAddress());
        dto.setHoePageLink(dto.getHoePageLink());
        dto.setImageLink(dto.getImageLink());
        dto.setVisit(dto.isVisit());
        dto.setVisitCount(dto.getVisitCount());
        dto.setLastVisitDate(dto.getLastVisitDate());
        return dto;
    }

    public List<WishListDto> findAll() {
        return wishListRepository.findAll();
    }

    public void delete(int index) {
        wishListRepository.deleteById(index);
    }
    public void addVisit(int index){
        var wishItem = wishListRepository.findById(index);
        if(wishItem.isPresent()){
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);
        }
    }
}
