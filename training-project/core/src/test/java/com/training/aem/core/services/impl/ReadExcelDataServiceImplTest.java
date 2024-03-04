package com.training.aem.core.services.impl;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.Rendition;
import com.training.aem.core.CommonConstants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class ReadExcelDataServiceImplTest {

    @InjectMocks
    ReadExcelDataServiceImpl readExcelDataService;

    AemContext ctx = new AemContext();

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Resource resource;

    @Mock
    XSSFSheet sheet;


    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProductDetailServiceImpl.class);
    }

    @Test
    void getDataFromExcel() throws Exception {

        String msg = "Hello World";

        Asset asset = mock(Asset.class);
        Rendition rendition = mock(Rendition.class);
        InputStream inputStream = mock(InputStream.class);
        XSSFWorkbook workbook = mock(XSSFWorkbook.class);
        XSSFSheet sheet = mock(XSSFSheet.class);

// Mock resource resolver behavior
        when(resourceResolver.getResource("/content/test")).thenReturn(resource);
        when(resource.isResourceType(CommonConstants.DAM_ASSET)).thenReturn(true);
        when(resource.adaptTo(Asset.class)).thenReturn(asset);

// Mock asset behavior
        when(asset.getMimeType()).thenReturn(CommonConstants.EXCEL_MIME_TYPE);
        when(asset.getOriginal()).thenReturn(rendition);
        when(rendition.adaptTo(InputStream.class)).thenReturn(new ByteArrayInputStream(msg.getBytes()));
        whenNew(XSSFWorkbook.class).withArguments(inputStream).thenReturn(workbook);




        //readExcelDataService.getDataFromExcel(resourceResolver,"/content/test");

    }

}