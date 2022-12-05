package com.yinhui.service;

import com.yinhui.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinhui
 * @date 2022/12/2
 */
@RestController
@Slf4j
public class EsController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping("insert")
    public void insert(@RequestBody UserInfo userInfo){
        IndexCoordinates index = IndexCoordinates.of("fm_alarm_index_12");
        final UserInfo save = elasticsearchRestTemplate.save(userInfo, index);
        log.info(save.toString());
    }

}
