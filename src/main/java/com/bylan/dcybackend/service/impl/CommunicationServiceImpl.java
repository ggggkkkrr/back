package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.service.CommunicationService;
import com.bylan.dcybackend.vo.DiscussionVO;
import com.bylan.dcybackend.vo.Feedback4StudentVO;
import com.bylan.dcybackend.vo.Feedback4TeacherVO;
import com.bylan.dcybackend.vo.NoticeVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhuaming
 * @date 2022/3/21 10:07
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    DiscussionDAO discussionDao;

    @Autowired
    NoticeDAO noticeDao;

    @Autowired
    FeedbackDAO feedbackDao;

    @Autowired
    SectionStudentDAO sectionStudentDAO;

    @Autowired
    SectionTeacherDAO sectionTeacherDAO;

    @Autowired
    StudentDAO studentDAO;

    private static final Logger log = LogManager.getLogger(CommunicationServiceImpl.class);

    @Override
    public Boolean addDiscussion(CreateDiscussionDTO createDiscussionDTO) {
        createDiscussionDTO.setStatus(Constant.Discussion.NORMAL);
        try {
            // 判断是一级评论还是回复评论
            if (createDiscussionDTO.getFirstDiscussionId() == null) {
                // 新增一级评论
                discussionDao.addFirstDiscussion(createDiscussionDTO);
            } else {
                // 新增回复评论
                discussionDao.addReplyDiscussion(createDiscussionDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入Discussion失败 堆栈信息: {}; 插入数据信息:{}", e, createDiscussionDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteDiscussion(DeleteDiscussionDTO deleteDiscussionDTO) {
        try {
            discussionDao.updateDiscussionStatus(deleteDiscussionDTO.getDiscussionId(), Constant.Discussion.DELETE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入Discussion失败 堆栈信息: {}; 插入数据信息:{}", e, deleteDiscussionDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<DiscussionVO> getDiscussionByCourseId(Long courseId) {
        HashMap<String,Long> id2RoleMap = new HashMap<>();
        List<String> studentIds = sectionStudentDAO.getStuIdByCourseId(courseId);
        List<String> teacherIds = sectionTeacherDAO.getTeaIdByCourseId(courseId);
        studentIds.forEach(item->{
            id2RoleMap.put(item,1L);
        });
        teacherIds.forEach(item->{
            id2RoleMap.put(item,2L);
        });
        List<DiscussionVO> list = discussionDao.getDiscussionByCourseId(courseId);
        list.forEach(item -> {
            if (item.getStatus().equals(Constant.Discussion.DELETE)) {
                item.setContent("<该评论已被删除>");
            }
            Long role = id2RoleMap.get(item.getUserId());
            if (role == null) {
                Integer exist = studentDAO.getNumById(item.getUserId());
                if (exist > 0) {
                    role = 1L;
                } else {
                    role = 2L;
                }
                id2RoleMap.put(item.getUserId(),role);
            }
            item.setRole(id2RoleMap.get(item.getUserId()));
        });
        // 填充角色


        Map<Long, List<DiscussionVO>> toDiscussionListMap = new HashMap<>();
        for (int a = 0; a < list.size(); a++) {
            List<DiscussionVO> tempList = new ArrayList<>();
            for (int b = 0; b < list.size(); b++) {
                if (list.get(a).getDiscussionId().equals(list.get(b).getToDiscussionId())) {
                    tempList.add(list.get(b));
                }
            }
            toDiscussionListMap.put(list.get(a).getDiscussionId(), tempList);
        }
        List<DiscussionVO> firstDiscussion = new ArrayList<>();
        list.forEach(item -> {
            if (item.getToDiscussionId() == null) {
                firstDiscussion.add(item);
            }
        });
        return getDiscussionReplys(firstDiscussion, toDiscussionListMap);
    }

    private List<DiscussionVO> getDiscussionReplys(List<DiscussionVO> list, Map<Long, List<DiscussionVO>> toDiscussionListMap) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        } else {
            list.forEach(item -> {
                Long discussionId = item.getDiscussionId();
                List<DiscussionVO> replyCommentList = toDiscussionListMap.get(discussionId);
                List<DiscussionVO> replyComments = getDiscussionReplys(replyCommentList, toDiscussionListMap);
                item.setReplyDiscussion(replyComments);
            });
            return list;
        }
    }

    @Override
    public Boolean addNotice(CreateNoticeDTO createNoticeDTO) {
        try {
            noticeDao.addNotice(createNoticeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入notice失败 堆栈信息: {}; 插入数据信息:{}", e, createNoticeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteNotice(DeleteNoticeDTO deleteNoticeDTO) {
        try {
            noticeDao.deleteNotice(deleteNoticeDTO.getNoticeId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除notice失败 堆栈信息: {}; 插入数据信息:{}", e, deleteNoticeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<NoticeVO> getNotice(Long sectionId) {
        return noticeDao.getNoticeBySectionId(sectionId);
    }

    @Override
    public Boolean addFeedback(CreateFeedbackDTO createFeedbackDTO) {
        try {
            feedbackDao.createFeedback(createFeedbackDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增feedback失败 堆栈信息: {}; 插入数据信息:{}", e, createFeedbackDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateFeedback(UpdateFeedbackDTO updateFeedbackDTO) {
        try {
            feedbackDao.updateFeedbackResponse(updateFeedbackDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新feedback失败 堆栈信息: {}; 插入数据信息:{}", e, updateFeedbackDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<Feedback4TeacherVO> getFeedback4Teacher(String teacherId) {
        List<Feedback4TeacherVO> list = feedbackDao.getFeedbackByTeacherId(teacherId, Constant.Feedback.CREATE);
        list.forEach(Feedback4TeacherVO::pathTransfer);
        return list;
    }

    @Override
    public List<Feedback4StudentVO> getFeedback4Student(String studentId) {
        List<Feedback4StudentVO> list = feedbackDao.getFeedbackByStudentId(studentId);
        list.forEach(Feedback4StudentVO::pathTransfer);
        return list;
    }
}
