import { Component, OnInit, Input } from '@angular/core';
import { Task } from '../model/task'
import { ActivatedRoute, Router } from '@angular/router';
import { TaskManagerService } from '../task.manager.service'

@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.css']
})
export class TaskEditComponent implements OnInit {

  taskId: number;
  sub: any;
  action: String;
  task: Task;
  success: boolean = false;
  error: boolean = false;
  errorMessage: string = "";
  tasks: Task[] = [];

  constructor(private taskManagerService: TaskManagerService, private activeRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.task = new Task();
    this.sub = this.activeRoute.params.subscribe(params => {
      this.taskManagerService.getAllTask().subscribe(
        taskresult => {
          this.tasks  = taskresult;
          if (params['id']) {
            this.taskId = params['id'];
            this.action = "Update";
            this.task = this.tasks.find (t => t.taskId == params['id']);
          } else {
            this.action = "Add Task";
          }
        },
        error => {
          this.error=true;
          this.errorMessage = "Error occured While Loading Task";
        }
      );
    });
  }
 
  addTask() {
    this.taskManagerService.create(this.task).subscribe(
      addResult => {
        this.success = true;
        this.resetData();
      },
      error => {
        this.error=true;
        this.errorMessage = "Error occured While Adding Task";
      }
    )
  }

  updateTask() {
    this.taskManagerService.update(this.task).subscribe(
      addResult => {
        this.success = true;
      },
      error => {
        this.error=true;
        this.errorMessage = "Error occured While Adding Task";
      }
    )
    this.router.navigate(['viewTask']);
  }

  resetData() {
    this.task = new Task();
  }

  cancelEdit() {
    this.router.navigate(['viewTask']);
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
