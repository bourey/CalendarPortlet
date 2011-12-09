package org.jasig.portlet.calendar.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class EmptyView extends AbstractView {

    public EmptyView() {
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    /**
     * Default content type. Overridable as bean property.
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonEncoding encoding = JsonEncoding.UTF8;

    private boolean prefixJson = false;

    private Set<String> renderedAttributes;

    /**
     * Sets the {@code ObjectMapper} for this view. If not set, a default {@link ObjectMapper#ObjectMapper() ObjectMapper}
     * is used.
     *
     * <p>Setting a custom-configured {@code ObjectMapper} is one way to take further control of the JSON serialization
     * process. For example, an extended {@link SerializerFactory} can be configured that provides custom serializers for
     * specific types. The other option for refining the serialization process is to use Jackson's provided annotations on
     * the types to be serialized, in which case a custom-configured ObjectMapper is unnecessary.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    /**
     * Sets the {@code JsonEncoding} for this converter. By default, {@linkplain JsonEncoding#UTF8 UTF-8} is used.
     */
    public void setEncoding(JsonEncoding encoding) {
        Assert.notNull(encoding, "'encoding' must not be null");
        this.encoding = encoding;
    }

    /**
     * Indicates whether the JSON output by this view should be prefixed with "{@code {} &&}". Default is false.
     *
     * <p> Prefixing the JSON string in this manner is used to help prevent JSON Hijacking. The prefix renders the string
     * syntactically invalid as a script so that it cannot be hijacked. This prefix does not affect the evaluation of JSON,
     * but if JSON validation is performed on the string, the prefix would need to be ignored.
     */
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }

    /**
     * Sets the attributes in the model that should be rendered by this view. When set, all other model attributes will be
     * ignored.
     */
    public void setRenderedAttributes(Set<String> renderedAttributes) {
        this.renderedAttributes = renderedAttributes;
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(getContentType());
        response.setCharacterEncoding(encoding.getJavaName());
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JsonGenerator generator =
            objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
        generator.writeRaw("    ");
    }

    /**
     * Filters out undesired attributes from the given model. The return value can be either another {@link Map}, or a
     * single value object.
     *
     * <p>Default implementation removes {@link BindingResult} instances and entries not included in the {@link
     * #setRenderedAttributes(Set) renderedAttributes} property.
     *
     * @param model the model, as passed on to {@link #renderMergedOutputModel}
     * @return the object to be rendered
     */
    protected Object filterModel(Map<String, Object> model) {
        Map<String, Object> result = new HashMap<String, Object>(model.size());
        Set<String> renderedAttributes =
                !CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
    
}
