package util;

import lombok.Getter;
import lombok.Setter;
import jakarta.ws.rs.core.Response;

@Getter
@Setter

public class CustomStatus implements Response.StatusType {
    private final int statusCode;
    private final String reasonPhrase;

    public CustomStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Response.Status.Family getFamily() {
        return null;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public Response.Status toEnum() {
        return Response.StatusType.super.toEnum();
    }
}
