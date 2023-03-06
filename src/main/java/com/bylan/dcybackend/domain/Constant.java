package com.bylan.dcybackend.domain;

import java.io.File;

/**
 * 常数和常字符串
 *
 * @author Rememorio
 */
public interface Constant {

    class Public {
        public static final String HELLO_WORLD = "Hello World";
        public static final String HELLO = "Hello";
        public static final String NAME = "name";
        public static final String SEPARATOR = ";";
        public static final String TWO_BITS = "%.2f";
        public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final String TIME_FORMAT_FOR_REACH = "yyyyMMdd";
        public static final String TIME_FORMAT_FOR_FILE = "yyyyMMddhhmmssSSS";
        public static final String WINDOWS_CRLF = "\r\n";
        public static final String LINUX_LF = "\n";
        public static final String MACOS_CR = "\r";
    }

    class Path {
        public static final String ROOT = String.join(File.separator, "", "home", "user", "dcy", "dist", "obereach");
        public static final String SYLLABUS = "syllabus";
        public static final String TEMPLATE = "教学大纲模板.docx";
        public static final String SYLLABUS_DOC = "课程教学大纲";
        public static final String DOC_FORMAT = ".docx";
        public static final String TEMP_FILE = "tempFile.docx";
        public static final String HOMEWORK = "homework";
        public static final String TEMP_ZIP = "temp.zip";
        public static final String JPG_FORMAT = ".jpg";
        public static final String CHAT_PICTURE = "img";
        public static final String PDF_FORMAT = ".pdf";
    }

    class Chat {
        // 消息状态
        public static final Integer NORMAL = 1;
        public static final Integer DELETE_BY_OWNER = 2;
        public static final Integer DELETE_BY_ADMIN = 3;

        // 分页查询
        public static final Integer INIT_PAGE_SIZE = 10;
        public static final Integer PAGE_SIZE = 5;
        // 用户角色状态
        public static final Integer STUDENT = 1;
        public static final Integer TEACHER = 2;
        public static final Integer GROUP = 3;

        // 上传图片限制大小 10M
        public static final Integer MAX_SIZE = 1024 * 1024 * 10;
        // 支持的格式
        public static final String SUPPORTED_FORMAT = "jpg,jpeg,gif,png".toUpperCase();
    }

    class MessageType {
        public static final Integer SEND_PERSON_MSG = 1; // 前端向后端发私人聊天消息
        public static final Integer SEND_GROUP_MSG = 2; // 前端向后端发送的群聊天消息
        public static final Integer ECHO = 3; // 回显
        public static final Integer BROADCAST_PERSON_MSG = 4; // 广播私人消息
        public static final Integer BROADCAST_GROUP_MSG = 5; // 广播群聊消息
        public static final Integer LEAVE = 6; // 已读
        public static final Integer BACKOUT_PERSON_MSG = 7; // 撤销消息
        public static final Integer BACKOUT_GROUP_MSG = 8; // 撤销消息
    }

    class Role {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String TEACHER = "ROLE_TEACHER";
        public static final String STUDENT = "ROLE_STUDENT";
        public static final String DEPT_HEAD = "ROLE_DEPT_HEAD";
        public static final String COURSE_MANAGER = "ROLE_COURSE_MANAGER";
    }

    class Gender {
        public static final Integer MALE = 1;
        public static final Integer FEMALE = 0;
    }

    class Curriculum {
        public static final Integer OPEN = 1;
        public static final Integer CLOSED = 0;
        // 选修课
        public static final Integer OPTIONAL_COURSE = 0;
        // 必修课
        public static final Integer REQUIRED_COURSE = 1;
    }

    class Course {
        public static final Integer SPRING = 1;
        public static final Integer AUTUMN = 2;
        public static final Integer THIRD_TERM = 3;

        public static final Integer OPEN = 1;
        public static final Integer CLOSED = 0;
    }

    class Reach {
        // 达成度的reachType字段
        public static final Integer STUDENT = 1;
        public static final Integer SECTION = 2;
        public static final Integer COURSE = 3;
    }

    class Task {
        // 任务附件存放的文件夹名称
        public static final String TASK = "task";
        // 对应task的三个阶段 创建、发布
        public static final Long CREATE = 1L;
        public static final Long PUBLISH = 2L;
        public static final Long FINISHED = 3L;
        // 已完成和未完成
        public static final String ACCOMPLISHED = "已完成";
        public static final String UNACCOMPLISHED = "未完成";

        // 上传附件限制大小 10M
        public static final Integer MAX_SIZE = 1024 * 1024 * 10;
        // 附件支持的格式
        public static final String SUPPORTED_FORMAT = "doc,docx,pdf,jpg,png,jpeg,zip,rar".toUpperCase();
    }

    class Discussion {
        public static final Long NORMAL = 1L;
        public static final Long DELETE = 2L;
    }

    class Feedback {
        public static final Long CREATE = 1L;
        public static final Long REPLY = 2L;
    }

    class Syllabus {
        public static final String ILO_REG = "(i|I)(l|L)(o|O)-*[0-9]+";
        public static final String KNOWLEDGE_REG = "(i|I)(l|L)(o|O)-*[0-9]+-+[0-9]+";
        public static final String ALPHABET_NUMBER_REG = "(?<=[A-Za-z])(?=[0-9])|(?<=[0-9])(?=[A-Za-z])";
        public static final Character CONNECTOR = '-';
        public static final Character UNDERLINE = '_';
        public static final String ILO_X = "ILO-X";
        public static final String ILO_X_X = "ILO-X-X";
        // 课程基本信息
        public static final String[] COURSE_BASE_INFORMATION = {
                "course_code", "course_desc", "prerequisite", "textbook", "ref_book", "course_website"
        };
        // 预期学习结果 ILO
        public static final String[] INTENDED_LEARNING_OUTCOMES = {
                "l2_desc", "l3_desc", "l3_weight", "initial_level", "achieve_level", "ilo_desc", "ilo_weight", "knowledge_desc", "knowledge_weight", "expected_score"
        };
        // 教学环节细则
        public static final String[] TEACHING_DETAIL = {
                "l2_desc", "teach_detail_weight", "teach_content", "impl_link", "teach_strategy"
        };
        // 课程考核细则
        public static final String[] ASSESS_DETAIL = {
                "assess_struct_desc", "l2_desc", "ilo_number", "ilo_desc"
        };
        // 课程考核评估标准
        public static final String[] ILO_ASSESS_STRUCT = {
                "ilo_number", "under_expectation", "meet_expectation", "beyond_expectation", "assess_struct_desc"
        };
        // 学习进度
        public static final String[] COURSE_SCHEDULE = {
                "week", "teaching_hour", "teach_mode", "teach_content", "ilos"
        };
        // 实验
        public static final String[] EXPERIMENT = {
                "experiment_desc", "experiment_number", "class_hour", "content_target", "task", "assessment_form", "score_standard"
        };
        // 实践
        public static final String[] PRACTICE = {
                "practice_desc", "practice_number", "class_hour", "content_target", "task", "assessment_form", "score_standard"
        };
    }
}
