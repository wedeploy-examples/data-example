var list = document.querySelector('.list');

WeDeploy
	.url('http://data.datademo.wedeploy.me/tasks/')
	.limit(5)
	.sort('id', 'desc')
	.get()
	.then(function(response) {
		appendTasks(response.body());
	})
	.catch(function(error) {
		console.error(error);
	});

	WeDeploy
		.url('http://data.datademo.wedeploy.me/tasks/')
		.limit(5)
		.sort('id', 'desc')
		.watch()
		.on('changes', function(tasks) {
			appendTasks(tasks);
		});

	function appendTasks(tasks) {
		var taskList = '';

		tasks.forEach(function(task) {
			taskList += `<input type="text" value="${task.name}" readonly>`;
		});

		list.innerHTML = taskList;
	}