package com.japanwork.repository.request_detail;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.RequestDetail;

public interface RequestDetailRepository extends JpaRepository<RequestDetail, UUID>{

}
