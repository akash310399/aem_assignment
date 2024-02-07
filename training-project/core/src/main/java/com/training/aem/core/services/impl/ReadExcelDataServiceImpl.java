package com.training.aem.core.services.impl;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.entities.MobileEntity;
import com.training.aem.core.services.ReadExcelDataService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Component(service = ReadExcelDataService.class)
public class ReadExcelDataServiceImpl implements ReadExcelDataService {

    private static final String PARENT_PATH = "/content/training-project/mobilenodes";

    @Override
    public void getDataFromExcel(ResourceResolver resolver, String path) {
        Resource resource = resolver.getResource(path);
        List<MobileEntity> mobileEntities = new ArrayList<>();
        try {
            if (resource.isResourceType(CommonConstants.DAM_ASSET)) {
                Asset asset = resource.adaptTo(Asset.class);

                if (asset.getMimeType()
                        .equalsIgnoreCase(CommonConstants.EXCEL_MIME_TYPE)) {
                    Rendition rendition = asset.getOriginal();
                    InputStream inputStream = rendition
                            .adaptTo(InputStream.class);
                    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                    XSSFSheet sheet  =workbook.getSheetAt(0);

                    int row = sheet.getLastRowNum();
                    int col = sheet.getRow(0).getLastCellNum();
                    DataFormatter formatter = new DataFormatter();
                    for (int i = 1; i <= row; i++) {
                        XSSFRow currentRow = sheet.getRow(i);
                        if(currentRow!=null){
                            MobileEntity mobileEntity  = new MobileEntity();
                            mobileEntity
                                    .setName(currentRow.getCell(0)
                                            .getStringCellValue());
                            mobileEntity
                                    .setBrand(currentRow.getCell(1)
                                            .getStringCellValue());
                            mobileEntity
                                    .setPrice(currentRow.getCell(2)
                                            .getNumericCellValue());
                            mobileEntities.add(mobileEntity);
                        }

                    }
                    createNodeFromList(resolver,mobileEntities);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNodeFromList(ResourceResolver resolver, List<MobileEntity> mobileEntities){
        try {
            Resource parentResource = resolver.getResource(PARENT_PATH);
            if (parentResource != null) {
                Node parentNode = parentResource.adaptTo(Node.class);
                if (parentNode != null) {
                    for (MobileEntity mobileEntity : mobileEntities) {
                        String nodeName = "mobile_" + mobileEntity.getName(); // Generate a unique node name
                        Node mobileNode = parentNode.addNode(nodeName, "nt:unstructured");
                        mobileNode.setProperty("name", mobileEntity.getName());
                        mobileNode.setProperty("brand", mobileEntity.getBrand());
                        mobileNode.setProperty("price", mobileEntity.getPrice());
                        resolver.commit();
                    }
                } else {
                    throw new RuntimeException("Failed to adapt parent resource to a Node.");
                }
            } else {
                throw new RuntimeException("Parent resource not found at path: " + PARENT_PATH);
            }
        } catch (RepositoryException | PersistenceException e) {
            throw new RuntimeException("Error creating nodes", e);
        }
    }

}
