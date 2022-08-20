package com.gzuniversity.eduservice.entity.vo.chater;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//章节实体类
@Data
public class ChapterVo {
    private String id;
    private String title;
    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
