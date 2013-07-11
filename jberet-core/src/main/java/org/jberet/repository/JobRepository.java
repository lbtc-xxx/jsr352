/*
 * Copyright (c) 2012-2013 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.repository;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;

import org.jberet.job.Job;
import org.jberet.runtime.JobExecutionImpl;
import org.jberet.runtime.JobInstanceImpl;
import org.jberet.runtime.StepExecutionImpl;

public interface JobRepository {
    boolean addJob(Job job);

    boolean removeJob(String jobId);

    Job getJob(String jobId);

    Collection<Job> getJobs();

    JobInstanceImpl createJobInstance(Job job, String applicationName, ClassLoader classLoader);
    void removeJobInstance(long jobInstanceId);
    JobInstance getJobInstance(long jobInstanceId);
    List<JobInstance> getJobInstances();

    JobExecutionImpl createJobExecution(JobInstanceImpl jobInstance, Properties jobParameters);
    JobExecution getJobExecution(long jobExecutionId);
    List<JobExecution> getJobExecutions();

    StepExecutionImpl createStepExecution(String stepName);
    void addStepExecution(JobExecutionImpl jobExecution, StepExecutionImpl stepExecution);

    void savePersistentData(JobExecution jobExecution, StepExecutionImpl stepExecution);

}