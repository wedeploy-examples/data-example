var list = document.querySelector('.list');

WeDeploy
	.url('http://data.datademo.wedeploy.me/tasks/')
	.limit(5)
	.sort('id', 'desc')
	.get()
	.then(function(response) {
		console.info('Tasks:', response.body());

		var tasks = response.body();
		var taskList = '';

		tasks.forEach(function(task) {
			taskList += `<input type="text" value="${task.name}" readonly>`;
		});

		list.innerHTML = taskList;
	})
	.catch(function(error) {
		console.error(error);
	});