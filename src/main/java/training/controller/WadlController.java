package training.controller;

import com.autentia.web.rest.wadl.builder.ApplicationBuilder;
import com.autentia.web.rest.wadl.builder.impl.springframework.SpringWadlBuilderFactory;
import com.autentia.xml.schema.SchemaBuilder;
import net.java.dev.wadl._2009._02.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/rest", produces = {"application/xml"})
public class WadlController {

    private final ApplicationBuilder applicationBuilder;
    private final SchemaBuilder schemaBuilder;

    @Autowired
    public WadlController(RequestMappingHandlerMapping handlerMapping) {
        final SpringWadlBuilderFactory wadlBuilderFactory = new SpringWadlBuilderFactory(handlerMapping);
        applicationBuilder = wadlBuilderFactory.getApplicationBuilder();
        schemaBuilder = wadlBuilderFactory.getSchemaBuilder();
    }

    @ResponseBody
    @RequestMapping(value = "wadl", method = RequestMethod.GET)
    public Application generateWadl(HttpServletRequest request) {
        return applicationBuilder.build(request);
    }

    @ResponseBody
    @RequestMapping(value = "schema/{classTypeName}", method = RequestMethod.GET)
    public String generateSchema(@PathVariable String classTypeName) {
        return schemaBuilder.buildFor(classTypeName);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleException() {
        // Do nothing, just magic annotations.
    }
}