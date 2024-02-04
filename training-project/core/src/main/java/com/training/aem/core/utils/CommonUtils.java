package com.training.aem.core.utils;

import com.google.gson.Gson;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.bean.ClientResponse;
import com.training.aem.core.entities.ProductEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.apache.oltu.oauth2.common.message.types.TokenType.BEARER;

public final class CommonUtils {
    public static <T> ClientResponse getClientResponse(final String method,
                                                       final String apiUrl,
                                                       final T requestObject,
                                                       final String token) throws Exception {
        ClientResponse clientResponse = new ClientResponse();

        if (StringUtils.isAllBlank(method, apiUrl)) {
            return clientResponse;
        }
        if (method.equals(CommonConstants.GET)) {
            final HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setHeader(CommonConstants.CONTENT_TYPE,
                    CommonConstants.CONTENT_TYPE_JSON);
            if (token != null) {
                httpGet.setHeader(HttpHeaders.AUTHORIZATION,
                        BEARER + token);
            }
            clientResponse = executeRequest(httpGet);
        }

        if (method.equals(CommonConstants.POST)) {
            final HttpPost httpPost = new HttpPost(apiUrl);
            String requestBody = new Gson().toJson(requestObject);
            if (StringUtils.isNotBlank(requestBody)) {
                httpPost.addHeader(CommonConstants.CONTENT_TYPE,
                        CommonConstants.CONTENT_TYPE_JSON);
                try {
                    httpPost.setEntity(new StringEntity(requestBody));
                } catch (UnsupportedEncodingException e) {

                }
            }
            clientResponse = executeRequest(httpPost);
        }
        return clientResponse;
    }

    public static ClientResponse executeRequest(final HttpUriRequest request) throws Exception {
        String result = StringUtils.EMPTY;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            HttpEntity httpEntity = httpResponse.getEntity();
            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setStatusCode(httpResponse
                    .getStatusLine().getStatusCode());
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity,
                        StandardCharsets.UTF_8);
                clientResponse.setData(result);

                EntityUtils.consume(httpEntity);
            }
            return clientResponse;

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return null;
    }


    // Sort the product list based on the price
    public static List<ProductEntity> sortProductEntitiesByPrice(List<ProductEntity> productEntityList, String sortOrder) {
        Collections.sort(productEntityList, new Comparator<ProductEntity>() {
            @Override
            public int compare(ProductEntity product1, ProductEntity product2) {
                // Assuming that ProductEntity has a getPrice() method
                double price1 = product1.getPrice();
                double price2 = product2.getPrice();

                if ("asc".equalsIgnoreCase(sortOrder)) {
                    return Double.compare(price1, price2);
                } else if ("dsc".equalsIgnoreCase(sortOrder)) {
                    return Double.compare(price2, price1);
                } else {
                    throw new IllegalArgumentException("Invalid sort order. Use 'ascending' or 'descending'.");
                }
            }
        });

        return productEntityList;
    }

    //----------- Get params From URL -----------

    public static List<String> getParamsFromURL(final SlingHttpServletRequest request){
        RequestPathInfo requestPathInfo = request.getRequestPathInfo();
        String suffix = requestPathInfo.getSuffix();
        if(!suffix.isEmpty()){
            String[] paramsArray = suffix.split("/");
            List<String> paramsList = Arrays.asList(paramsArray);
            List<String> params = new ArrayList<>(paramsList);
            params.remove(0); //removing first index because there is a whitespace at 1st index

            return params;
        }

        return new ArrayList<>();
    }

}
