package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.vo.DiscussionVO;
import com.bylan.dcybackend.vo.Feedback4StudentVO;
import com.bylan.dcybackend.vo.Feedback4TeacherVO;
import com.bylan.dcybackend.vo.NoticeVO;

import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/21 10:05
 */
public interface CommunicationService {

    Boolean addDiscussion(CreateDiscussionDTO createDiscussionDTO);

    Boolean deleteDiscussion(DeleteDiscussionDTO deleteDiscussionDTO);

    List<DiscussionVO> getDiscussionByCourseId(Long sectionId);

    Boolean addNotice(CreateNoticeDTO createNoticeDTO);

    Boolean deleteNotice(DeleteNoticeDTO deleteNoticeDTO);

    List<NoticeVO> getNotice(Long sectionId);

    Boolean addFeedback(CreateFeedbackDTO createFeedbackDTO);

    Boolean updateFeedback(UpdateFeedbackDTO updateFeedbackDTO);

    List<Feedback4TeacherVO> getFeedback4Teacher(String teacherId);

    List<Feedback4StudentVO> getFeedback4Student(String studentId);

}
