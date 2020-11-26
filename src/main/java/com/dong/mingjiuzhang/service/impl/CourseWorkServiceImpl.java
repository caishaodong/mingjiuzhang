package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkPageVO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.CourseWorkMapper;
import com.dong.mingjiuzhang.service.CourseWorkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class CourseWorkServiceImpl extends ServiceImpl<CourseWorkMapper, CourseWork> implements CourseWorkService {

    @Override
    public CourseWork getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<CourseWork>().eq(CourseWork::getId, id).eq(CourseWork::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    @Override
    public IPage<CourseWorkPageVO> pageList(CourseWorkSearchDTO courseWorkSearchDTO) {
        return this.baseMapper.pageList(courseWorkSearchDTO);
    }
}
