package com.wedeploy.boilerplate_data;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.wedeploy.android.Callback;
import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.transport.Response;
import com.wedeploy.boilerplate_data.databinding.AddToDoActivityBinding;
import org.json.JSONException;
import org.json.JSONObject;

public class AddToDoActivity extends AppCompatActivity {

	private AddToDoActivityBinding binding;
	private WeDeploy weDeploy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_to_do_activity);

		weDeploy = new WeDeploy.Builder().build();

		binding = DataBindingUtil.setContentView(this, R.layout.add_to_do_activity);

		binding.addItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String todoItem = binding.item.getText().toString();

				if (!todoItem.isEmpty()) {
					addToDo(todoItem);
				} else {
					Toast.makeText(AddToDoActivity.this, "You have to fill the field",
						Toast.LENGTH_SHORT).show();
				}
			}
		});

		binding.goToList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AddToDoActivity.this, ToDoListActivity.class));
			}
		});
	}

	private void addToDo(String todo) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.putOpt("name", todo);
		} catch (JSONException e) {
		}

		weDeploy.data("https://data-boilerplatedata.wedeploy.sh")
			.create("tasks", jsonObject)
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					Toast.makeText(AddToDoActivity.this, "To do added", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(Exception e) {
					Toast.makeText(AddToDoActivity.this, "Error adding to do", Toast.LENGTH_SHORT)
						.show();
				}
			});
	}
}
