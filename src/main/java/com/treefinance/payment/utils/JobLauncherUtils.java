package com.treefinance.payment.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.StepLocator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/10/25 上午11:15
 * @Version 1.0
 */
public class JobLauncherUtils {
    private static final long JOB_PARAMETER_MAXIMUM = 1000000;

    /** Logger */
//    protected final Log logger = LogFactory.getLog(getClass());

    private JobLauncher jobLauncher;

    private Job job;

    private JobRepository jobRepository;

    private StepRunner stepRunner;

    /**
     * The Job instance that can be manipulated (e.g. launched) in this utility.
     *
     * @param job the {@link AbstractJob} to use
     */
    @Autowired
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * The {@link JobRepository} to use for creating new {@link JobExecution}
     * instances.
     *
     * @param jobRepository a {@link JobRepository}
     */
    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * @return the job repository
     */
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    /**
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * A {@link JobLauncher} instance that can be used to launch jobs.
     *
     * @param jobLauncher a job launcher
     */
    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    /**
     * @return the job launcher
     */
    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    /**
     * Launch the entire job, including all steps.
     *
     * @return JobExecution, so that the test can validate the exit status
     * @throws Exception thrown if error occurs launching the job.
     */
    public JobExecution launchJob() throws Exception {
        return this.launchJob(this.getUniqueJobParameters());
    }

    /**
     * Launch the entire job, including all steps
     *
     * @param jobParameters instance of {@link JobParameters}.
     * @return JobExecution, so that the test can validate the exit status
     * @throws Exception thrown if error occurs launching the job.
     */
    public JobExecution launchJob(JobParameters jobParameters) throws Exception {
        return getJobLauncher().run(this.job, jobParameters);
    }

    /**
     * @return a new JobParameters object containing only a parameter for the
     * current timestamp, to ensure that the job instance will be unique.
     */
    public JobParameters getUniqueJobParameters() {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("random", new JobParameter((long) (Math.random() * JOB_PARAMETER_MAXIMUM)));
        return new JobParameters(parameters);
    }

    /**
     * Convenient method for subclasses to grab a {@link StepRunner} for running
     * steps by name.
     *
     * @return a {@link StepRunner}
     */
    protected StepRunner getStepRunner() {
        if (this.stepRunner == null) {
            this.stepRunner = new StepRunner(getJobLauncher(), getJobRepository());
        }
        return this.stepRunner;
    }

    /**
     * Launch just the specified step in the job. A unique set of JobParameters
     * will automatically be generated. An IllegalStateException is thrown if
     * there is no Step with the given name.
     *
     * @param stepName The name of the step to launch
     * @return JobExecution
     */
    public JobExecution launchStep(String stepName) {
        return this.launchStep(stepName, this.getUniqueJobParameters(), null);
    }

    /**
     * Launch just the specified step in the job. A unique set of JobParameters
     * will automatically be generated. An IllegalStateException is thrown if
     * there is no Step with the given name.
     *
     * @param stepName The name of the step to launch
     * @param jobExecutionContext An ExecutionContext whose values will be
     * loaded into the Job ExecutionContext prior to launching the step.
     * @return JobExecution
     */
    public JobExecution launchStep(String stepName, ExecutionContext jobExecutionContext) {
        return this.launchStep(stepName, this.getUniqueJobParameters(), jobExecutionContext);
    }

    /**
     * Launch just the specified step in the job. An IllegalStateException is
     * thrown if there is no Step with the given name.
     *
     * @param stepName The name of the step to launch
     * @param jobParameters The JobParameters to use during the launch
     * @return JobExecution
     */
    public JobExecution launchStep(String stepName, JobParameters jobParameters) {
        return this.launchStep(stepName, jobParameters, null);
    }

    /**
     * Launch just the specified step in the job. An IllegalStateException is
     * thrown if there is no Step with the given name.
     *
     * @param stepName The name of the step to launch
     * @param jobParameters The JobParameters to use during the launch
     * @param jobExecutionContext An ExecutionContext whose values will be
     * loaded into the Job ExecutionContext prior to launching the step.
     * @return JobExecution
     */
    public JobExecution launchStep(String stepName, JobParameters jobParameters, @Nullable
        ExecutionContext jobExecutionContext) {
        if (!(job instanceof StepLocator)) {
            throw new UnsupportedOperationException("Cannot locate step from a Job that is not a StepLocator: job="
                + job.getName() + " does not implement StepLocator");
        }
        StepLocator locator = (StepLocator) this.job;
        Step step = locator.getStep(stepName);
        if (step == null) {
            step = locator.getStep(this.job.getName() + "." + stepName);
        }
        if (step == null) {
            throw new IllegalStateException("No Step found with name: [" + stepName + "]");
        }
        return getStepRunner().launchStep(step, jobParameters, jobExecutionContext);
    }
}
