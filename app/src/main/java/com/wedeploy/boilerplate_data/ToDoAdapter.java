package com.wedeploy.boilerplate_data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wedeploy.boilerplate_data.databinding.TodoItemBinding;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

	private List<String> todos;

	public ToDoAdapter(List<String> todos) {
		this.todos = todos;
	}

	@Override
	public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		TodoItemBinding binding = TodoItemBinding.inflate(layoutInflater, parent, false);

		return new ToDoViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ToDoViewHolder holder, int position) {
		holder.binding.setTodo(todos.get(position));
	}

	@Override
	public int getItemCount() {
		return todos.size();
	}

	class ToDoViewHolder extends RecyclerView.ViewHolder {

		TodoItemBinding binding;

		ToDoViewHolder(TodoItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}
}
