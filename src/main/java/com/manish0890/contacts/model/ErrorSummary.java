package com.manish0890.contacts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "error", "message", "path", "timestamp", "trace"})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorSummary {
    private Integer status;
    private String error;
    private String message;
    private String timestamp;
    private String trace;
    private String path;

    public boolean equals(Object object) {
        if (!(object instanceof ErrorSummary)) {
            return false;
        } else if (this == object) {
            return true;
        } else {
            ErrorSummary otherObject = (ErrorSummary)object;
            return EqualsBuilder.reflectionEquals(this, otherObject);
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
