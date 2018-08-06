# Wolox CI

This a Jenkins library built to make it easier for us at Wolox to configure pipelines without necessarily knowing about Jenkinsfile syntax.
All our projects are built using a Dockerfile

When using this library, your Jenkinsfile should look something like this:

```
@Library('wolox-ci') _

node {

  checkout scm

  woloxCi('.woloxci/config.yml');
}
```

It basically loads the library, clones the target repository and calls `woloxCi` to make its magic.
As an argument, `woloxCi` receives the path to a configuration yaml file.
This file looks something like this:

```
config:
  dockerfile: .woloxci/Dockerfile
  project_name: some-rails-project

services:
  - mssql

steps:
  analysis:
    - bundle exec rubocop -R app spec --format simple
    - bundle exec rubycritic --path ./analysis --minimum-score 80 --no-browser
  setup_db:
    - bundle exec rails db:create
    - bundle exec rails db:schema:load
  test:
    - bundle exec rspec
  security:
    - bundle exec brakeman --exit-on-error
  audit:
    - bundle audit check --update


environment:
  RAILS_ENV: test
  GIT_COMMITTER_NAME: a
  GIT_COMMITTER_EMAIL: b
  LANG: C.UTF-8
```

This file has different sections:

## Configuration

The section under the `config` label defines some basic configuration for this project:
1. The dockerfile that contains the image for the project to run.
2. The project name.

## Services

This section lists the project's dependencies. Each section will define and expose some environment variables that might be needed by the application:

### Microsoft SQL

When listing `mssql` as a service, this will build a docker image from [`microsoft/mssql-server-linux`](https://hub.docker.com/r/microsoft/mssql-server-linux/) exposing the following environment variables:

1. DB_USERNAME
2. DB_PASSWORD
3. DB_HOST
4. DB_PORT

### PostgreSQL

When listing `postgres` as a service, this will build a docker image from [`postgres`](https://hub.docker.com/_/postgres/) exposing the following environment variables:

1. DB_USERNAME
2. DB_PASSWORD
3. DB_HOST
4. DB_PORT

### Redis

When listing `redis` as a service, this will build a docker image from [`redis`](https://hub.docker.com/_/redis/) exposing the following environment variables:

1. REDIS_URL: the redis url that looks like this `redis://redis`

### MySQL

When listing `mysql` as a service, this will build a docker image from [`mysql`](https://hub.docker.com/_/mysql/) exposing the following environment variables:

1. DB_USERNAME
2. DB_PASSWORD
3. DB_HOST
4. DB_PORT

### MongoDB

When listing `mongo` as a service, this will build a docker image from [`mongo`](https://hub.docker.com/_/mongo/) exposing the following environment variables:

1. DB_USERNAME
2. DB_PASSWORD
3. DB_HOST
4. DB_PORT

## Steps

This section lets you define the steps you need to build your project. Each level inside the `steps` section is a stage in the Jenkins pipeline and each item in the stage is a command to run. In the case above, there are 5 stages named:

1. analysis
2. setup_db
3. test
4. security
5. audit


The analysis stage, for example, runs the following commands:

1. `bundle exec rubocop -R app spec --format simple`
2. `bundle exec rubycritic --path ./analysis --minimum-score 80 --no-browser`


## Environment

This section lets you set up custom environment variables. Each item inside this section defines a variable with its value.
