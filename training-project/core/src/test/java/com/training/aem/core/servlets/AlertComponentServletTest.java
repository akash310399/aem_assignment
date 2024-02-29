package com.training.aem.core.servlets;

import com.google.gson.Gson;
import com.training.aem.core.entities.AlertContentFragmentEntity;
import com.training.aem.core.services.AlertService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AlertComponentServletTest {

    @InjectMocks
    AlertComponentServlet alertComponentServlet;

    @Mock
    AlertService alertService;




    @Test
    void doGet(AemContext context) throws ServletException, IOException {

        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        List<AlertContentFragmentEntity> mockList = new ArrayList<>();


        // Create and add the first entry
        AlertContentFragmentEntity fragmentEntities = new AlertContentFragmentEntity();
        fragmentEntities.setName("abc");
        fragmentEntities.setMessage("abc");
        fragmentEntities.setAlert("abc");
        mockList.add(fragmentEntities);

        when(alertService.getAlertsFromContentFragment()).thenReturn(mockList);

        Gson json = new Gson();
        String list = json.toJson(mockList);

        //doNothing().when(response.getWriter().write(list));


        alertComponentServlet.doGet(request, response);
    }
}