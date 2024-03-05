package com.training.aem.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class SearchProductServletTest {

    @Mock
    MockSlingHttpServletRequest request;

    @Mock
    MockSlingHttpServletResponse response;

    @Mock
    ResourceResolver resourceResolver;

    @InjectMocks
    SearchProductServlet searchProductServlet;

    @Test
    void doGet(AemContext context) throws ServletException, IOException, RepositoryException {

        String queryParam = "someQuery";

        String sqlQuery = "SELECT * FROM [cq:PageContent] AS product WHERE" +
                " ISDESCENDANTNODE(product, '/content/sample') AND" +
                " CONTAINS(product.*, '" + queryParam + "')";


        Session session = mock(Session.class);
        Workspace workspace = mock(Workspace.class);
        Query query = mock(Query.class);
        QueryResult result = mock(QueryResult.class);
        NodeIterator nodeIterator = mock(NodeIterator.class);
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        PrintWriter printWriter = mock(PrintWriter.class);

        QueryManager queryManager = mock(QueryManager.class);


        when(request.getParameter("query")).thenReturn(queryParam);
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.getWorkspace()).thenReturn(workspace);
        when(workspace.getQueryManager()).thenReturn(queryManager);
        when(queryManager.createQuery(anyString(),eq("JCR-SQL2"))).thenReturn(query);
        when(query.execute()).thenReturn(result);
        when(result.getNodes()).thenReturn(nodeIterator);
        when(nodeIterator.hasNext()).thenReturn(true, false); // Simulate one iteration
        when(nodeIterator.nextNode()).thenReturn(node);
        when(node.getProperty("jcr:title")).thenReturn(property);
        when(property.getString()).thenReturn("Title");

        when(response.getWriter()).thenReturn(printWriter);

        searchProductServlet.doGet(request,response);




    }
}