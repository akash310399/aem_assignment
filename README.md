This project comprises the implementation of various functionalities and components within an AEM (Adobe Experience Manager) environment. Below is a list of tasks completed along with a brief description of each:

Create a project using archetype: Utilized archetype to create a new AEM project.
Create FAQ Component: Developed a component that accepts input for questions and answers from a multi-field.
Create Flexible Media Component: Designed a component allowing users to input either image paths or video URLs.
Product Detail Component: Implemented a component that retrieves product details from a third-party API based on the provided product ID.
Scheduler for Content Creation: Developed a scheduler to pull content from a third-party API and create pages in AEM using a specified template at a specific parent path.
Carousel Component for Products: Created a carousel component that dynamically pulls products based on the price range authored by the author.
Dynamic Product Information Component: Developed a component that renders product information based on request parameters by making calls to a third-party API.
Dynamic Script Tag Rendering: Implemented a dynamic flow where script tags are authored at the parent page level and rendered on child pages.
Workflow for Excel Sheet Upload: Created a workflow triggered by the upload of an Excel sheet to a specific DAM folder, parsing product-related values and storing them in the node structure.
Servlet for Product Carousel Sorting: Developed a servlet that responds with ordered product data based on specified sort types.
Test Cases: Wrote test cases for each functionality implemented.
Sling Job Listener for Page Creation: Developed a Sling job listener that notifies admin via email and AEM console when a page is created.
Environment Specific Scheduler Configuration: Ensured the scheduler triggers only in the author environment and created configs for running in stage run mode.
Auto-Populate Product Search: Implemented auto-populate search functionality for products based on user input.
Alert Component: Created an alert component pulling content from a parent path in a content fragment, with the ability to close the notification and persist its state.
Conditional Dialog Field: Added a field in the dialog that is visible only to users added to the content authors group.
Currency Configuration: Utilized CA configurations to display product prices based on user location and authored values.


Component, models and services structure:
	Model package: com.training.aem.core.models;
	Service package: com.training.aem.core.services
	Scheduler package: com.training.aem.core.schedulers
	CommonUtils: com.training.aem.core.utils
	servlets: com.training.aem.core.servlets
	


  1. FAQ Component:
     	Models: FaqComponentModel
     	component: jcr:title="FAQ Component"
     
  3. Image or Video
     	Models: ImageOrVideoComponentModel
     	component: jcr:title="Image and Video"
     
  4. Product Detail From API using page property
     	Models: ProductDetailModel
     	component: jcr:title="Product Detail Component" 
	service:ProductDetailService

  5. Question 5
	Scheduler:PageScheduler
	
  6. Question 6
	Model: ProductCarousalModel
	component: jcr:title="Product Carousal"
	service: ProductDetailService
  7. Question 7
	Model: SingleProductModel
	component: jcr:title="Single Product From URL"
	service: ProductDetailService, RestService
  8. Question 8
	Model: PagePropertyModel
	component: Extended the core page component

  9. Question 9
	Workflow process: com.training.aem.core.workflow.ParseExcelSheetProcessStep

  10.Question 10
	servlet: SortProductServlet
	service: ProductDetailService, RestService
	component: jcr:title="Products by sort type"

  11.Question 12
	Sling Job class: com.training.aem.core.jobs.PageSchedulerJob
	service: SendNotificationService
	
  12. Question 13
	OSGI Config: com.training.aem.core.configs.PageConfig
	
  13. Question 14
	Servlet: SearchProductServlet
	component: jcr:title="Search Component"
  14. Question 15
	service: AlertService
	servlet: AlertComponentServlet
	component: jcr:title="Alert Component"
  15 Question 16
	component: jcr:title="Author Group Component"

  16 Question 17
	CA Config: com.training.aem.core.caconfig.CurrencyConfig
	
	
	
	
	
	
	
