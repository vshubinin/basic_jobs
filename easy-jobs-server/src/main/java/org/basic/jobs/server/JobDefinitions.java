package org.basic.jobs.server;

import org.basic.jobs.job.JobDefinition;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.*;

import static java.lang.String.format;

public class JobDefinitions {

    static final String JOBS_DEFINITIONS_CONFIGURATION_FILE_PARAMETER_NAME = "easy.jobs.server.config.jobs.descriptor";
    static final String JOBS_DEFINITIONS_CONFIGURATION_PATH_PARAMETER_NAME = "easy.jobs.server.config.jobs.directory";

    private String sourceFile;
    private List<JobDefinition> jobDefinitions;

    public JobDefinitions(List<JobDefinition> jobDefinitions, String sourceFile) {
        this.jobDefinitions = jobDefinitions;
        this.sourceFile = sourceFile;
    }

    public List<JobDefinition> getJobDefinitions() {
        return jobDefinitions;
    }

    public void setJobDefinitions(List<JobDefinition> jobDefinitions) {
        this.jobDefinitions = jobDefinitions;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    static class Reader {
        private Yaml yaml = new Yaml();

        JobDefinitions read(File file) throws Exception {
            Iterable<Object> objects = yaml.loadAll(new FileReader(file));
            List<JobDefinition> jobDefinitions = new ArrayList<>();
            for (Object object : objects) {
                Map<String, Object> map = (Map<String, Object>) object;
                JobDefinition jobDefinition = createJobDefinition(map);
                jobDefinitions.add(jobDefinition);
            }
            return new JobDefinitions(jobDefinitions, file.getAbsolutePath());
        }

        private JobDefinition createJobDefinition(Map<String, Object> map) {
            JobDefinition jobDefinition = new JobDefinition();
            // todo add sanity checks
            jobDefinition.setId((Integer) map.get("id"));
            jobDefinition.setName((String) map.get("name"));
            jobDefinition.setDescription((String) map.get("description"));
            jobDefinition.setClazz((String) map.get("class"));
            jobDefinition.setMethod((String) map.get("method"));
            return jobDefinition;
        }
    }

    static class Validator {

        private ClassLoader classLoader;

        public Validator(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        public void validate(JobDefinition jobDefinition) throws InvalidJobDefinitionException {
            // validation is done in separate try/catch blocks to provide a detailed error message for each case

            // validate job class
            Class<?> jobClass;
            try {
                jobClass = Class.forName(jobDefinition.getClazz(), true, classLoader);
            } catch (ClassNotFoundException e) {
                throw new InvalidJobDefinitionException(format("Unable to load class %s for job '%s'. The job class must be included in the classpath", jobDefinition.getClazz(), jobDefinition.getName()), e);
            }

            // validate job constructor
            String error = format("Class %s for job '%s' does not provide a public default constructor", jobDefinition.getClazz(), jobDefinition.getName());
            try {
                Constructor<?> constructor = jobClass.getConstructor();
                if (constructor == null) {
                    throw new InvalidJobDefinitionException(error);
                }
            } catch (NoSuchMethodException e) {
                throw new InvalidJobDefinitionException(error, e);
            }

            // validate job method
            try {
                jobClass.getMethod(jobDefinition.getMethod());
            } catch (NoSuchMethodException e) {
                throw new InvalidJobDefinitionException(format("Unable to find method %s in class %s for job '%s'. The job execution method must be public", jobDefinition.getMethod(), jobDefinition.getClazz(), jobDefinition.getName()), e);
            }
        }
    }

    static class InvalidJobDefinitionException extends Exception {
        public InvalidJobDefinitionException(String message) {
            super(message);
        }

        public InvalidJobDefinitionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Map<Integer, JobDefinition> mapJobDefinitionsToJobIdentifiers() {
        Map<Integer, JobDefinition> jobDefinitionsMap = new HashMap<>();
        for (JobDefinition jobDefinition : jobDefinitions) {
            jobDefinitionsMap.put(jobDefinition.getId(), jobDefinition);
        }
        return jobDefinitionsMap;
    }

    static String getSimpleNameFrom(String fullyQualifiedName) {
        int beginIndex = fullyQualifiedName.lastIndexOf('.');
        if(beginIndex == -1) {
            return fullyQualifiedName;
        }
        return fullyQualifiedName.substring(beginIndex + 1);
    }
}
