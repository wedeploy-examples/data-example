var form = document.querySelector('form');

form.addEventListener('submit', function(e) {
	e.preventDefault();

WeDeploy.data('http://data.boilerplate-data.wedeploy.io')
    .create('tasks', {name: form.item.value })
		.then(function(response) {
			form.reset();
			form.item.focus();
			console.info('Saved:', response);
		})
		.catch(function(error) {
			console.error(error);
		});
});
