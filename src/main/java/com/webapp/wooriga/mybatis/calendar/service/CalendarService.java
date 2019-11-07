package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.vo.User;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAOImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CalendarService {
    private EmptyDaysDAOImpl emptyDaysDAOImpl;
    private UserDAO userDAO;

    Logger log = LoggerFactory.getLogger(CalendarService.class);
    @Autowired
    public CalendarService(EmptyDaysDAOImpl emptyDaysDAOImpl, UserDAO userDAO){
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;
        this.userDAO = userDAO;
    }

    public void insertDayOnCalendar(EmptyDays emptyDays, HttpServletResponse httpServletResponse){
        httpServletResponse.setStatus(200);
        try {
            emptyDaysDAOImpl.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            //duplicate date
            httpServletResponse.setStatus(400);
            log.error(e.toString());
        }
    }


    public CalendarInfo[] selectCalendarInfo(Map<String,String> family, HttpServletResponse response){
        response.setStatus(200);
        String firstDate = family.get("year") + "-" + family.get("month") + "-" + "01";
        String finalDate = family.get("year") +"-" +  family.get("month") + "-" + "31";
        String familyId = family.get("familyId");
        try {
            List<EmptyDays> emptyDays = emptyDaysDAOImpl.selectEmptyDay(familyId, firstDate, finalDate);
            if (emptyDays.size() > 0) {
                CalendarInfo[] calendarInfos = new CalendarInfo[emptyDays.size()];
                int i = 0;
                for (EmptyDays emptyDay : emptyDays) {
                    User user = userDAO.selectUserForCalendar(emptyDay);
                    calendarInfos[i] = new CalendarInfo(emptyDay.getEmptydate().toString(), user.getColor(), user.getName(), user.getRelationship(), user.getProfile());
                    i++;
                }
                return calendarInfos;
            }
            else{
                //정보가 없음
                response.setStatus(300);
                return null;
            }
        }
        catch(Exception e){
            //실패
                response.setStatus(400);
                log.error(e.toString());
            }
        return null;
    }
}
