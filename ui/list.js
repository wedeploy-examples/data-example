var list = document.querySelector('.list');

WeDeploy
	.data('data-boilerplatedata.wedeploy.io')
	.orderBy('id', 'desc')
	.limit(5)
	.get('tasks')
	.then(function(response) {
		appendTasks(response);
	})
	.catch(function(error) {
		console.error(error);
	});

WeDeploy
	.data('data-boilerplatedata.wedeploy.io')
	.limit(5)
	.orderBy('id', 'desc')
	.watch('tasks')
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
