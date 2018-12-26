package com.xuanxuan.csu.web;

import com.xuanxuan.csu.announce.LoginRequired;
import com.xuanxuan.csu.configurer.AppConfigurer;
import com.xuanxuan.csu.core.Result;
import com.xuanxuan.csu.core.ResultGenerator;
import com.xuanxuan.csu.dto.RefreshDTO;
import com.xuanxuan.csu.model.Passage;
import com.xuanxuan.csu.service.PassageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanxuan.csu.vo.CommentRefreshVO;
import com.xuanxuan.csu.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by PualrDwade on 2018/12/03.
 */
@RestController
@RequestMapping("/api/passage")
public class PassageController {
    @Resource
    private PassageService passageService;


    /**
     * 生成文章的id
     *
     * @param url
     * @return
     */
    @ApiOperation(value = "申请文章接入Id")
    @PostMapping("/genOpenId")
    @LoginRequired
    public Result genOpenId(@RequestParam String url) {
        return ResultGenerator.genSuccessResult(passageService.genOpenId(url));
    }


    /**
     * @return
     * @apiNote 页面需要评论数据接口
     * @note 默认10条数据, 默认得到第一页数据
     * 请求此接口需要传入文章id,通过数据库查询得到评论与回复数据
     */
    @ApiOperation(value = "得到文章的详细评论信息")
    @GetMapping("/{passageId}/comments")
    @LoginRequired
    public Result getComments(@RequestParam(defaultValue = "1") Integer page,
                              @PathVariable String passageId) {
        if (page < 1) {
            //默认查询第一页
            page = 1;
        }
        page -= 1;
        page *= AppConfigurer.COMMENT_PAGE_SIZE;
        List<CommentVO> list = passageService.getComments(passageId, page);
        return ResultGenerator.genSuccessResult(list);
    }


    /**
     * 得到刷新的数据
     *
     * @param passageId
     * @param refreshDTO
     * @return
     */
    @ApiOperation(value = "刷新评论数据")
    @PostMapping("/comments/refresh")
    @LoginRequired
    public Result getNewComments(@Valid @RequestBody RefreshDTO refreshDTO) {
        CommentRefreshVO commentRefreshVO = passageService.getRefreshComments(refreshDTO);
        return ResultGenerator.genSuccessResult(commentRefreshVO);
    }

}
