//
// ========================================================================
// Copyright (c) 1995-2021 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Public License v. 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
// which is available at https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty12.server.handler;

import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty12.server.Handler;
import org.eclipse.jetty12.server.Request;
import org.eclipse.jetty12.server.Response;

public class RewriteHandler extends Handler.Nested
{
    @Override
    public boolean handle(Request request, Response response)
    {
        Request rewritten = rewrite(request, response);
        if (response.isCommitted())
            return true;
        return super.handle(request, response);
    }

    protected Request rewrite(Request request, Response response)
    {
        // TODO run the rules, but ultimately wrap for any changes:
        return new Request.Wrapper(request)
        {
            @Override
            public HttpURI getURI()
            {
                // TODO return alternative URI
                return super.getURI();
            }

            @Override
            public HttpFields getHeaders()
            {
                // TODO return modified headers
                return super.getHeaders();
            }
        };
    }
}