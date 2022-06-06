Basic Jobs stores meta-data of jobs in a relational database. Three tables are used: `job`, `job_execution` and `job_execution_request`.
The job server polls the `job_execution_request` table regularly looking for pending job execution requests.
When a job execution request comes in, the job server creates a job instance of the requested job and executes it


The job server uses a pool of worker threads to execute jobs
Job execution requests are submitted through a RESTful API

## How to use it ?
 You should get a directory with the following content:

```shell
$>cd basic-jobs-dist-0.4
$>tree -d
├── conf
├── drivers
│   ├── h2
│   └── mysql
├── jobs
└── lib
    ├── admin
    └── server
```

Run the job server with the following command:

```
java -cp "drivers/h2/*:lib/server/*" \
 -Deasy.jobs.database.config.file=$(pwd)/conf/database.properties \
 -Deasy.jobs.database.config.init=true \
 -Deasy.jobs.server.config.jobs.directory=$(pwd)/jobs \
 -Deasy.jobs.server.config.jobs.descriptor=$(pwd)/conf/jobs.yml \
 org.basic.jobs.server.JobServer
```

If you are on windows, use the following command:

```
java -cp "drivers/h2/*;lib/server/*" ^
 -Deasy.jobs.database.config.file=%cd%\conf\database.properties ^
 -Deasy.jobs.database.config.init=true ^
 -Deasy.jobs.server.config.jobs.directory=%cd%\jobs ^
 -Deasy.jobs.server.config.jobs.descriptor=%cd%\conf\jobs.yml ^
 org.basic.jobs.server.JobServer
```

That's it! The job server should be up and running waiting for you to submit job execution requests.

## REST API

By default, the job server will be started on `localhost:8080`. You can change the port as well as other parameter as described in the 
In the previous command, we used H2 database which is fine for testing but not recommended for production. You can use another if you want.

The distribution comes with a sample job called `HelloWorldJob` located in the `jobs` directory. Here is its source code:

```java
public class HelloWorldJob {

    private String name;

    public void doWork() {
        System.out.println("Hello " + name);
    }

    // getter and setter for name
}
```

Jobs in Basic Jobs are regular Java classes. There is no annotation to add, no interface to implement or class to extend.
Your jobs are simple POJOs. Easy Jobs is not intrusive! But you have to tell it where to find your job using a job descriptor:

```yaml
---
id: 1
name: hello world job
class: HelloWorldJob
method: doWork
```

This job descriptor `jobs.yml` can be found in `conf` directory. It gives Easy Jobs all required information to identify your job and execute it when requested.
Let's first check if the `HelloWorldJob` is registered:

```json
$>curl localhost:8080/jobs
[
 {
  "id": 1,
  "name": "Hello World Job",
  "description" : "description: A job that says 'hello' to the given name"
 }
]
```

The job server has successfully loaded the job. Now, we can submit a job execution request:

```shell
$>curl \
  --request POST \
  --header "Content-Type: application/json" \
  --data '{"jobId":"1", "name":"world"}' \
  localhost:8080/requests
```

The job server will pick up this request in the next polling run, create a job instance of the `HelloWorldJob` and execute it with parameter `name=world`.
Let's check job executions on the `/executions` endpoint:

```json
$>curl localhost:8080/executions
[
 {
  "id": 1,
  "requestId": 1,
  "jobExecutionStatus": "FINISHED",
  "jobExitStatus": "SUCCEEDED",
  "startDate": [
      2017, 6, 23, 9, 25, 13, 939000000
  ],
  "endDate": [
      2017, 6, 23, 9, 25, 13, 959000000
  ]
 }
]
```

Great! the job has been executed and finished successfully. You should have seen this in the server's log:

```
INFO: Received a new job execution request for job 1 with parameters {jobId=1, name=world}
INFO: Found 1 pending job execution request(s)
INFO: Creating a new job for request n° 1 with parameters [{"jobId":"1", "name":"world"}]
INFO: Submitted a new job for request n° 1
INFO: Processing job execution request with id 1
Hello world
INFO: Successfully processed job execution request with id 1
```

That's all!

## Admin Web Interface

Easy Jobs comes with an administration web interface:


This interface gives you some insights on a running job server and allows you to submit job execution requests.
To run the application, use the following command:

```
java -cp "drivers/h2/*:lib/admin/*" \
 -Deasy.jobs.database.config.file=$(pwd)/conf/database.properties \
 -Dserver.port=9000 \
 org.basic.jobs.admin.Application
```

On windows, you can use the following command:

```
java -cp "drivers/h2/*;lib/admin/*" ^
 -Deasy.jobs.database.config.file=%cd%\conf\database.properties ^
 -Dserver.port=9000 ^
 org.basic.jobs.admin.Application
```


For demonstration purpose, you can login to the application using `admin/admin` credentials.
Make sure the server and the admin interface are running on different ports. In this example, the server is running on port `8080` (by default) and the admin interface on port `9000`

