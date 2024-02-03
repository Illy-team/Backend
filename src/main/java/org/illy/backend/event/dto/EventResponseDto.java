package org.illy.backend.event.dto;

import lombok.*;
import org.illy.backend.event.entity.Event;
import org.illy.backend.event.entity.EventType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long eventId;
    private EventType type;
    private String hostName;
    private String image;
    private String category;
    private String title;
    private String applyDate;
    private String link;
    private String tasks;
    private String requirement;
    private String preferred;
    private String activityDate;

    public EventResponseDto(Event event) {
        eventId = event.getId();
        type = event.getType();
        hostName = event.getHostName();
        image = event.getImage();
        category = event.getCategory();
        title = event.getTitle();
        applyDate = event.getApplyDate();
        link = event.getLink();
        tasks = event.getTasks();
        requirement = event.getRequirement();
        preferred = event.getPreferred();
        activityDate = event.getActivityDate();
    }

    public String calcDateComparison(Date targetDate) {
        if (targetDate == null) {
            return "상시채용";
        }
        Date currentDate = new Date();
        long diffInMillis = targetDate.getTime() - currentDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

        if (diffInDays == 0) {
            return "D-DAY";
        } else if (diffInDays > 0) {
            return "D-" + diffInDays;
        } else {
            return "D+" + Math.abs(diffInDays);
        }
    }

    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");

        // "상시채용"인 경우 null 반환
        if (dateString.equals("상시채용")) {
            return null;
        }

        // "~"가 있는 경우는 기간 형식으로 처리
        if (dateString.contains("~")) {
            String[] dateParts = dateString.split(" ~ ");
            String endDateString = dateParts[1].trim();

            Date endDate = dateFormatter.parse(endDateString);
            return endDate;
        } else {
            // "~"가 없는 경우는 날짜 형식으로 처리
            return dateFormatter.parse(dateString.replace('-','.'));
        }
    }

    // Getter 메서드를 수정하여 필요한 변환을 수행
    public String convertApplyDate(String date) throws ParseException {
        return calcDateComparison(parseDate(date));
    }
}
