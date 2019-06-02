package com.binzikeji.itoken.service.posts.service.impl;

import com.binzikeji.itoken.common.domain.TbPostsPost;
import com.binzikeji.itoken.common.mapper.TbPostsPostMapper;
import com.binzikeji.itoken.common.service.impl.BaseServiceImpl;
import com.binzikeji.itoken.service.posts.service.TbPostsPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/25 14:46
 **/
@Service
@Transactional(readOnly = true)
public class TbPostsPostServiceImpl extends BaseServiceImpl<TbPostsPost, TbPostsPostMapper> implements TbPostsPostService {

}
