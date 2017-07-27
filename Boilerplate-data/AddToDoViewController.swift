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

class AddToDoViewController: UIViewController {

	@IBOutlet weak var toDoTextField: UITextField!
	
	override func viewDidLoad() {
		super.viewDidLoad()
		let tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleScreenTap))
		view.addGestureRecognizer(tapGesture)
	}
	
	@IBAction func addToDoClick() {
		guard let todo = toDoTextField.text,
			!todo.isEmpty else { return }
		
		WeDeploy.data("https://data-boilerplatedata.wedeploy.sh")
			.create(resource: "tasks", object: ["name" : todo])
			.toCallback { objectCreated, error in
				if let objectCreated = objectCreated {
					print("To do added: \(objectCreated)")
					self.toDoTextField.text = ""
				}
				else {
					print("Error: \(error!)")
				}
			}
	}
	
	func handleScreenTap() {
		view.endEditing(true)
	}

}

