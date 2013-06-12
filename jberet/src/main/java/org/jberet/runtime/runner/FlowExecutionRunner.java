/*
 * Copyright (c) 2013 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.runtime.runner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.batch.runtime.BatchStatus;

import org.jberet.job.Flow;
import org.jberet.runtime.context.AbstractContext;
import org.jberet.runtime.context.FlowContextImpl;

import static org.jberet.util.BatchLogger.LOGGER;

public final class FlowExecutionRunner extends CompositeExecutionRunner<FlowContextImpl> implements Runnable {
    private Flow flow;
    private CountDownLatch latch;

    public FlowExecutionRunner(FlowContextImpl flowContext, CompositeExecutionRunner enclosingRunner, CountDownLatch latch) {
        super(flowContext, enclosingRunner);
        this.flow = flowContext.getFlow();
        this.latch = latch;
    }

    @Override
    protected List<?> getJobElements() {
        return flow.getDecisionOrFlowOrSplit();
    }

    @Override
    public void run() {
        batchContext.setBatchStatus(BatchStatus.STARTED);
        jobContext.setBatchStatus(BatchStatus.STARTED);

        try {
            runFromHeadOrRestartPoint(null);
        } catch (Throwable e) {
            LOGGER.failToRunJob(e, jobContext.getJobName(), flow.getId(), flow);
            batchContext.setBatchStatus(BatchStatus.FAILED);
            for (AbstractContext c : batchContext.getOuterContexts()) {
                c.setBatchStatus(BatchStatus.FAILED);
            }
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }

        if (batchContext.getBatchStatus() == BatchStatus.STARTED) {  //has not been marked as failed, stopped or abandoned
            batchContext.setBatchStatus(BatchStatus.COMPLETED);
        }

        if (batchContext.getBatchStatus() == BatchStatus.COMPLETED) {
            String next = resolveTransitionElements(flow.getTransitionElements(), flow.getNext(), false);
            enclosingRunner.runJobElement(next, batchContext.getFlowExecution().getLastStepExecution());
        }
    }

}
