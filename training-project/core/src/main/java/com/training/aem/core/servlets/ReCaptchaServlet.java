package com.training.aem.core.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.drew.lang.Charsets.UTF_8;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,},
        name = "com.training.aem.core.servlets.ReCaptchaServlet"
)
@SlingServletPaths(value = {"/bin/validateCaptcha"})
@ServiceDescription("Servlet to validate recaptcha")
public class ReCaptchaServlet extends SlingAllMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaServlet.class);
    private static final long serialVersionUID = 1L;

    private JsonParser jsonParser = new JsonParser();

    private static final String recaptchaSiteUrl = "https://www.google.com/recaptcha/api/siteverify";
    private static final String siteKey = "6Ld-aYopAAAAANJCp871EfcD9sQlX-4iN4tDlmcb";

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding(UTF_8.toString());
        String token = request.getParameter("token");

        if (isTokenValid(token)) {
            response.getWriter().write("Captcha is authenticated");
        } else {
            response.getWriter().write("Captcha is not authenticated");
        }

    }

    private boolean isTokenValid(String token) throws IOException {
        boolean isValid = false;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(recaptchaSiteUrl);
            List<NameValuePair> params = new ArrayList<>(2);
            params.add(new BasicNameValuePair("secret", siteKey));
            params.add(new BasicNameValuePair("response", token));
            httpPost.setEntity(new UrlEncodedFormEntity(params, UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String captchaResponse = getStringFromInputStream(inputStream);
                JsonObject jsonObject = jsonParser.parse(captchaResponse).getAsJsonObject();
                LOGGER.debug("Captcha Response : {}", jsonObject);
                if (jsonObject.get("success").getAsBoolean()) {
                    isValid = true;
                }
            }
            return isValid;
        } catch (IOException e) {
            LOGGER.error("Exception {}", e.getLocalizedMessage());
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return Boolean.FALSE;
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("Exception {}", e.getLocalizedMessage());
        } finally {
            IOUtils.closeQuietly();
        }
        return sb.toString();
    }
}
