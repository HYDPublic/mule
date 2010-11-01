/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.processor;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorChain;
import org.mule.api.processor.policy.AroundPolicy;
import org.mule.util.ObjectUtils;

import java.util.Collections;
import java.util.List;


public class NullMessageProcessor implements MessageProcessorChain
{
    public MuleEvent process(MuleEvent event) throws MuleException
    {
        return event;
    }

    @Override
    public String toString()
    {
        return ObjectUtils.toString(this);
    }

    public List<MessageProcessor> getMessageProcessors()
    {
        return Collections.emptyList();
    }

    public void add(AroundPolicy policy)
    {
    }

    public AroundPolicy removePolicy(String policyName)
    {
        return null;
    }

    public List<AroundPolicy> getActivePolicies()
    {
        return Collections.unmodifiableList(Collections.<AroundPolicy>emptyList());
    }

    public void clearPolicies()
    {
    }
}
