package com.bylan.dcybackend.controller;


import com.bylan.dcybackend.bo.CheckQuestScoreBO;
import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.domain.ResultCode;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.entity.Task;
import com.bylan.dcybackend.entity.TaskScore;
import com.bylan.dcybackend.service.TaskProcessService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务处理相关
 *
 * @author wuhuaming
 */
@Api(value = "任务处理相关", tags = {"任务处理"})
@RequestMapping("/task")
@RestController
public class TaskProcessController {

    private static final Logger log = LogManager.getLogger(TaskProcessController.class);

    @Autowired
    private TaskProcessService taskProcessService;

    @PostMapping(value = "/createTask")
    @ApiOperation("批量创建任务")
    @ResponseBody
    public CommonResult<Boolean> createTasks(@RequestBody @Valid ValidList<CreateTaskDTO> createTaskDTOList) {
        log.info("——————————批量创建任务——————————");
        List<Task> tasks = new ArrayList<>();
        for (CreateTaskDTO item : createTaskDTOList) {
            // 转换成存储类型
            try {
                Task task = new Task(item);
                Date current = new Date();
                if (current.after(task.getDeadline())) {
                    // deadline不能比现在早
                    return CommonResult.failed("截止时间不能早于当前时间");
                }
                // 标志为创建
                task.setTaskStatus(Constant.Task.CREATE);
                tasks.add(task);
            } catch (Exception e) {
                log.info("时间解析错误：{}", item);
                return CommonResult.failed("时间解析错误，请选择合法的时间");
            }
        }

        Boolean result = taskProcessService.mCreateTasks(tasks);
        log.info("创建任务结果: {}", result);
        if (!result) {
            CommonResult.failed("创建任务失败");
        }
        return CommonResult.success(true);
    }


    @PostMapping(value = "/publishTask")
    @ApiOperation("批量发布任务")
    @ResponseBody
    public CommonResult<Boolean> publishTask(@RequestBody @Valid PublishTaskDTO publishTaskDTO) {
        log.info("——————————批量发布任务——————————");
        Date current = new Date();
        for (Long item : publishTaskDTO.getTaskIds()) {
            // 检验当前时间是否已经超过deadline
            Date deadline = taskProcessService.getTaskDeadlineById(item);
            if (deadline == null || current.after(deadline)) {
                // deadline不能比现在早
                return CommonResult.failed("截止时间不能早于当前时间");
            }
            // 查询该任务的题目数量
            Integer num = taskProcessService.getQuestionNum(item);
            if (num == null || num == 0) {
                return CommonResult.failed("待发布的任务至少关联一条题目");
            }
            // 只有创建阶段的任务才能发布
            Long status = taskProcessService.getTaskStatusByTaskId(item);
            if (!status.equals(Constant.Task.CREATE)) {
                return CommonResult.failed("不能发布非创建状态的任务");
            }
        }

        Boolean result = taskProcessService.mUpdateTaskStatus(publishTaskDTO.getTaskIds(), Constant.Task.PUBLISH);
        log.info("发布任务结果: {}", result);
        // 进行存储
        if (!result) {
            return CommonResult.failed("发布任务失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/publishResult")
    @ApiOperation("完成打分，批量公布成绩")
    @ResponseBody
    public CommonResult<Boolean> publishResult(@RequestBody @Valid PublishTaskDTO publishResultDTO) {
        log.info("——————————完成打分，批量公布成绩——————————");
        for (Long item : publishResultDTO.getTaskIds()) {
            // 只有发布阶段的任务才能公布成绩
            Long status = taskProcessService.getTaskStatusByTaskId(item);
            if (!status.equals(Constant.Task.PUBLISH)) {
                return CommonResult.failed("不能公布非发布状态任务的成绩");
            }
            // 校验是否所有都打完分了
            Boolean flag = taskProcessService.checkMarkAllStu(item);
            if (!flag) {
                return CommonResult.failed("未批改所有学生的作业前不能公布成绩");
            }
        }

        Boolean result = taskProcessService.mUpdateTaskStatus(publishResultDTO.getTaskIds(), Constant.Task.FINISHED);
        log.info("公布成绩: {}", result);
        // 进行存储
        if (!result) {
            return CommonResult.failed("公布成绩失败");
        }
        return CommonResult.success(true);
    }


    @GetMapping(value = "/mGetTask")
    @ApiOperation("批量查询任务")
    @ResponseBody
    public CommonResult<List<TaskVO>> mGetTaskBySectionId(@RequestParam("section_id")
                                                          @ApiParam("教学班id")
                                                          @NotNull(message = "section_id不能为空")
                                                          @PositiveOrZero(message = "section_id不能为负数")
                                                          Long sectionId) {
        log.info("——————————批量查询任务——————————");
        log.info("教学班id: {}", sectionId);
        List<TaskVO> taskVOList = taskProcessService.mGetTaskBySessionId(sectionId);
        log.info("任务列表: {}", taskVOList);
        return CommonResult.success(taskVOList);
    }

    @PostMapping(value = "/updateTask")
    @ApiOperation("批量更新任务")
    @ResponseBody
    public CommonResult<Boolean> mUpdateTask(@RequestBody @Valid ValidList<UpdateTaskDTO> updateTasks) {
        log.info("——————————批量更新任务——————————");
        List<Task> tasks = new ArrayList<>();
        for (UpdateTaskDTO item : updateTasks) {
            if (!item.isValid()) {
                return CommonResult.failed("请更新至少一项信息");
            }
            // 转换成存储类型
            try {
                Task task = new Task(item);
                // 判断是否是创建状态
                Long status = taskProcessService.getTaskStatusByTaskId(item.getTaskId());
                if (!status.equals(Constant.Task.CREATE)) {
                    return CommonResult.failed("不能更新非创建状态的任务信息");
                }
                tasks.add(task);
            } catch (Exception e) {
                log.info("时间解析错误: {}", item);
                return CommonResult.failed("时间解析错误，请选择合法的时间");
            }
        }

        Boolean result = taskProcessService.mUpdateTask(tasks);
        log.info("更新任务结果: {}", result);
        // 进行存储
        if (!result) {
            return CommonResult.failed("更新任务失败");
        }
        return CommonResult.success(true);

    }

    @PostMapping(value = "/removeTask")
    @ApiOperation("批量删除任务")
    @ResponseBody
    public CommonResult<Boolean> mDeleteTask(@RequestBody @Valid DeleteTaskDTO deleteTaskDTO) {
        log.info("———————批量删除任务————————");
        // 如果已经
        for (Long item : deleteTaskDTO.getTaskIds()) {
            Long status = taskProcessService.getTaskStatusByTaskId(item);
            if (!status.equals(Constant.Task.CREATE)) {
                return CommonResult.failed("不能删除非创建状态的任务");
            }
        }

        Boolean result = taskProcessService.mDeleteTask(deleteTaskDTO.getTaskIds());
        log.info("删除任务结果: {}", result);
        // 进行存储
        if (!result) {
            return CommonResult.failed("删除任务失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/createQuestion")
    @ApiOperation("批量创建题目")
    @ResponseBody
    public CommonResult<Boolean> mCreateQuestion(@RequestBody @Valid ValidList<CreateQuestionDTO> createQuestionDTOList) {
        log.info("—————————批量创建题目—————————");
        for (CreateQuestionDTO item : createQuestionDTOList) {
            Long status = taskProcessService.getTaskStatusByTaskId(item.getTaskId());
            if (!status.equals(Constant.Task.CREATE)) {
                return CommonResult.failed("不能为非创建状态的任务创建题目");
            }
        }

        Boolean result = taskProcessService.mCreateQuestion(createQuestionDTOList);
        log.info("创建题目结果: {}", result);
        if (!result) {
            return CommonResult.failed("创建题目失败");
        }
        return CommonResult.success(true);

    }

    @GetMapping(value = "/getQuestionByTaskId")
    @ApiOperation("查询题目")
    @ResponseBody
    public CommonResult<List<QuestionVO>> getQuestionByTaskId(@RequestParam("task_id")
                                                              @ApiParam("任务id")
                                                              @NotNull(message = "task_id不能为空")
                                                              @PositiveOrZero(message = "task_id不能为负数")
                                                              Long taskId) {
        log.info("—————————查询题目—————————");
        log.info("任务id：{}", taskId);
        List<QuestionVO> questionVOList = taskProcessService.getQuestionByTaskId(taskId);
        log.info("题目列表：{}", questionVOList);
        return CommonResult.success(questionVOList);
    }

    @PostMapping(value = "/updateQuestion")
    @ApiOperation("批量更新题目")
    @ResponseBody
    public CommonResult<Boolean> mUpdateQuestion(@RequestBody @Valid ValidList<UpdateQuestionDTO> updateQuestionDTOList) {
        log.info("—————————批量更新题目—————————");
        for (UpdateQuestionDTO each : updateQuestionDTOList) {
            if (!each.isValid()) {
                return CommonResult.failed("请更新至少一项信息");
            }
            Long status = taskProcessService.getTaskStatusByQuestionId(each.getQuestionId());
            if (!status.equals(Constant.Task.CREATE)) {
                return CommonResult.failed("不能为非创建状态的任务更新题目信息");
            }
        }

        Boolean result = taskProcessService.mUpdateQuestion(updateQuestionDTOList);
        log.info("更新题目结果: {}", result);
        if (!result) {
            return CommonResult.failed("更新题目失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteQuestion")
    @ApiOperation("批量删除题目")
    @ResponseBody
    public CommonResult<Boolean> mDeleteQuestion(@RequestBody @Valid DeleteQuestionDTO deleteQuestionDTO) {
        log.info("———————————批量删除题目—————————————");
        for (Long questionId : deleteQuestionDTO.getQuestionIds()) {
            Long status = taskProcessService.getTaskStatusByQuestionId(questionId);
            if (!status.equals(Constant.Task.CREATE)) {
                return CommonResult.failed("不能为非创建状态的任务删除题目");
            }
        }

        Boolean result = taskProcessService.mDeleteQuestion(deleteQuestionDTO.getQuestionIds());
        log.info("删除题目结果: {}", result);
        if (!result) {
            return CommonResult.failed("删除题目失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/createQuestionScore")
    @ApiOperation("批量打分")
    @ResponseBody
    public CommonResult<Boolean> mCreateQuestionScore(@RequestBody @Valid ValidList<CreateQuestScoreDTO> createQuestScoreDTOList) {
        log.info("———————————批量打分—————————————");
        // 判断deadline和分数上限是否超出
        CheckQuestScoreBO checkQuestScoreBO = taskProcessService.mCheckQuestScore(createQuestScoreDTOList, false);
        if (!checkQuestScoreBO.getResult()) {
            return CommonResult.failed(checkQuestScoreBO.getMessage());
        }

        Boolean result = taskProcessService.mCreateQuestionScore(createQuestScoreDTOList);
        log.info("批量打分结果: {}", result);
        if (!result) {
            return CommonResult.failed("打分失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/updateQuestionScore")
    @ApiOperation("批量更新分数")
    @ResponseBody
    public CommonResult<Boolean> mUpdateQuestionScore(@RequestBody @Valid ValidList<CreateQuestScoreDTO> createQuestScoreDTOList) {
        log.info("———————————批量更新分数—————————————");
        // 判断deadline和分数上限是否超出
        CheckQuestScoreBO checkQuestScoreBO = taskProcessService.mCheckQuestScore(createQuestScoreDTOList, true);
        if (!checkQuestScoreBO.getResult()) {
            return CommonResult.failed(checkQuestScoreBO.getMessage());
        }

        Boolean result = taskProcessService.mUpdateQuestionScore(createQuestScoreDTOList);
        log.info("更新分数结果: {}", result);
        if (!result) {
            return CommonResult.failed("更新分数失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/submitTask")
    @ApiOperation("批量提交作业")
    @ResponseBody
    public CommonResult<Boolean> mCreateTaskScore(@RequestBody @Valid ValidList<CreateTaskScoreDTO> createTaskScoreDTOList) {
        log.info("———————————批量提交作业—————————————");
        // 类转换
        List<TaskScore> taskScores = new ArrayList<>();
        for (CreateTaskScoreDTO each : createTaskScoreDTOList) {
            Date deadline = taskProcessService.getTaskDeadlineById(each.getTaskId());
            Date current = new Date();
            if (current.after(deadline)) {
                return CommonResult.failed("已超过作业提交时间");
            }
            Long status = taskProcessService.getTaskStatusByTaskId(each.getTaskId());
            if (!status.equals(Constant.Task.PUBLISH)) {
                return CommonResult.failed("不能为非发布状态的任务提交作业");
            }
            TaskScore taskScore = new TaskScore(each);
            taskScores.add(taskScore);
        }

        Boolean result = taskProcessService.mCreateTaskScore(taskScores);
        log.info("提交作业结果: {}", result);
        if (!result) {
            return CommonResult.failed(ResultCode.FAILED);
        }
        return CommonResult.success(true);
    }

    @GetMapping(value = "/getSection")
    @ApiOperation("获取教学班编号")
    @ResponseBody
    public CommonResult<List<SectionVO>> getSectionIdByCourseId(@RequestParam("course_id")
                                                                @ApiParam("课程id")
                                                                @NotNull(message = "course_id不能为空")
                                                                @PositiveOrZero(message = "course_id不能为负数")
                                                                Long courseId) {
        log.info("——————————查询教学班—————————————");
        List<SectionVO> sectionVOList = taskProcessService.getSectionIdByCourseId(courseId);
        log.info("教学班: {}", sectionVOList);
        return CommonResult.success(sectionVOList);
    }

    @GetMapping("/getKnowlPt")
    @ApiOperation("获取课程对应的知识点")
    @ResponseBody
    public CommonResult<List<KnowlListVO>> getKnowlPtByCourseId(@RequestParam("course_id")
                                                                @ApiParam("课程id")
                                                                @NotNull(message = "course_id不能为空")
                                                                @PositiveOrZero(message = "course_id不能为负数")
                                                                Long courseId) {
        log.info("——————————查询知识点—————————————");
        List<KnowlListVO> knowlListVOList = taskProcessService.mGetKnowlByCourseId(courseId);
        log.info("知识点：{}", knowlListVOList);
        return CommonResult.success(knowlListVOList);
    }


    @GetMapping(value = "/getTaskCategory")
    @ApiOperation("根据教学班编号和教学周查询所有任务并分类")
    @ResponseBody
    public CommonResult<List<TaskCategoryVO>> getSortedQuestion(@RequestParam("section_id")
                                                                @ApiParam("教学班id")
                                                                @NotNull(message = "section_id不能为空")
                                                                @PositiveOrZero(message = "section_id不能为负数")
                                                                Long sectionId,
                                                                @RequestParam(value = "week", required = false)
                                                                @ApiParam("教学周")
                                                                @PositiveOrZero(message = "week不能为负数")
                                                                Long week) {
        log.info("———————————根据教学班编号和教学周查询任务—————————————");
        List<TaskCategoryVO> taskCategoryVOList = taskProcessService.getTaskCategoryBySectionId(sectionId, week);
        log.info("任务：{}", taskCategoryVOList);
        return CommonResult.success(taskCategoryVOList);
    }

    @GetMapping(value = "/getTaskSummary")
    @ApiOperation("查看教学班指定任务所有学生的总分")
    @ResponseBody
    public CommonResult<List<TaskSumVO>> getStuTaskInfo(@RequestParam("section_id")
                                                        @ApiParam("教学班id")
                                                        @NotNull(message = "section_id不能为空")
                                                        @PositiveOrZero(message = "section_id不能为负数")
                                                        Long sectionId,
                                                        @RequestParam(value = "task_id")
                                                        @ApiParam("任务编号")
                                                        @NotNull(message = "task_id不能为空")
                                                        @PositiveOrZero(message = "task_id不能为负数")
                                                        Long taskId) {
        log.info("———————————查询所有学生任务情况—————————————");
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.FINISHED)) {
            return CommonResult.failed("不能为非完成状态的任务查询学生任务情况");
        }

        List<TaskSumVO> taskSumVOList = taskProcessService.getTaskSummary(sectionId, taskId);
        log.info("学生任务情况：{}", taskSumVOList);
        return CommonResult.success(taskSumVOList);
    }

    @GetMapping(value = "/getUnratedTask")
    @ApiOperation("查看教学班指定任务所有学生的总分——只限打分使用")
    @ResponseBody
    public CommonResult<List<TaskSumVO>> getUnratedTask(@RequestParam("section_id")
                                                        @ApiParam("教学班id")
                                                        @NotNull(message = "section_id不能为空")
                                                        @PositiveOrZero(message = "section_id不能为负数")
                                                        Long sectionId,
                                                        @RequestParam(value = "task_id")
                                                        @ApiParam("任务编号")
                                                        @NotNull(message = "task_id不能为空")
                                                        @PositiveOrZero(message = "task_id不能为负数")
                                                        Long taskId) {
        log.info("———————————查询学生未完成打分的任务情况—————————————");
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.PUBLISH)) {
            return CommonResult.failed("不能为非发布状态的任务查询为打分学生的任务情况");
        }

        List<TaskSumVO> taskSumVOList = taskProcessService.getTaskSummary(sectionId, taskId);
        log.info("未完成打分的任务情况：{}", taskSumVOList);
        return CommonResult.success(taskSumVOList);
    }

    @GetMapping(value = "/getScoreDetail")
    @ApiOperation("查看单个任务所有题目的学生情况")
    @ResponseBody
    public CommonResult<List<ScoreDetailVO>> getClassQuestInfo(@RequestParam(value = "task_id")
                                                               @ApiParam("任务编号")
                                                               @NotNull(message = "task_id不能为空")
                                                               @PositiveOrZero(message = "task_id不能为负数")
                                                               Long taskId) {
        log.info("———————————查询任务分数细则—————————————");
        // 是否发布
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.FINISHED)) {
            return CommonResult.failed("不能为非完成状态的任务查询任务分数细则");
        }
        // 是否到了ddl
        Date deadline = taskProcessService.getTaskDeadlineById(taskId);
        Date current = new Date();
        if (current.before(deadline)) {
            return CommonResult.failed("尚未到达截止时间");
        }

        List<ScoreDetailVO> scoreDetailVOList = taskProcessService.getScoreDetail(taskId);
        log.info("任务分数细则：{}", scoreDetailVOList);
        return CommonResult.success(scoreDetailVOList);
    }

    @PostMapping(value = "/submitEva")
    @ApiOperation("批量提交教学评价")
    @ResponseBody
    public CommonResult<Boolean> createEvaluation(@RequestBody @Valid ValidList<CreateEvaluationDTO> createEvaluationDTOList) {
        log.info("———————————提交教学评价—————————————");
        for (CreateEvaluationDTO item : createEvaluationDTOList) {
            Date deadline = taskProcessService.getTaskDeadlineById(item.getTaskId());
            Date current = new Date();
            if (current.before(deadline)) {
                return CommonResult.failed("尚未到达截止时间");
            }
            // 只有发布状态才可以提交
            Long status = taskProcessService.getTaskStatusByTaskId(item.getTaskId());
            if (!status.equals(Constant.Task.PUBLISH)) {
                return CommonResult.failed("不能为非发布状态的任务提交教学评价");
            }
        }
        Boolean result = taskProcessService.mUpdateEvaluation(createEvaluationDTOList);
        log.info("提交教学评价结果: {}", result);
        if (!result) {
            return CommonResult.failed("提交教学评价失败");
        }
        return CommonResult.success(true);
    }

    @GetMapping(value = "/getStuQuestDetail")
    @ApiOperation("查看单个学生单个作业所有题目分数细节-只限打分使用")
    @ResponseBody
    public CommonResult<List<QuestionScoreDetailVO>> getPersonalQuest(@RequestParam("student_id")
                                                                      @ApiParam("学生id")
                                                                      @NotBlank(message = "学生id不能为空")
                                                                      String studentId,
                                                                      @RequestParam(value = "task_id")
                                                                      @ApiParam("任务编号")
                                                                      @NotNull(message = "task_id不能为空")
                                                                      @PositiveOrZero(message = "task_id不能为负数")
                                                                      Long taskId) {
        log.info("———————————查看学生评分————————————");
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.PUBLISH)) {
            return CommonResult.failed("不能为非发布状态的任务查询学生评分");
        }
        List<QuestionScoreDetailVO> questionScoreDetailVOList = taskProcessService.getQuestionScoreByStuIdAndTaskId(studentId, taskId);
        log.info("查询学生分数细节：{}", questionScoreDetailVOList);
        return CommonResult.success(questionScoreDetailVOList);
    }

    @GetMapping(value = "/getStuTaskDetail")
    @ApiOperation("查看单个学生所有任务情况 只能查到公布结果的")
    @ResponseBody
    public CommonResult<List<TaskScoreDetailVO>> getTaskScoreDetailVO(@RequestParam("student_id")
                                                                      @ApiParam("学生id")
                                                                      @NotBlank(message = "学生id不能为空")
                                                                      String studentId,
                                                                      @RequestParam("section_id")
                                                                      @ApiParam("教学班id")
                                                                      @NotNull(message = "教学班id不能为空")
                                                                      @PositiveOrZero(message = "教学班id不能为负数")
                                                                      Long sectionId) {
        log.info("———————————查看学生任务————————————");
        List<TaskScoreDetailVO> taskScoreDetailVOList = taskProcessService.getStuTaskScoreDetail(studentId, sectionId);
        log.info("学生任务：{}", taskScoreDetailVOList);
        return CommonResult.success(taskScoreDetailVOList);
    }

    @PostMapping(value = "/submitHomework")
    @ApiOperation("批量提交作业")
    @ResponseBody
    public CommonResult<Boolean> submitHomework(@RequestParam("files")
                                                @ApiParam("文件")
                                                @NotEmpty(message = "文件不能为空")
                                                MultipartFile[] files,
                                                @RequestParam("section_id")
                                                @ApiParam("教学班id")
                                                @NotNull(message = "教学班id不能为空")
                                                @PositiveOrZero(message = "教学班id不能为负数")
                                                Long sectionId,
                                                @RequestParam("task_id")
                                                @ApiParam("任务id")
                                                @NotNull(message = "任务id不能为空")
                                                @PositiveOrZero(message = "任务id不能为负数")
                                                Long taskId,
                                                @RequestParam("student_id")
                                                @ApiParam("学生id")
                                                @NotBlank(message = "学生id不能为空")
                                                String studentId) {
        log.info("———————————批量提交作业—————————————");
        Date deadline = taskProcessService.getTaskDeadlineById(taskId);
        Date current = new Date();
        if (current.after(deadline)) {
            return CommonResult.failed("已经过了截止时间");
        }

        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.PUBLISH)) {
            return CommonResult.failed("不能为非发布状态的任务提交作业");
        }

        log.info("开始上传文件");
        Boolean result = taskProcessService.submitHomework(files, sectionId, taskId, studentId);
        log.info("上传文件状态：{}", result);
        if (!result) {
            return CommonResult.failed("上传文件失败");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/getTaskAnswer")
    @ApiOperation("获取学生提交的答案图片")
    @ResponseBody
    public void getTaskAnswer(@RequestParam("file_path")
                              @ApiParam("图片路径")
                              @NotBlank(message = "不能为空") String filePath, HttpServletResponse response) {
        log.info("——————————获取学生提交的答案图片——————————");
        taskProcessService.getTaskAnswer(filePath, response);
    }

    @PostMapping(value = "/zipHomework")
    @ApiOperation("打包下载作业，如果studentId为空，那就下载整个任务的作业")
    @ResponseBody
    public void zipHomework(@RequestParam("section_id")
                            @ApiParam("教学班id")
                            @NotNull(message = "教学班id不能为空")
                            @PositiveOrZero(message = "教学班id不能为负数")
                            Long sectionId,
                            @RequestParam("task_id")
                            @ApiParam("任务id")
                            @NotNull(message = "任务id不能为空")
                            @PositiveOrZero(message = "任务id不能为负数")
                            Long taskId,
                            @RequestParam(value = "student_id", required = false)
                            @ApiParam("学生id")
                            @NotBlank(message = "学生id不能为空")
                            String studentId, HttpServletResponse response) {
        log.info("——————————打包下载作业——————————");
        taskProcessService.zipHomework(sectionId, taskId, studentId, response);
    }

    @GetMapping(value = "/getStuQuestInfo")
    @ApiOperation("获取该题目所有学生所有题目的得分详情")
    @ResponseBody
    public CommonResult<List<StuQuestInfoVO>> getSpecifiedTaskStuQuestionInfo(@RequestParam("task_id")
                                                                              @ApiParam("任务id")
                                                                              @NotNull(message = "不能为空") Long taskId) {
        log.info("——————————获取整个任务所有学生所有题目的分数细则——————————");
        // 校验任务的id 必须是发布的才可以查询
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (status == null || !status.equals(Constant.Task.PUBLISH)) {
            return CommonResult.failed("不能为非发布状态的任务查询学生分数细则");
        }
        List<StuQuestInfoVO> stuQuestInfoVOList = taskProcessService.getStuQuestByTaskId(taskId);
        log.info("学生分数细则：{}", stuQuestInfoVOList);
        return CommonResult.success(stuQuestInfoVOList);
    }

    @PostMapping(value = "/uploadTaskFile")
    @ApiOperation("上传任务的附件")
    public CommonResult<Boolean> uploadTaskFile(@RequestParam(name = "files", required = false)
                                                @ApiParam("任务附件")
                                                @NotEmpty(message = "不能为空") MultipartFile[] files,
                                                @RequestParam("task_id")
                                                @ApiParam("任务id")
                                                @NotNull(message = "不能为空") Long taskId) {
        log.info("———————————上传任务的附件—————————————");
        // 验证任务状态
        Long status = taskProcessService.getTaskStatusByTaskId(taskId);
        if (!status.equals(Constant.Task.CREATE)) {
            return CommonResult.failed("不能为非创建状态的任务更新任务信息");
        }
        // 验证所有文件：文件大小 文件名是否为空 文件格式
        if (files == null) {
            return CommonResult.failed("上传的文件为空");
        }
        for (MultipartFile file : files) {
            if (file.getSize() > Constant.Task.MAX_SIZE) {
                return CommonResult.failed(String.format("上传的文件过大，请选择小于%dM的文件", Constant.Task.MAX_SIZE >> 20));
            }
            // 获取文件后缀
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                return CommonResult.failed("上传的文件名为空");
            }
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (!Constant.Task.SUPPORTED_FORMAT.contains(suffix.toUpperCase())) {
                return CommonResult.failed("请选择doc,docx,pdf,jpg,png,jpeg,zip,rar格式的文件");
            }
        }
        List<String> filenames = taskProcessService.uploadTaskFiles(files);
        if (filenames == null) {
            log.error("文件上传服务器失败");
            return CommonResult.failed("文件上传服务器失败");
        }
        log.info("文件上传服务器成功");
        Boolean isDelete = taskProcessService.deleteTaskFilesByTaskId(taskId);
        if (!isDelete) {
            log.error("删除任务原先的附件————操作失败");
        }

        List<Task> tasks = new ArrayList<>();
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setTaskId(taskId);
        updateTaskDTO.setTaskFilePath(filenames);
        log.info("新的附件路径： {}", filenames);
        try {
            Task task = new Task(updateTaskDTO);
            tasks.add(task);
        } catch (Exception e) {
            log.info("时间解析错误: {}", updateTaskDTO);
            return CommonResult.failed("时间解析错误，请选择合法的时间");
        }
        Boolean result = taskProcessService.mUpdateTask(tasks);
        if (!result) {
            log.error("任务附件路径写入数据库————操作失败");
            return CommonResult.failed("任务路径写入数据库失败");
        }
        log.info("任务附件路径成写入数据库————操作成功");
        return CommonResult.success(true);
    }

    @GetMapping(value = "/downloadTaskFile")
    @ApiOperation("下载任务附件")
    public void downloadTaskFile(@RequestParam("filename")
                                 @ApiParam("文件名")
                                 @NotBlank(message = "文件名不能为空")
                                 String filename, HttpServletResponse response) {
        log.info("——————————下载任务附件——————————");
        log.info("文件名: {}", filename);
        taskProcessService.downloadTaskFile(filename, response);
    }

    @GetMapping(value = "/getTaskDetail")
    @ApiOperation("查询单个任务的详细信息")
    @ResponseBody
    public CommonResult<TaskVO> getTaskDetailByTaskId(@RequestParam("task_id")
                                                      @ApiParam("任务id")
                                                      @NotNull(message = "task_id不能为空")
                                                      @PositiveOrZero(message = "task_id不能为负数")
                                                      Long taskId) {
        log.info("——————————查询单个任务的详细信息——————————");
        log.info("任务id: {}", taskId);
        TaskVO taskVO = taskProcessService.getTaskDetailByTaskId(taskId);
        log.info("任务详细信息: {}", taskVO);
        return CommonResult.success(taskVO);
    }
}

