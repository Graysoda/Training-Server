package soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.io.*;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	public WebServiceConfig() {combineSchemas();}

	@Bean
	public ServletRegistrationBean messageDispatherServlet(ApplicationContext applicationContext){
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "operations")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema operationsSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("OperationPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://localhost:8080");
		wsdl11Definition.setSchema(operationsSchema);
		return wsdl11Definition;
	}

	@Bean(name = "operationsSchema")
	public XsdSchema operationSchema(){
		ClassPathResource resource = new ClassPathResource("xsd/operations.xsd");

		return new SimpleXsdSchema(resource);
	}

	@Bean(name = "data_elements")
	public SimpleXsdSchema dataElements(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/data_elements.xsd"));
	}

	@Bean(name = "actor")
	public SimpleXsdSchema actorSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/actor.xsd"));
	}

	@Bean(name = "address")
	public SimpleXsdSchema addressSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/address.xsd"));
	}

	@Bean(name = "customer")
	public SimpleXsdSchema customerSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/customer.xsd"));
	}

	@Bean(name = "films")
	public SimpleXsdSchema filmSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/films.xsd"));
	}

	@Bean(name = "inventory")
	public SimpleXsdSchema inventorySchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/inventory.xsd"));
	}

	@Bean(name = "payments")
	public SimpleXsdSchema paymentsSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/payments.xsd"));
	}

	@Bean(name = "rental")
	public SimpleXsdSchema rentalSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/rental.xsd"));
	}

	@Bean(name = "staff")
	public SimpleXsdSchema staffSchema(){
		return new SimpleXsdSchema(new ClassPathResource("xsd/staff.xsd"));
	}

	private void combineSchemas() {
		StringBuilder sb = new StringBuilder();
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"" +
				"           targetNamespace=\"my-namespace\"" +
				"			xmlns:data=\"my-namespace\""+
				"           elementFormDefault=\"qualified\">";
		String foot = "</xs:schema>";
		String[] fileNames = new String[]{
				"actor.xsd",
				"address.xsd",
				"customer.xsd",
				"films.xsd",
				"inventory.xsd",
				"payments.xsd",
				"rental.xsd",
				"staff.xsd"
		};
		String line;
		BufferedReader reader = null;

		sb.append(header).append("\n<xs:include schemaLocation=\"data_elements.xsd\"/>");

		for (String fileName : fileNames) {
			StringBuilder fileString = new StringBuilder();
			try {
				//ResourceUtils.getFile("target/classes/xsd/"+fileName)
				reader = new BufferedReader(new InputStreamReader(new ClassPathResource("xsd/"+fileName).getInputStream()));
				line = reader.readLine();

				while (line != null){
					fileString.append(line);
					line = reader.readLine();
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sb.append(clean(fileString.toString()));
		}

		sb.append(foot).append("\n");

		try {
			//ResourceUtils.getFile("target/classes/xsd/operations.xsd");
			File ops = new ClassPathResource("xsd/operations.xsd").getFile();
			FileWriter writer = new FileWriter(ops, false);
			writer.write(sb.toString());
			writer.flush();
			writer.close();

			System.out.println("file exists = "+ops.exists());
			System.out.println("file name = "+ops.getName());
			System.out.println("file path = "+ops.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String clean(String s) {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String schemaHeader = "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"           targetNamespace=\"my-namespace\"           xmlns:data=\"my-namespace\"           elementFormDefault=\"qualified\">";
		String include = "<xs:include schemaLocation=\"data_elements.xsd\"/>";
		String foot = "</xs:schema>";

		return s.replace(xmlHeader,"").replace(schemaHeader,"").replace(include,"").replace(foot,"").trim();
	}
}
