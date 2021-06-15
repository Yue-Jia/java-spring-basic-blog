package com.yueejia.data;

import com.yueejia.model.BlogPost;
import com.yueejia.model.VisitorIpInfo;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface VisitorIpInfoRepository extends CrudRepository<VisitorIpInfo,Long> {
    List<VisitorIpInfo> findByLastLoginBetween(ZonedDateTime startOfDay,ZonedDateTime endOfDay);
    List<VisitorIpInfo> findAll();
    void deleteById(Long id);
    void deleteAll();
}
