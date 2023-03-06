package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.CreateFeedbackDTO;
import com.bylan.dcybackend.dto.UpdateFeedbackDTO;
import com.bylan.dcybackend.vo.Feedback4StudentVO;
import com.bylan.dcybackend.vo.Feedback4TeacherVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Feedback)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface FeedbackDAO {

    /**
     * 新增反馈
     *
     * @param createFeedbackDTO 新增反馈DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int createFeedback(CreateFeedbackDTO createFeedbackDTO) throws DataAccessException;

    /**
     * 更新反馈
     *
     * @param updateFeedbackDTO 更新反馈DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateFeedbackResponse(UpdateFeedbackDTO updateFeedbackDTO) throws DataAccessException;

    /**
     * 通过教师id获取反馈
     *
     * @param teacherId 教师id
     * @param status    状态
     * @return 反馈
     */
    List<Feedback4TeacherVO> getFeedbackByTeacherId(@Param("teacherId") String teacherId, @Param("status") Long status);

    /**
     * 通过学生id获取反馈
     *
     * @param studentId 学生id
     * @return 反馈
     */
    List<Feedback4StudentVO> getFeedbackByStudentId(@Param("studentId") String studentId);
}