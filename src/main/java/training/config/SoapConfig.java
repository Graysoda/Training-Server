package training.config;

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
public class SoapConfig extends WsConfigurerAdapter {
    private static final String TARGET_NAMESPACE = "localhost:8080";//"https://training-server.herokuapp.com";

    public SoapConfig() {
        combineSchemas();
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/soap/*");
    }

    @Bean(name = "operations")
    public DefaultWsdl11Definition operationWsdl11Definition(XsdSchema operationsSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("OperationPort");
        wsdl11Definition.setLocationUri("/soap/");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(operationsSchema);
        return wsdl11Definition;
    }

    @Bean(name = "actor")
    public DefaultWsdl11Definition actorWsdl11Definition(XsdSchema actorSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ActorPort");
        wsdl11Definition.setLocationUri("/soap/actor");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(actorSchema);
        return wsdl11Definition;
    }

    @Bean("address")
    public DefaultWsdl11Definition addressWsdlDefinition(XsdSchema addressSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AddressPort");
        wsdl11Definition.setLocationUri("/soap/address");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(addressSchema);
        return wsdl11Definition;
    }
    @Bean("customer")
    public DefaultWsdl11Definition customerWsdlDefinition(XsdSchema customerSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CustomerPort");
        wsdl11Definition.setLocationUri("/soap/customer");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(customerSchema);
        return wsdl11Definition;
    }

    @Bean("films")
    public DefaultWsdl11Definition filmsWsdlDefinition(XsdSchema filmsSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("FilmsPort");
        wsdl11Definition.setLocationUri("/soap/film");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(filmsSchema);
        return wsdl11Definition;
    }

    @Bean("inventory")
    public DefaultWsdl11Definition inventoryWsdlDefinition(XsdSchema inventorySchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("InventoryPort");
        wsdl11Definition.setLocationUri("/soap/inventory");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(inventorySchema);
        return wsdl11Definition;
    }

    @Bean(name = "payments")
    public DefaultWsdl11Definition paymentsWsdlDefinition(XsdSchema paymentsSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PaymentsPort");
        wsdl11Definition.setLocationUri("/soap/payment");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(paymentsSchema);
        return wsdl11Definition;
    }

    @Bean(name = "rental")
    public DefaultWsdl11Definition rentalWsdlDefinition(XsdSchema rentalSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RentalPort");
        wsdl11Definition.setLocationUri("/soap/rental");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(rentalSchema);
        return wsdl11Definition;
    }

    @Bean(name = "staff")
    public DefaultWsdl11Definition staffWsdlDefinition(XsdSchema staffSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("StaffPort");
        wsdl11Definition.setLocationUri("/soap/staff");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(staffSchema);
        return wsdl11Definition;
    }

    @Bean(name = "language")
    public DefaultWsdl11Definition languageWsdlDefinition(XsdSchema languageSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("LanguagePort");
        wsdl11Definition.setLocationUri("/soap/language");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(languageSchema);
        return wsdl11Definition;
    }

    @Bean(name = "store")
    public DefaultWsdl11Definition storeWsdlDefinition(XsdSchema storeSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("StorePort");
        wsdl11Definition.setLocationUri("/soap/store");
        wsdl11Definition.setTargetNamespace(TARGET_NAMESPACE);
        wsdl11Definition.setSchema(storeSchema);
        return wsdl11Definition;
    }

    @Bean(name = "operationsSchema")
    public XsdSchema operationSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/operations.xsd"));
    }

    @Bean(name = "data_elements")
    public SimpleXsdSchema dataElements(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/data_elements.xsd"));
    }

    @Bean(name = "actorSchema")
    public SimpleXsdSchema actor(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/actor.xsd"));
    }

    @Bean(name = "addressSchema")
    public SimpleXsdSchema address(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/address.xsd"));
    }

    @Bean(name = "customerSchema")
    public SimpleXsdSchema customer(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/customer.xsd"));
    }

    @Bean(name = "filmsSchema")
    public SimpleXsdSchema films(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/films.xsd"));
    }

    @Bean(name = "inventorySchema")
    public SimpleXsdSchema inventory(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/inventory.xsd"));
    }

    @Bean(name = "paymentsSchema")
    public SimpleXsdSchema payments(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/payments.xsd"));
    }

    @Bean(name = "rentalSchema")
    public SimpleXsdSchema rental(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/rental.xsd"));
    }

    @Bean(name = "staffSchema")
    public SimpleXsdSchema staff(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/staff.xsd"));
    }

    @Bean(name = "languageSchema")
    public SimpleXsdSchema language(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/language.xsd"));
    }

    @Bean(name = "storeSchema")
    public SimpleXsdSchema store(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/store.xsd"));
    }

    private void combineSchemas() {
        StringBuilder sb = new StringBuilder();
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "           targetNamespace=\"my-namespace\"\n" +
                "			xmlns:data=\"my-namespace\"\n"+
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
                "staff.xsd",
                "language.xsd"
        };
        String line;
        BufferedReader reader;

        sb.append(header).append("\n<xs:include schemaLocation=\"data_elements.xsd\"/>\n");

        for (String fileName : fileNames) {
            StringBuilder fileString = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(new ClassPathResource("xsd/"+fileName).getFile())));
                line = reader.readLine();

                while (line != null){
                    fileString.append(line);
                    line = reader.readLine();
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(clean(fileString.toString())).append("\n");
        }

        sb.append(foot).append("\n");

        //System.out.println(sb.toString());

        try {
            File ops = new ClassPathResource("xsd/operations.xsd").getFile();
            ops.setWritable(true);
            FileWriter writer = new FileWriter(ops, false);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
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
