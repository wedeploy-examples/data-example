# WeDeploy Data / JavaScript Boilerplate

This example will guide you through the process of deploying a WeDeploy Data container.

> We assume that you already have the [WeDeploy CLI](https://github.com/wedeploy/cli) installed and [Docker](https://docs.docker.com/docker-for-mac/) is running.

> [Visit this guide](#) if you need help setting that up.

## Running locally

1. Start local infrastructure:

	```sh
we run
	```

2. Clone this repository:

	```sh
git clone -b js https://github.com/wedeploy/boilerplate-data.git boilerplate-data-js
cd boilerplate-data-js
	```

3. Link this container with the local infrastructure:

	```sh
we link
	```

4. Now your container is ready to be used:

	```
http://datademo.wedeploy.me
	```

5. [Visit the documentation to learn how to start saving and fetching data in realtime.](#)

## Deploying to the cloud

1. [Fork this repository](https://github.com/wedeploy/boilerplate-data/fork).
2. Go to the [Dashboard](http://dashboard.wedeploy.io).
3. Create a project.
4. In the sidebar, click on *GitHub Integration*.
5. Type your repository URL and `master` branch.
6. Click on *Update Project* and wait a few seconds.
7. Once you see them in the Dashboard, your container will be ready to be used.

	```
http://datademo.wedeploy.io
	```

8. [Visit the documentation to learn how to start saving and fetching data in realtime.](#)