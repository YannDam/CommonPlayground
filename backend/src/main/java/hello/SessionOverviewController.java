package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SessionOverviewController {

    private List<Session> sessions = new ArrayList();
    private final SessionRepository sessionRepository;
    @Autowired
    public SessionOverviewController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @RequestMapping("/getSessionList")
    public List<Session> getSessionList() {
        for (Session session: sessionRepository.findAll()) {
            sessions.add(session);
        }
        return sessions;
    }
}