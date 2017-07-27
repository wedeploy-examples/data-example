/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/


import UIKit
import WeDeploy
import SocketIO

class ToDoListViewController: UIViewController, UITableViewDataSource {
	
	@IBOutlet weak var tableView: UITableView!
	
	var todos = [String]()
	var socket: SocketIOClient?
	
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.dataSource = self
		tableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
		tableView.tableFooterView = UIView()
		tableView.separatorColor = .mainColor
		
		WeDeploy.data("https://data-boilerplatedata.wedeploy.sh")
			.orderBy(field: "id", order: .DESC)
			.limit(5)
			.get(resourcePath: "tasks")
			.toCallback { tasks, error in
				if let tasks = tasks {
					self.todos = tasks.map({ $0["name"] as! String})
					self.tableView.reloadData()
				}
				else {
					print("Error: \(error!)")
				}
			}
		
		socket = WeDeploy.data("https://data-boilerplatedata.wedeploy.sh")
			.orderBy(field: "id", order: .DESC)
			.limit(5)
			.watch(resourcePath: "tasks")
		
		socket?.on(.changes) { [unowned self] event in
			let changes = event.document["changes"] as! [[String: Any]]
			self.todos = changes.map({ $0["name"] as! String })
			self.tableView.reloadData()
		}
    }

	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return todos.count
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
		cell.textLabel?.text = todos[indexPath.row]
		cell.textLabel?.font = UIFont.weFont(ofSize: 17)
		cell.textLabel?.textAlignment = .center
		cell.selectionStyle = .none
		
		return cell
	}
	
	@IBAction func goBack() {
		self.navigationController?.popViewController(animated: true)
	}
}
