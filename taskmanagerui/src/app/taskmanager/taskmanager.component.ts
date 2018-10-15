import { Component, OnInit } from '@angular/core';
import { Task } from '../model/task'

@Component({
  selector: 'app-taskmanager',
  templateUrl: './taskmanager.component.html',
  styleUrls: ['./taskmanager.component.css']
}) 
export class TaskmanagerComponent implements OnInit {

  task: Task = {
    taskId: 1001,
    taskName: 'First Task',
    startDate: '09/06/2018',
    endDate: '09/12/2018',
    priority: 26,
    parentTaskId: 0,
    parentTaskName: '',
    end: false
  };

  constructor() { }

  ngOnInit() {
  }

}
