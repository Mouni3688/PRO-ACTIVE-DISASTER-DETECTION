package com.proactive.disaster.helpers;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.stereotype.Component;



import jakarta.servlet.http.HttpSession;


@Component
public class SessionHelper {
@SuppressWarnings({"CallToPrintStackTrace", "empty-statement"})
public static void removeMessage() {
        try {
            System.out.println("removing message from session");
            @SuppressWarnings("null")
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error in SessionHelper: " + e);
            ;
            e.printStackTrace();
        }

    }
}
