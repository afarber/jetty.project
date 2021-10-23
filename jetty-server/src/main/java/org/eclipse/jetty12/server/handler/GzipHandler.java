package org.eclipse.jetty12.server.handler;

import java.nio.ByteBuffer;

import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty12.server.Content;
import org.eclipse.jetty12.server.Handler;
import org.eclipse.jetty12.server.Request;
import org.eclipse.jetty12.server.Response;

public class GzipHandler extends Handler.Wrapper<Request>
{
    private static final HttpField ACCEPT_GZIP = new HttpField(HttpHeader.ACCEPT, "gzip");
    private static final HttpField CONTENT_ENCODING_GZIP = new HttpField(HttpHeader.CONTENT_ENCODING, "gzip");

    @Override
    public boolean handle(Request request, Response response)
    {
        // TODO more conditions than this
        // TODO handle other encodings
        if (!request.getHeaders().contains(ACCEPT_GZIP, CONTENT_ENCODING_GZIP))
            return super.handle(request, response);

        HttpFields updated = HttpFields.from(request.getHeaders(), f ->
        {
            if (f.getHeader() != null)
            {
                // TODO this is too simple
                if (CONTENT_ENCODING_GZIP.equals(f))
                    return null;
                if (f.getHeader().equals(HttpHeader.CONTENT_LENGTH))
                    return null;
            }
            return f;
        });

        return super.handle(
            new Request.Wrapper(request)
            {
                @Override
                public HttpFields getHeaders()
                {
                    return updated;
                }

                @Override
                public long getContentLength()
                {
                    // TODO hide the content length
                    return -1;
                }

                @Override
                public Content readContent()
                {
                    // TODO inflate data
                    return super.readContent();
                }
            },
            new Response.Wrapper(response)
            {
                @Override
                public void write(boolean last, Callback callback, ByteBuffer... content)
                {
                    // TODO deflate data
                    super.write(last, callback, content);
                }
            });
    }
}