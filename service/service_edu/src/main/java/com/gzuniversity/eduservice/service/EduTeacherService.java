package com.gzuniversity.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzuniversity.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-08-19
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
