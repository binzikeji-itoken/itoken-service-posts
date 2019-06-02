package com.binzikeji.itoken.service.posts.controller;

import com.binzikeji.itoken.common.domain.TbPostsPost;
import com.binzikeji.itoken.common.dto.BaseRestult;
import com.binzikeji.itoken.common.utils.MapperUtils;
import com.binzikeji.itoken.service.posts.service.TbPostsPostService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/25 14:49
 **/
@RestController
@RequestMapping(value = "v1/posts")
public class TbPostsPostController {

    @Autowired
    private TbPostsPostService tbPostsPostService;

    @ApiOperation(value = "根据 postsGuid 查询文章")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "postsGuid", value = "postsGuid 文章id", required = true, dataTypeClass = String.class, paramType = "path")
    )
    @RequestMapping(value = "{postsGuid}", method = RequestMethod.GET)
    public BaseRestult get(@PathVariable(value = "postsGuid") String postsGuid){
        TbPostsPost tbPostsPost = new TbPostsPost();
        tbPostsPost.setPostGuid(postsGuid);
        TbPostsPost obj = tbPostsPostService.selectOne(tbPostsPost);
        return BaseRestult.ok(obj);
    }

    @ApiOperation(value = "保存文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbPostsPostJson", value = "文章对象", required = true, dataTypeClass = String.class, paramType = "json"),
            @ApiImplicitParam(name = "optsBy", value = "保存人", required = true, dataTypeClass = String.class)
    })
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseRestult save(
            @RequestParam(required = true) String tbPostsPostJson,
            @RequestParam(required = true) String optsBy
    ){
        int result = 0;

        TbPostsPost tbPostsPost = null;

        try {
            tbPostsPost = MapperUtils.json2pojo(tbPostsPostJson, TbPostsPost.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tbPostsPost != null){
            // 主键为空,表示新增
            if (StringUtils.isBlank(tbPostsPost.getPostGuid())){
                tbPostsPost.setPostGuid(UUID.randomUUID().toString());
                result = tbPostsPostService.insert(tbPostsPost, optsBy);
            }

            // 编辑
            else {
                result = tbPostsPostService.update(tbPostsPost, optsBy);
            }

            if (result > 0){
                return BaseRestult.ok("保存文章成功");
            }
        }
        return BaseRestult.ok("保存文章失败");
    }

    @ApiOperation(value = "文章分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "tbPostsPostJson", value = "文章对象 JSON 字符串", required = false, dataTypeClass = String.class, paramType = "json")
    })
    @RequestMapping(value = "page/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public BaseRestult page(
            @PathVariable(required = true) int pageNum,
            @PathVariable(required = true) int pageSize,
            @RequestParam(required = false) String tbPostsPostJson
    ){
        TbPostsPost tbPostsPost = null;
        if (StringUtils.isNotBlank(tbPostsPostJson)){
            try {
                tbPostsPost = MapperUtils.json2pojo(tbPostsPostJson, TbPostsPost.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PageInfo<TbPostsPost> pageInfo = tbPostsPostService.page(pageNum, pageSize, tbPostsPost);
        List<TbPostsPost> list = pageInfo.getList();
        BaseRestult.Cursor cursor = new BaseRestult.Cursor();
        cursor.setTotal(new Long(pageInfo.getTotal()).intValue());
        cursor.setOffset(pageInfo.getPageNum());
        cursor.setLimit(pageInfo.getPageSize());

        return BaseRestult.ok(list, cursor);

    }
}
