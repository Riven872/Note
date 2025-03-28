package com.example.strategydemo.edu;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventHandlerStrategy {
    @Resource
    private List<IEvent> eventList;

    /**
     * 外部去调用的方法
     */
    public void EventHandle(String eventType, String json) {
        EventContext eventContext = new EventContext(this.getIEventStrategy(eventType));
        eventContext.executeStrategy(json);
    }

    /**
     * 获取确定的实现类
     * @param eventType
     * @return
     */
    private IEvent getIEventStrategy(String eventType){
        if (this.eventList != null && !this.eventList.isEmpty()) {
            for (IEvent event : eventList){
                if (event.getEventType().equals(eventType))
                    return event;
            }
        }
        return null;
    }
}
