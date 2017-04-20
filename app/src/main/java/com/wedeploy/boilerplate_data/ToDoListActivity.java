package com.wedeploy.boilerplate_data;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.wedeploy.boilerplate_data.databinding.ToDoListActivityBinding;
import com.wedeploy.sdk.Callback;
import com.wedeploy.sdk.RealTime;
import com.wedeploy.sdk.WeDeploy;
import com.wedeploy.sdk.query.SortOrder;
import com.wedeploy.sdk.transport.Response;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class ToDoListActivity extends AppCompatActivity {

	private static final String DATA_URL = "http://data.boilerplate-data.wedeploy.io";

	private ToDoAdapter adapter;
	private List<String> todos = new ArrayList<>();
	private WeDeploy weDeploy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.to_do_list_activity);
		adapter = new ToDoAdapter(todos);
		weDeploy = new WeDeploy.Builder().build();

		ToDoListActivityBinding binding =
			DataBindingUtil.setContentView(this, R.layout.to_do_list_activity);

		binding.todoList.setAdapter(adapter);
		binding.todoList.setLayoutManager(new LinearLayoutManager(this,
			LinearLayoutManager.VERTICAL, false));
		binding.todoList.addItemDecoration(new DividerItemDecoration(this,
			DividerItemDecoration.VERTICAL));
		binding.todoList.setItemAnimator(new DefaultItemAnimator());

		binding.goToHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		populateList();
	}

	private void populateList() {
		weDeploy.data(DATA_URL)
			.limit(5)
			.orderBy("id", SortOrder.DESCENDING)
			.get("tasks")
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					try {
						JSONArray array = new JSONArray(response.getBody());
						parseAndAddTodos(array);
					} catch (JSONException e) {
						onFailure(e);
					}
				}

				@Override
				public void onFailure(Exception e) {
					Toast.makeText(ToDoListActivity.this,
						"Error loading todos", Toast.LENGTH_SHORT).show();
				}
			});

		weDeploy.data(DATA_URL)
			.limit(5)
			.orderBy("id", SortOrder.DESCENDING)
			.watch("tasks")
			.on("changes", new RealTime.OnEventListener() {
				@Override
				public void onEvent(final Object... objects) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								JSONArray array = (JSONArray) objects[0];
								parseAndAddTodos(array);
							} catch (JSONException e) {
								Toast.makeText(ToDoListActivity.this,
									"Error loading todos", Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			});
	}

	private void parseAndAddTodos(JSONArray array) throws JSONException {
		List<String > newTodos = new ArrayList<>(array.length());
		for (int i = 0; i < array.length(); i++) {
			newTodos.add(array.getJSONObject(i).optString("name", ""));
		}

		todos.clear();
		todos.addAll(newTodos);
		adapter.notifyDataSetChanged();
	}
}
