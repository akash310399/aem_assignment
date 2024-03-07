package com.training.aem.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ReCaptchaServletTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    ReCaptchaServlet reCaptchaServlet;

    @Mock
    MockSlingHttpServletRequest request;

    @Mock
    MockSlingHttpServletResponse response;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ReCaptchaServlet.class);
    }

    @Test
    void doPost() throws ServletException, IOException {
        String token = "YSD98H98FDSH98FNDSEWKJDS89EWWE898FS7D89DS";

        PrintWriter printWriter = mock(PrintWriter.class);
        when(request.getParameter("token")).thenReturn(token);
        when(response.getWriter()).thenReturn(printWriter);
        reCaptchaServlet.doPost(request,response);
    }
}