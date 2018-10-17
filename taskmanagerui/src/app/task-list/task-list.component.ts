import { Component, OnInit } from '@angular/core';
import { Task } from '../model/task'
import { Router } from '@angular/router';
import { TaskManagerService } from '../task.manager.service'
import * as _ from 'lodash';


@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  error: boolean = false;
  errorMessage: string = "";
  success: boolean = false;
  successMsg: string = "";
  tasks: Task[];
  tasksMaster: Task[];
  taskName: string;
  parentTaskName: string;
  priorityFrom: number;
  priorityTo: number;
  dateFrom: string;
  dateTo: String;

  filters = {}

  constructor(private router: Router, private taskManagerService: TaskManagerService) {
  }

  ngOnInit() {
   this.init();
  }

  private init() {
    this.taskManagerService.getAllTask().subscribe(
      taskresult => {
        this.tasksMaster = taskresult;
        this.tasks = this.tasksMaster;
        this.applyFilters();
      },
      error => {
        this.error=true;
        this.errorMessage = "Error occured While retriving Tasking";
      }
    )
  }

  private applyFilters() {
    this.tasks = _.filter(this.tasksMaster, _.conforms(this.filters))
  }

  filterContains(property: string, value: string) {
    this.filters[property] = val => val.includes(value);
    this.applyFilters();
  }

  filterGreaterThan(property: string, value: number) {
    this.filters[property] = val => val >= value;
    this.applyFilters();
  }

  filterLessThan(property: string, value: number) {
    if (value) {
      this.filters[property] = val => val <= value;
      this.applyFilters();
    } else {
      this.removeFilter(property);
    }
  }

  filterDateBefore(property: string, value: string) {
    if (value) {
      this.filters[property] = val => new Date(val).valueOf() <= new Date(value).valueOf();
      this.applyFilters();
    } else {
      this.removeFilter(property);
    }
  }

  filterDateAfter(property: string, value: string) {
    if (value) {
      this.filters[property] = val => new Date(val).valueOf() >= new Date(value).valueOf();
      this.applyFilters();
    } else {
      this.removeFilter(property);
    }
  }

  removeFilter(property: string) {
    delete this.filters[property]
    this[property] = null
    this.applyFilters()
  }

  editTask(task: Task) {
    this.router.navigate(['editTask/' + task.taskId]);
  }

  delete(task: Task) {
    this.taskManagerService.delete(task).subscribe(
      addResult => {
        this.success = true;
        this.init();
        this.successMsg = "Task Successfully Deleted"
      },
      error => {
        this.error=true;
        this.errorMessage = "Error occured While Deleting Task";
      }
    )
  }

  endTask(task: Task) {
    this.taskManagerService.endTask(task).subscribe(
      addResult => {
        this.success = true;
        this.init();
        this.successMsg = "Task Successfully Ended"
      },
      error => {
        this.error=true;
        this.errorMessage = "Error occured While Eding Task";
      }
    )
  }

}
