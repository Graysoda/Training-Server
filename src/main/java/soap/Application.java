package soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.*;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		combineSchemas();
		SpringApplication.run(Application.class, args);
	}

	private static void combineSchemas() {
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
		BufferedReader reader;

		sb.append(header).append("\n<xs:include schemaLocation=\"data_elements.xsd\"/>");

		for (String fileName : fileNames) {
			StringBuilder fileString = new StringBuilder();
			try {
                System.out.println(ResourceUtils.getFile("target/classes/xsd/").getPath());
                System.out.println(ResourceUtils.getFile("target/classes/xsd/").listFiles().toString());
				reader = new BufferedReader(new InputStreamReader(ResourceUtils.getURL("target/classes/xsd/"+fileName).openStream()));
				line = reader.readLine();

				while (line != null){
					fileString.append(line);
					line = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			sb.append(clean(fileString.toString()));
		}

		sb.append(foot).append("\n");

		try {
			File ops = new File(ResourceUtils.getFile("target/classes/xsd/operations.xsd").toURI());
			FileWriter writer = new FileWriter(ops, false);
			writer.write(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String clean(String s) {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String schemaHeader = "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"           targetNamespace=\"my-namespace\"           xmlns:data=\"my-namespace\"           elementFormDefault=\"qualified\">";
		String include = "<xs:include schemaLocation=\"data_elements.xsd\"/>";
		String foot = "</xs:schema>";

		return s.replace(xmlHeader,"").replace(schemaHeader,"").replace(include,"").replace(foot,"").trim();
	}
}
