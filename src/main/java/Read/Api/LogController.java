package Read.Api;

import Read.Domain.Book.Book;
import Read.Domain.Log.Log;
import Read.Domain.Log.LogStatusByUserIdDto;
import Read.Domain.Log.LogService;
import Read.Domain.ResponseDto.MyBookLogResponseDto;
import Read.Domain.ResponseDto.ReadBookResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by iljun on 2017-07-13.
 */
@RestController
@RequestMapping(value ="api/v1/log")
@Api(value="Log APi" ,description = "Log Api" ,basePath="/api/v1/Log")
@Slf4j
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;


    @ApiOperation(value="리드박스 전체보기" ,notes="리드박스 전체보기")
    @RequestMapping(value="readbook", method= RequestMethod.GET)
    public ReadBookResponseDto myreadbook(@RequestParam(value = "userId") Long id) {
        try {
            return logService.selectByUser(id);
        }catch (Exception e){
            logger.info("Log Controller search error"+e.getMessage());
        }
        return ReadBookResponseDto.ofEmpty();
    }

    @ApiOperation(value="상태별로 보기 api" ,notes="상태별로 보기 api")
    @RequestMapping(value="myreadbook/status", method= RequestMethod.GET)
    public ReadBookResponseDto bookstatus(@RequestParam(value = "userId") Long id, @RequestParam(value = "status") Long status) {
        try {
            return logService.selectUserAndStatus(id,status);
        }catch (Exception e){
            logger.info("Log Controller status error"+e.getMessage());
        }
        return ReadBookResponseDto.ofEmpty();
    }

    @ApiOperation(value = "내 북로그", notes= "내 북로그")
    @RequestMapping(value ="myBookLog", method = RequestMethod.GET)
    public List<MyBookLogResponseDto> myBookLog(@ApiParam(value = "유저아이디")
                     @RequestParam("userId") Long userId){
        try{
            return logService.myBookLog(userId);
        }catch(Exception e){
            logger.error("/api/v1/log/myBookLog error : " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
