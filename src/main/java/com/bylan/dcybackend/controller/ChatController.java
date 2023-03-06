package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.utils.FtpUtil;
import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.domain.ResultCode;
import com.bylan.dcybackend.service.ChatService;
import com.bylan.dcybackend.utils.FileProcessUtil;
import com.bylan.dcybackend.vo.FriendMsgStatusVO;
import com.bylan.dcybackend.vo.MessageVO;
import com.bylan.dcybackend.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.*;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/31 11:19
 */
@Api(value = "即时通信", tags = {"通信"})
@RequestMapping("/chat")
@RestController
public class ChatController {

    private static final Logger log = LogManager.getLogger(ChatController.class);

    @Autowired
    ChatService chatService;

    // 暂时放在这里

    @GetMapping(value = "/getUserNameById")
    @ApiOperation(value = "获取好友列表")
    @ResponseBody
    public CommonResult<String> getUserList(@RequestParam("user_id")
                                            @ApiParam("用户id")
                                            @NotBlank(message = "用户id不能为空")
                                            String userId) {
        log.info("用户id： {}", userId);
        String userName = chatService.getUserNameById(userId);
        return CommonResult.success(userName);
    }

    @GetMapping(value = "/getUserList")
    @ApiOperation(value = "获取好友列表")
    @ResponseBody
    public CommonResult<List<UserVO>> getUserList(@RequestParam("section_id")
                                                  @ApiParam("教学班id")
                                                  @NotNull(message = "section_id不能为空")
                                                  @PositiveOrZero(message = "section_id不能为负数")
                                                  Long sectionId) {
        log.info("教学班id： {}", sectionId);
        List<UserVO> userVOList = chatService.getFriendListBySectionId(sectionId);
        return CommonResult.success(userVOList);
    }

    @GetMapping(value = "/getMsgStatus")
    @ApiOperation(value = "获取聊天消息状态")
    @ResponseBody
    public CommonResult<List<FriendMsgStatusVO>> getMessageStatus(@RequestParam("section_id")
                                                                  @ApiParam("教学班id")
                                                                  @NotNull(message = "section_id不能为空")
                                                                  @PositiveOrZero(message = "section_id不能为负数")
                                                                  Long sectionId,
                                                                  @RequestParam("user_id")
                                                                  @ApiParam("接收者id")
                                                                  @NotBlank(message = "user_id不能为空")
                                                                  String studentId) {
        log.info("教学班id： {}, 学号：{}", sectionId, studentId);
        List<FriendMsgStatusVO> friendMsgStatusVOList = chatService.getFriendMsgStatus(sectionId, studentId);
        return CommonResult.success(friendMsgStatusVOList);
    }

    @GetMapping(value = "/getMessage")
    @ApiOperation(value = "获取好友列表")
    @ResponseBody
    public CommonResult<List<MessageVO>> getMessage(@RequestParam("section_id")
                                                     @ApiParam("教学班id")
                                                     @NotNull(message = "section_id不能为空")
                                                     @PositiveOrZero(message = "section_id不能为负数")
                                                     Long sectionId,
                                                     @RequestParam("from_id")
                                                     @ApiParam("当前用户id")
                                                     @NotBlank(message = "from_id不能为空")
                                                     String fromId,
                                                     @RequestParam("to_id")
                                                     @ApiParam("好友id")
                                                     @NotBlank(message = "to_id不能为空")
                                                     String toId,
                                                     @RequestParam(name = "start_id", required = false)
                                                     @ApiParam("起始消息id")
                                                     Long startMsgId) {
        log.info("获取聊天记录: {}{}{}", sectionId, fromId, toId);

        List<MessageVO> messageVOList = chatService.getChatHistory(sectionId, fromId, toId, startMsgId);
        return CommonResult.success(messageVOList);
    }

    @PostMapping("/upload")
    public CommonResult<String> upload(@RequestParam(name = "file", required = false) MultipartFile file) {
        if (file == null) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        if (file.getSize() > Constant.Chat.MAX_SIZE) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }

        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!Constant.Chat.SUPPORTED_FORMAT.contains(suffix.toUpperCase())) {
            return CommonResult.failed("请选择jpg,jpeg,gif,png格式的图片");
        }
        // 给文件加上时间戳
        String filename = FileProcessUtil.convertFilename(originalFilename);

        byte [] byteArr;
        try {
            byteArr = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed("上传文件失败");
        }
        InputStream fileInputStream = new ByteArrayInputStream(byteArr);
        //
        String resourcePath = FtpUtil.putFile(fileInputStream,filename,Constant.Path.CHAT_PICTURE);
//        String rootFilePath = String.join(File.separator, ROOT, path, filename);

//        String savePath;
//        try {
//            savePath = String.join(File.separator, ResourceUtils.getURL("classpath:").getPath(), Constant.Path.CHAT_PICTURE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return CommonResult.failed("获取项目路径失败");
//        }
//        String fileName = UUID.randomUUID() + "." + suffix;
//        File savePathFile = new File(savePath);
//        if (!savePathFile.exists()) {
//            //若不存在该目录，则创建目录
//            log.info("建立文件夹状态：{}", savePathFile.mkdir());
//        }
//        File newFile = new File(savePath, fileName);
//        String fileName = FileProcessUtil.uploadFile(file, savePath, file.getOriginalFilename());
//        try {
//            file.transferTo(newFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return CommonResult.failed("存储失败");
//        }

        //返回文件名称
        return CommonResult.success(resourcePath);
    }

}
