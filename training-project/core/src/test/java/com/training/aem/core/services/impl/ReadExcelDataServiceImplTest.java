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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
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


        Asset asset = mock(Asset.class);
        Rendition rendition = mock(Rendition.class);
        XSSFWorkbook workbook = mock(XSSFWorkbook.class);
        XSSFSheet sheet1 = mock(XSSFSheet.class);
        InputStream inputStream = mock(InputStream.class);
//        byte[] excelData = createExcelData();
//
//        // Create ByteArrayInputStream from the byte array
//        InputStream inputStream = new ByteArrayInputStream(excelData);




        when(rendition.adaptTo(InputStream.class)).thenReturn(inputStream);
        when(resourceResolver.getResource("/content/test")).thenReturn(resource);
        when(resource.isResourceType(CommonConstants.DAM_ASSET)).thenReturn(true);
        when(resource.adaptTo(Asset.class)).thenReturn(asset);
        when(asset.getMimeType()).thenReturn(CommonConstants.EXCEL_MIME_TYPE);
        when(asset.getOriginal()).thenReturn(rendition);

        //doReturn(workbook).when(new XSSFWorkbook(inputStream));





        readExcelDataService.getDataFromExcel(resourceResolver,"/content/test");

    }


    private byte[] createExcelData() throws IOException {
        // Use Apache POI to create an XSSFWorkbook with your desired data
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Sheet1");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Hello, World!");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        }

    }



}