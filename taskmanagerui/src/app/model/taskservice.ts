
import { Injectable } from '@angular/core';
import { TASKS } from '../mock/tasklist'
import { Task } from '../model/task'

@Injectable()
export class TaskService {
    
  private tasks;

  constructor() {
    this.tasks = TASKS;
  }

  getTasks() {
    return this.tasks;
  }

  createTask(task: Task) {
    this.tasks.push(task);
  }

  updateTask( task: Task ) {

  }

  endTask(task: Task) {
    task.isEnd = true;
  }

}