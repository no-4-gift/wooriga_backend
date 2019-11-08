package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.vo.User;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAOImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {
    private EmptyDaysDAOImpl emptyDaysDAOImpl;
    private UserDAO userDAO;

    Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    @Autowired
    public CalendarServiceImpl(EmptyDaysDAOImpl emptyDaysDAOImpl, UserDAO userDAO){
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;
        this.userDAO = userDAO;
    }

    @Override
    public void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException{
        try {
            emptyDaysDAOImpl.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            throw new NoStoringException();
        }
    }

    @Override
    public CalendarInfo[] selectCalendarInfo(Map<String,String> family) throws RuntimeException{
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
        }
        catch(Exception e){
                throw new NoMatchPointException();
            }
        return null;
    }
}
