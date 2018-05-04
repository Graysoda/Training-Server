package soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.io.FileNotFoundException;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	public WebServiceConfig() {}

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
	public XsdSchema operationSchema() throws FileNotFoundException {
		return new SimpleXsdSchema((Resource) ResourceUtils.getFile("target/classes/xsd/operations.xsd"));
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
}
