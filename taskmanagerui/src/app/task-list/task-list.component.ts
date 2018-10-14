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
    this.taskManagerService.getAllTask().subscribe(
      taskresult => {
        this.tasksMaster = taskresult;
        this.tasks = this.tasksMaster;
        this.applyFilters();
        console.log(this.tasksMaster);
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
    console.log(task);
    this.router.navigate(['editTask/' + task.taskId]);
  }

  endTask(task: Task) {

  }

}
