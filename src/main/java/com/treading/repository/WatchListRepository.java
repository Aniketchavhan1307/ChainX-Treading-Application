package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long>
{

	WatchList findByUserId(Long userId);
}
